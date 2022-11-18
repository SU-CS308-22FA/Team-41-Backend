package com.grove.tfb_backend.players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayersDao extends JpaRepository<Players,Long> {

    boolean existsByName(String name); // sql query yerine geçiyor

    Players findPlayerById(Long id);
}
