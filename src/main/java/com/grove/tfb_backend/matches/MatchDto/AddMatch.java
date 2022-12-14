package com.grove.tfb_backend.matches.MatchDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddMatch {

    private Long userId;

    private Long homeId;

    private Long awayId;

    private Long refereeId;

    private int homeGoals;

    private int awayGoals;

    private LocalDateTime date;

}
