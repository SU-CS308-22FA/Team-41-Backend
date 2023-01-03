package com.grove.tfb_backend.referee;


import com.grove.tfb_backend.matches.Matches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Referee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double rating;

    private Long totalVote;

    private Long totalRefereeVote;

    private Double refereeRating;


    @OneToMany(mappedBy = "refereeId",cascade = CascadeType.ALL)
    private List<Matches> matches;
}
