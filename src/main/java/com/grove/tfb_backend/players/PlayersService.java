package com.grove.tfb_backend.players;

import com.grove.tfb_backend.players.PlayerDto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayersService {

    private final PlayersDao playersDao;

    public PlayersService(PlayersDao playersDao) {
        this.playersDao = playersDao;
    }

    public PlayerInfo getPlayerInfo(Long id) {
        Players player = playersDao.findPlayerById(id);

        if (player == null) throw new IllegalStateException("PLAYER NOT FOUND!");

        return new PlayerInfo(player.getName(), player.getTeamName(), player.getAge(), player.getPosition(), player.getNumber(), player.getPictureURL());
    }

    @Transactional
    public void addPlayer(PlayerInfo playerInfoDto) {

        if (playersDao.existsByName(playerInfoDto.getName())) throw new IllegalStateException("NAME IN USE!");

        Players newPlayer = new Players(playerInfoDto);
        Players playerDb = playersDao.save(newPlayer);

    }
}
