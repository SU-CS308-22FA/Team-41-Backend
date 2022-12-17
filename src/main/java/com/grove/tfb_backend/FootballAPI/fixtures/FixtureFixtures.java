package com.grove.tfb_backend.FootballAPI.fixtures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FixtureFixtures {
    private int id;
    private String referee;
    private String timezone;
    private Date date;
    private int timestamp;
    private PeriodFixtures periods;
    private VenueFixtures venue;
    private StatusFixtures status;
}

