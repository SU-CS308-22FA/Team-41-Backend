package com.grove.tfb_backend.standings;

import com.grove.tfb_backend.standings.standingsDto.StandingsUpdate;
import com.grove.tfb_backend.teams.TeamsDao;
import com.grove.tfb_backend.teams.TeamsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class StandingsService {


    private final StandingsDao standingsDao;
    private final TeamsService teamsService;

    private final TeamsDao teamsDao;

    public StandingsService(StandingsDao standingsDao, TeamsService teamsService, TeamsDao teamsDao) {
        this.standingsDao = standingsDao;
        this.teamsService = teamsService;
        this.teamsDao = teamsDao;
    }

    public List<Standings> getStandings() {
        return standingsDao.findAll(Sort.by("points").descending().and(Sort.by("average").descending()));
    }

    @Transactional
    public void updateStandings(StandingsUpdate s){
        Integer hGoal = s.getHomeGoal();
        Integer aGoal = s.getAwayGoal();
        Standings homeTeam = standingsDao.findStandingsByTeamId(s.getHomeId());
        Standings awayTeam = standingsDao.findStandingsByTeamId(s.getAwayId());

        homeTeam.setGoalsFor(homeTeam.getGoalsFor()+hGoal);
        homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst()+aGoal);
        homeTeam.setAverage(homeTeam.getAverage()+hGoal-aGoal);
        awayTeam.setGoalsFor(awayTeam.getGoalsFor()+aGoal);
        awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst()+hGoal);
        awayTeam.setAverage(awayTeam.getAverage()+aGoal-hGoal);

        if (hGoal.equals(aGoal)){
            homeTeam.setDrawnCount(homeTeam.getDrawnCount()+1);
            awayTeam.setDrawnCount(awayTeam.getDrawnCount()+1);
            homeTeam.setPoints(homeTeam.getPoints()+1);
            awayTeam.setPoints(awayTeam.getPoints()+1);

        }
        else if (hGoal > aGoal){
            homeTeam.setWinCount(homeTeam.getWinCount()+1);
            awayTeam.setLoseCount(awayTeam.getLoseCount()+1);
            homeTeam.setPoints(homeTeam.getPoints()+3);

        }
        else {
            awayTeam.setPoints(awayTeam.getPoints()+3);
            homeTeam.setLoseCount(homeTeam.getLoseCount()+1);
            awayTeam.setWinCount(awayTeam.getWinCount()+1);
        }
    }





    /*
    @PostConstruct
    private void fillTable(){

        List<Standings> standings = new ArrayList<>();

        standings.add(new Standings(4L,1,29,9,2,2,36,14));
        standings.add(new Standings(5L,2,27,8,3,2,23,10));
        standings.add(new Standings(13L,3,24,6,6,1,24,14));
        standings.add(new Standings(3L,4,24,6,6,2,16,9));
        standings.add(new Standings(2L,5,24,7,3,3,19,15));
        standings.add(new Standings(8L,6,23,7,2,5,20,15));
        standings.add(new Standings(7L,7,23,6,5,2,19,16));
        standings.add(new Standings(1L,8,22,6,4,3,26,18));
        standings.add(new Standings(6L,9,17,4,5,5,19,24));
        standings.add(new Standings(14L,10,16,4,4,5,19,19));
        standings.add(new Standings(11L,11,16,5,1,6,19,21));
        standings.add(new Standings(15L,12,15,4,3,6,13,20));
        standings.add(new Standings(10L,13,15,4,3,6,11,22));
        standings.add(new Standings(16L,14,14,4,2,7,12,22));
        standings.add(new Standings(19L,15,13,3,4,6,24,27));
        standings.add(new Standings(12L,16,13,3,4,6,16,21));
        standings.add(new Standings(9L,17,11,2,5,7,13,19));
        standings.add(new Standings(18L,18,8,2,2,9,12,26));
        standings.add(new Standings(17L,19,7,1,4,8,15,24));

        standingsDao.saveAll(standings);

    }
     */
}
