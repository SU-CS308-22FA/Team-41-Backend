package com.grove.tfb_backend.referee.refereeDto;


import com.grove.tfb_backend.referee.Referee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefPoint {
    private Referee ref;
    private Double points;
}
