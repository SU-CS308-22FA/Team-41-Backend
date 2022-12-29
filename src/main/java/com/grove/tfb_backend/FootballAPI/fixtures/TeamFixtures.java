package com.grove.tfb_backend.FootballAPI.fixtures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamFixtures {
    private int id;
    private String name;
    private String logo;
    private boolean winner;
}
