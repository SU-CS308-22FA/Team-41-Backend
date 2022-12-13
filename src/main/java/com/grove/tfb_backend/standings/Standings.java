package com.grove.tfb_backend.standings;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Standings {

    @Id
    private Long teamId;

    private String teamName;

    private Integer rank;

    private Integer points;

    private Integer winCount;

    private Integer drawnCount;

    private Integer loseCount;

    private Integer goalsFor;

    private Integer goalsAgainst;

    private Integer average;


}
