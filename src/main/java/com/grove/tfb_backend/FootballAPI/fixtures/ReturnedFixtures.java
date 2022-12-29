package com.grove.tfb_backend.FootballAPI.fixtures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReturnedFixtures {
    private String get;
    private Map<String, String> parameters;
    private List<Map<String, String>> errors;
    private int results;
    private Map<String, Integer> paging;
    private List<ResponseFixtures> response;
}
