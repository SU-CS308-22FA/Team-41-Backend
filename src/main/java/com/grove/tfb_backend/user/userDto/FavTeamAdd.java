package com.grove.tfb_backend.user.userDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavTeamAdd {

    private Long userId;

    private Long teamId;
}
