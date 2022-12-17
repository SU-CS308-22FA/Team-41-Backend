package com.grove.tfb_backend.FootballAPI.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenueTeams {
    private int id;
    private String name;
    private String address;
    private String city;
    private Integer capacity;
    private String surface;
    private String image;
}
