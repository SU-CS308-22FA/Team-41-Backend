package com.grove.tfb_backend.players;

import com.grove.tfb_backend.players.PlayerDto.*;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.teams.TeamsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayersService {

    private final PlayersDao playersDao;
    private final TeamsDao teamsDao;

    public PlayersService(PlayersDao playersDao, TeamsDao teamsDao) {
        this.playersDao = playersDao;
        this.teamsDao = teamsDao;
    }


    public Players getPlayerInfo(Long id) {
        Players player = playersDao.findPlayerById(id);

        if (player == null) throw new IllegalStateException("PLAYER NOT FOUND!");

        return player;
    }

    public Teams getTeam(String teamName) {
        if(teamsDao.existsByName(teamName)) {
            return teamsDao.findTeamsByName(teamName);
        }
        return null;
    }

    public List<Players> getPlayersOfTeam(Long teamId) {
        String teamName = teamsDao.findTeamById(teamId).getName();
        List<Players> players = playersDao.findPlayersByTeamName(teamName);

        if (players.size() == 0) throw new IllegalStateException("0 PLAYERS FOUND!");

        return players;
    }

    public List<Players> getAllPlayers() {
        List<Players> players = playersDao.findAll();

        if (players.size() == 0) throw new IllegalStateException("0 PLAYERS FOUND!");

        return players;
    }

    @Transactional
    public void addPlayer(PlayerInfo playerInfoDto) {

        if (playersDao.existsByName(playerInfoDto.getName())) throw new IllegalStateException("NAME IN USE!");

        Players newPlayer = new Players(playerInfoDto);
        Players playerDb = playersDao.save(newPlayer);

    }
}
