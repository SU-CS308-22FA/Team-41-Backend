package com.grove.tfb_backend.matches.MatchDto;

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

}
