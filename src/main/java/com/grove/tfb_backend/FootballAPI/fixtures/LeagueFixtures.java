package com.grove.tfb_backend.FootballAPI.fixtures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeagueFixtures {
    private int id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Integer season;
    private String round;
}
