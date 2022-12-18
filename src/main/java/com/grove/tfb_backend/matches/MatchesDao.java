package com.grove.tfb_backend.matches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MatchesDao extends JpaRepository<Matches,Long> {

    List<Matches> findAllByOrderByDateAndTime();

    List<Matches> findAllByHomeTeamNameOrderByDateAndTime(String homeTeamName);

    List<Matches> findAllByAwayTeamNameOrderByDateAndTime(String awayTeamName);

    List<Matches> findAllByHomeTeamNameOrAwayTeamNameOrderByDateAndTime(String homeTeamName, String awayTeamName);

    @Query("SELECT m.id FROM Matches m where m.dateAndTime = :time and m.homeTeamName = :home and m.awayTeamName = :away ORDER BY m.dateAndTime")
    Long findId(@Param("time") LocalDateTime time, @Param("home") String home, @Param("away") String away);

    Matches findMatchById(Long id);
}
