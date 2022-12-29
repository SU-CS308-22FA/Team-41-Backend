package com.grove.tfb_backend.FootballAPI.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatusFixtures {
    @JsonProperty("long")
    private String long_;
    @JsonProperty("short")
    private String short_;
    private Integer elapsed;
}
