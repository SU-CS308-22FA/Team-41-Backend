package com.grove.tfb_backend.standings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsDao extends JpaRepository<Standings, Long> {

    List<Standings> findAllByOrderByRankAsc();

    Standings findStandingsByTeamId(Long id);
}
