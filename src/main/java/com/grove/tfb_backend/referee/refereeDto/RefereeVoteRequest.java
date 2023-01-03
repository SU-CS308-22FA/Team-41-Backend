package com.grove.tfb_backend.referee.refereeDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefereeVoteRequest {

    private Long matchId;

    private Long totalPoints;
}
