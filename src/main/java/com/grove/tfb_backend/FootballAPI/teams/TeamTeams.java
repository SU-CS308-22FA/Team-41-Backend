package com.grove.tfb_backend.FootballAPI.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamTeams {
    private int id;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private String logo;
}
