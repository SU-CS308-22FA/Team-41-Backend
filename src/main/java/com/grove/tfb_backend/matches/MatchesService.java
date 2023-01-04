package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import com.grove.tfb_backend.referee.Referee;
import com.grove.tfb_backend.referee.RefereeDao;
import com.grove.tfb_backend.standings.StandingsService;
import com.grove.tfb_backend.standings.standingsDto.StandingsUpdate;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.teams.TeamsDao;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.UsersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchesService {

    private final MatchesDao matchesDao;
    private final TeamsDao teamsDao;
    private final UsersDao usersDao;
    private final RefereeDao refereeDao;
    private final StandingsService standingsService;

    public MatchesService(MatchesDao matchesDao, TeamsDao teamsDao, UsersDao usersDao, RefereeDao refereeDao, StandingsService standingsService) {
        this.matchesDao = matchesDao;
        this.teamsDao = teamsDao;
        this.usersDao = usersDao;
        this.refereeDao = refereeDao;
        this.standingsService = standingsService;
    }

    public SingleMatchResponse getMatchInfo(Long id) {
        Matches match = matchesDao.findMatchById(id);

        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        return new SingleMatchResponse(match);
    }

    public Teams getTeam(String teamName) {
        if(teamsDao.existsByName(teamName)) {
            return teamsDao.findTeamsByName(teamName);
        }
        return null;
    }

    public Referee getReferee(String refereeName){
        return refereeDao.findRefereeByName(refereeName);
    }

    public List<Matches> getAllMatches() {
        List<Matches> matches = matchesDao.findAllByOrderByDateAndTime();

        if (matches.size() == 0) throw new IllegalStateException("0 MATCHES FOUND!");

        return matches;
    }

    public List<Matches> getAllHomeTeamMatches(Long teamId) {
        String teamName = teamsDao.findTeamById(teamId).getName();
        List<Matches> matches = matchesDao.findAllByHomeTeamNameOrderByDateAndTime(teamName);

        if (matches.size() == 0) throw new IllegalStateException("0 MATCHES FOUND!");

        return matches;
    }

    public List<Matches> getAllAwayTeamMatches(Long teamId) {
        String teamName = teamsDao.findTeamById(teamId).getName();
        List<Matches> matches = matchesDao.findAllByAwayTeamNameOrderByDateAndTime(teamName);

        if (matches.size() == 0) throw new IllegalStateException("0 MATCHES FOUND!");

        return matches;
    }

    public List<Matches> getAllTeamMatches(Long teamId) {
        String teamName = teamsDao.findTeamById(teamId).getName();
        List<Matches> matches = matchesDao.findAllByHomeTeamNameOrAwayTeamNameOrderByDateAndTime(teamName, teamName);

        if (matches.size() == 0) throw new IllegalStateException("0 MATCHES FOUND!");

        return matches;
    }

    /**
     * this function gets admin id and iinformation of a new match and tries to add that match to DB.
     *
     * @param newMatch is an object contain admin id and information of new match
     *
     * @throws IllegalStateException if the given admin id does not exist
     * @throws IllegalStateException if the given admin id is not an admin, it belongs to normal user
     * @throws IllegalStateException if the given home team information does not exist
     * @throws IllegalStateException if the given away team information does not exist
     * @throws IllegalStateException if the given referee information does not exist
     *
     * @see AddMatch
     * */
    public void addMatchByAdmin(AddMatch newMatch) {
        Users admin = usersDao.findUserById(newMatch.getUserId());
        if(admin == null)  throw new IllegalStateException("ADMIN USER NOT FOUND!");
        if(!admin.isAdmin()) throw new IllegalStateException("NO PERMISSION!");

        Teams home = teamsDao.findTeamById(newMatch.getHomeId());
        if(home == null) throw new IllegalStateException("HOME TEAM NOT FOUND!");

        Teams away = teamsDao.findTeamById(newMatch.getAwayId());
        if(away == null) throw new IllegalStateException("AWAY TEAM NOT FOUND!");

        Referee ref = refereeDao.findRefereeById(newMatch.getRefereeId());
        if(ref == null) throw new IllegalStateException("REFEREE NOT FOUND!");

        String result;
        String status = "Match Finished";
        boolean isFinished = true;
        if(newMatch.getHomeGoals() == newMatch.getAwayGoals()) {
            if(newMatch.getHomeGoals() == -1) {
                status = "Time to be defined";
                result = "none";
                isFinished = false;
            }
            else
                result = "draw";
        }
        else if(newMatch.getHomeGoals() > newMatch.getAwayGoals())
            result = "home winner";
        else
            result = "away winner";

        MatchInfo match = new MatchInfo(home.getName(), away.getName(), ref.getName(), home.getCity(),
                                        home.getStadiumName(), newMatch.getDate(), "Match Finished", true,
                                        newMatch.getHomeGoals(), newMatch.getAwayGoals(), result, home, away, ref, -1);
        addMatch(match);
    }

    @Transactional
    public void updateResultByAdmin(Long id, UpdateMatch updatedMatch) {
        Users admin = usersDao.findUserById(id);
        if(admin == null)  throw new IllegalStateException("ADMIN NOT FOUND!");
        if(!admin.isAdmin()) throw new IllegalStateException("NO PERMISSION!");

        Matches match = matchesDao.findMatchById(updatedMatch.getMatchId());
        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        Referee ref = refereeDao.findRefereeById(updatedMatch.getRefereeId());

        String result;
        if(updatedMatch.getHomeGoals() == updatedMatch.getAwayGoals())
            result = "draw";
        else if(updatedMatch.getHomeGoals() > updatedMatch.getAwayGoals())
            result = "home winner";
        else
            result = "away winner";

        match.setResult(result);
        match.setStatus("Match Finished");
        match.setReferee(ref.getName());
        match.setRefereeId(ref);
        match.setGoalHome(updatedMatch.getHomeGoals());
        match.setGoalAway(updatedMatch.getAwayGoals());
    }

    @Transactional
    void updateMatchAuto(MatchAutoUpdate matchUpdate) {
        Matches match = matchesDao.findMatchesByApiID(matchUpdate.getApiID());
        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");
        if(match.isFinished()) throw new IllegalStateException("MATCH ALREADY UPDATED!");
        if(matchUpdate.getDate().compareTo(LocalDateTime.now()) > 0) throw new IllegalStateException("TOO EARLY TO UPDATE!");

        if(matchUpdate.isFinished()) {
            StandingsUpdate newStandings = new StandingsUpdate(match.getHome_team().getId(), match.getAway_team().getId(),
                    matchUpdate.getHomeGoals(), matchUpdate.getAwayGoals());

            standingsService.updateStandings(newStandings);
            standingsService.updateRanks();
            System.out.println("updating standings for match of "+match.getHomeTeamName()+" "+match.getAwayTeamName());
        }

        match.setCity(matchUpdate.getCity());
        match.setStadiumName(matchUpdate.getStadiumName());
        match.setGoalHome(matchUpdate.getHomeGoals());
        match.setGoalAway(matchUpdate.getAwayGoals());
        match.setDateAndTime(matchUpdate.getDate());
        match.setResult(matchUpdate.getResult());
        match.setStatus(matchUpdate.getStatus());
        match.setFinished(matchUpdate.isFinished());
        matchesDao.save(match);
    }

    @Transactional
    public void addMatch(MatchInfo matchInfoDto) {

        Matches newMatch = new Matches(matchInfoDto);
        Matches matchDb = matchesDao.save(newMatch);

    }

    /**
    * this function get the matches dated as today if there are any.
     *
     * @return list of matches, that each match contain all information of that match
     *
     * @throws IllegalStateException if there is no match dated as today
     * */
    public List<Matches> getAllTodaysMatches() {
        String date = LocalDate.now().toString();
        List<Matches> matches = matchesDao.findAllByOrderByDateAndTime();
        List<Matches> res = new ArrayList<>();

        for(Matches m: matches) {
            String tmp = m.getDateAndTime().toString();
            if(tmp.substring(0, 10).equals(date)) {
                res.add(m);
            }
        }

        if (res.size() == 0) throw new IllegalStateException("0 MATCHES FOUND!");

        return res;
    }

    public Long getId(MatchInfo matchInfoDto) {
        Long id = matchesDao.findId(matchInfoDto.getDateAndTime(), matchInfoDto.getHomeTeamName(), matchInfoDto.getAwayTeamName());
        return id;
    }

    @Transactional
    public void updateMatch(Long matchId, MatchInfo matchInfoDto) {
        Matches match = matchesDao.findMatchById(matchId);
        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        match.setFinished(matchInfoDto.isFinished());
        match.setStatus(matchInfoDto.getStatus());
        match.setResult(matchInfoDto.getResult());
        match.setGoalAway(matchInfoDto.getGoalAway());
        match.setGoalHome(matchInfoDto.getGoalHome());
        match.setDateAndTime(matchInfoDto.getDateAndTime());
    }
}
