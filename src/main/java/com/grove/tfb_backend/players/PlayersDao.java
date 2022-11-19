package com.grove.tfb_backend.players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayersDao extends JpaRepository<Players,Long> {

    boolean existsByName(String name);

    List<Players> findPlayersByTeamName(String teamName);

    Players findPlayerById(Long id);
}
