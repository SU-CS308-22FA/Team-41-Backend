package com.grove.tfb_backend.teams;

import com.grove.tfb_backend.teams.TeamDto.TeamInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamsService {

    private final TeamsDao teamsDao;

    public TeamsService(TeamsDao teamsDao) {
        this.teamsDao = teamsDao;
    }

    public TeamInfo getTeamInfo(Long id) {
        Teams team = teamsDao.findTeamById(id);

        if (team == null) throw new IllegalStateException("TEAM NOT FOUND!");

        return new TeamInfo(team.getName(), team.getCity(), team.getStadiumName(), team.getLogoURL());
    }

    @Transactional
    public void addTeam(TeamInfo teamInfoDto) {

        if (teamsDao.existsByName(teamInfoDto.getName())) throw new IllegalStateException("NAME IN USE!");

        Teams newTeam = new Teams(teamInfoDto);
        Teams userDb = teamsDao.save(newTeam);

    }
}
