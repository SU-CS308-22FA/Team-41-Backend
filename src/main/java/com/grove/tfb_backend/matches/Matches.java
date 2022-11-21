package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.MatchInfo;
import com.grove.tfb_backend.referee.Referee;
import com.grove.tfb_backend.teams.Teams;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String homeTeamName;

    private String awayTeamName;

    private String referee;

    private String city;

    private String stadiumName;

    private LocalDateTime dateAndTime;

    private String status;

    private boolean isFinished;

    private int goalHome;

    private int goalAway;

    private String result;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Teams home_team;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Teams away_team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Referee refereeId;


    public Matches(MatchInfo matchInfoDto) {
        homeTeamName = matchInfoDto.getHomeTeamName();
        awayTeamName = matchInfoDto.getAwayTeamName();
        referee = matchInfoDto.getReferee();
        city = matchInfoDto.getCity();
        stadiumName = matchInfoDto.getStadiumName();
        dateAndTime = matchInfoDto.getDateAndTime();
        status = matchInfoDto.getStatus();
        isFinished = matchInfoDto.isFinished();
        goalHome = matchInfoDto.getGoalHome();
        goalAway = matchInfoDto.getGoalAway();
        result = matchInfoDto.getResult();
        home_team = matchInfoDto.getHome_team();
        away_team = matchInfoDto.getAway_team();
        refereeId = matchInfoDto.getRefereeId();
    }
}