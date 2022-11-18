package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String homeTeam;

    private String awayTeam;

    private String referee;

    private String city;

    private String stadiumName;

    private LocalDateTime dateAndTime;

    private String status;

    private boolean isFinished;

    private int goalHome;

    private int goalAway;


    public Matches(MatchInfo matchInfoDto) {
        homeTeam = matchInfoDto.getHomeTeam();
        awayTeam = matchInfoDto.getAwayTeam();
        referee = matchInfoDto.getReferee();
        city = matchInfoDto.getCity();
        stadiumName = matchInfoDto.getStadiumName();
        dateAndTime = matchInfoDto.getDateAndTime();
        status = matchInfoDto.getStatus();
        isFinished = matchInfoDto.isFinished();
        goalHome = matchInfoDto.getGoalHome();
        goalAway = matchInfoDto.getGoalAway();
    }
}
