package com.grove.tfb_backend.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamsDao extends JpaRepository<Teams,Long> {

    boolean existsByName(String name); // sql query yerine geçiyor

    Teams findTeamById(Long id);
}
