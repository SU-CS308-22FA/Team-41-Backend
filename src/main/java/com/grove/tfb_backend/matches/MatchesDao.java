package com.grove.tfb_backend.matches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchesDao extends JpaRepository<Matches,Long> {

    //boolean existsByName(String name); // sql query yerine geçiyor

    Matches findMatchById(Long id);
}
