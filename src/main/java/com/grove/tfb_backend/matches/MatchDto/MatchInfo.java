package com.grove.tfb_backend.matches.MatchDto;

import com.grove.tfb_backend.referee.Referee;
import com.grove.tfb_backend.teams.Teams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatchInfo {

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

    private Teams home_team;

    private Teams away_team;

    private Referee refereeId;

    private int apiID;

}
