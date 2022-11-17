package com.grove.tfb_backend.referee.refereeDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteRequest {

    private Long id;

    private Double rate;
}
