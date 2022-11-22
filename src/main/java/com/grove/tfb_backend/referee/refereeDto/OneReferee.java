package com.grove.tfb_backend.referee.refereeDto;


import com.grove.tfb_backend.matches.Matches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OneReferee {

    private Long id;

    private String name;

    private Long totalVote;

    private Double rating;

    private List<Matches> matchesList;
}
