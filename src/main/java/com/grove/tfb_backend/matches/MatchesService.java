package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import com.grove.tfb_backend.referee.Referee;
import com.grove.tfb_backend.referee.RefereeDao;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.teams.TeamsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchesService {

    private final MatchesDao matchesDao;
    private final TeamsDao teamsDao;

    private final RefereeDao refereeDao;

    public MatchesService(MatchesDao matchesDao, TeamsDao teamsDao, RefereeDao refereeDao) {
        this.matchesDao = matchesDao;
        this.teamsDao = teamsDao;
        this.refereeDao = refereeDao;
    }

    public MatchInfo getMatchInfo(Long id) {
        Matches match = matchesDao.findMatchById(id);

        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        return new MatchInfo(match.getHomeTeamName(), match.getAwayTeamName(),
                match.getReferee(), match.getCity(),
                match.getStadiumName(), match.getDateAndTime(),
                match.getStatus(), match.isFinished(),
                match.getGoalHome(), match.getGoalAway(), match.getResult(),
                match.getHome_team(), match.getAway_team(),match.getRefereeId());
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

    @Transactional
    public void addMatch(MatchInfo matchInfoDto) {

        Matches newMatch = new Matches(matchInfoDto);
        Matches matchDb = matchesDao.save(newMatch);

    }
}
