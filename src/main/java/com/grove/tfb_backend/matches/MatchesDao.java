package com.grove.tfb_backend.matches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchesDao extends JpaRepository<Matches,Long> {

    List<Matches> findAllByOrderByDateAndTime();

    List<Matches> findAllByHomeTeamNameOrderByDateAndTime(String homeTeamName);

    List<Matches> findAllByAwayTeamNameOrderByDateAndTime(String awayTeamName);

    List<Matches> findAllByHomeTeamNameOrAwayTeamNameOrderByDateAndTime(String homeTeamName, String awayTeamName);

    Matches findMatchById(Long id);
}
