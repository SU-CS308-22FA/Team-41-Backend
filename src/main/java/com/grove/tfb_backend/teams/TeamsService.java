package com.grove.tfb_backend.teams;

import com.grove.tfb_backend.matches.MatchesDao;
import com.grove.tfb_backend.players.PlayersDao;
import com.grove.tfb_backend.teams.TeamDto.TeamInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamsService {

    private final TeamsDao teamsDao;
    private final PlayersDao playersDao;
    private final MatchesDao matchesDao;

    public TeamsService(TeamsDao teamsDao, PlayersDao playersDao, MatchesDao matchesDao) {
        this.teamsDao = teamsDao;
        this.playersDao = playersDao;
        this.matchesDao = matchesDao;
    }

    public TeamInfo getTeamInfo(Long id) {
        Teams team = teamsDao.findTeamById(id);

        if (team == null) throw new IllegalStateException("TEAM NOT FOUND!");

        return new TeamInfo(team.getName(), team.getCity(), team.getStadiumName(), team.getLogoURL());
    }

    public List<Teams> getAllTeams() {
        List<Teams> teams = teamsDao.findAll();

        if (teams == null) throw new IllegalStateException("0 TEAMS FOUND!");

        return teams;
    }

    public void addPlayersAndMatches() {
        List<Teams> teams = getAllTeams();
        List<Teams> newTeams = new ArrayList<Teams>();

        for(int i = 0; i < teams.size(); i++) {
            Teams tmp = teams.get(i);
            tmp.setPlayers(playersDao.findPlayersByTeamName(tmp.getName()));
            tmp.setHomeMatches(matchesDao.findAllByHomeTeamNameOrderByDateAndTime(tmp.getName()));
            tmp.setAwayMatches(matchesDao.findAllByAwayTeamNameOrderByDateAndTime(tmp.getName()));
            newTeams.add(tmp);
        }

        teamsDao.deleteAll();
        teamsDao.saveAll(newTeams);
    }

    @Transactional
    public void addTeam(TeamInfo teamInfoDto) {

        if (teamsDao.existsByName(teamInfoDto.getName())) throw new IllegalStateException("NAME IN USE!");

        Teams newTeam = new Teams(teamInfoDto);
        Teams teamDb = teamsDao.save(newTeam);

    }
}
