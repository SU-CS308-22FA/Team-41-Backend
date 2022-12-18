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
public class UpdateMatch {

    private Long matchId;

    private Long refereeId;

    private int homeGoals;

    private int awayGoals;

}
