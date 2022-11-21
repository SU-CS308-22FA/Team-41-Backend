package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import com.grove.tfb_backend.referee.Referee;
import com.grove.tfb_backend.referee.RefereeDao;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.teams.TeamsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    public Matches getMatchInfo(Long id) {
        Matches match = matchesDao.findMatchById(id);

        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        return match;
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
