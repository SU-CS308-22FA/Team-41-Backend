package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import com.grove.tfb_backend.referee.Referee;
import com.grove.tfb_backend.referee.RefereeDao;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.teams.TeamsDao;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.UsersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchesService {

    private final MatchesDao matchesDao;
    private final TeamsDao teamsDao;
    private final UsersDao usersDao;
    private final RefereeDao refereeDao;

    public MatchesService(MatchesDao matchesDao, TeamsDao teamsDao, UsersDao usersDao, RefereeDao refereeDao) {
        this.matchesDao = matchesDao;
        this.teamsDao = teamsDao;
        this.usersDao = usersDao;
        this.refereeDao = refereeDao;
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
        if(newMatch.getHomeGoals() == newMatch.getAwayGoals())
            result = "draw";
        else if(newMatch.getHomeGoals() > newMatch.getAwayGoals())
            result = "home winner";
        else
            result = "away winner";

        MatchInfo match = new MatchInfo(home.getName(), away.getName(), ref.getName(), home.getCity(),
                                        home.getStadiumName(), newMatch.getDate(), "Match Finished", true,
                                        newMatch.getHomeGoals(), newMatch.getAwayGoals(), result, home, away, ref);
        addMatch(match);
    }

    @Transactional
    public void addMatch(MatchInfo matchInfoDto) {

        Matches newMatch = new Matches(matchInfoDto);
        Matches matchDb = matchesDao.save(newMatch);

    }
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


}
