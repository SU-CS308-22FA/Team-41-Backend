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
public class MatchAutoUpdate {
    private String city;
    private String stadiumName;
    private int homeGoals;
    private int awayGoals;
    private LocalDateTime date;
    private String result;
    private String status;
    private boolean isFinished;
    private int apiID;
}
