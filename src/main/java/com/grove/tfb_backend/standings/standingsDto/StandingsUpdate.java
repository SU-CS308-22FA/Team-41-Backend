package com.grove.tfb_backend.standings.standingsDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandingsUpdate{

    private Long homeId;

    private Long awayId;

    private Integer homeGoal;

    private Integer awayGoal;


}
