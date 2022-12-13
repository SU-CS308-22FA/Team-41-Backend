package com.grove.tfb_backend.matches.MatchDto;


import com.grove.tfb_backend.comment.Comment;
import com.grove.tfb_backend.comment.commentDto.CommentResponse;
import com.grove.tfb_backend.matches.Matches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleMatchResponse {

    private long id;

    private String homeTeamName;

    private String awayTeamName;

    private String referee;

    private String city;

    private String stadiumName;

    private LocalDateTime dateAndTime;

    private String status;

    private boolean isFinished;

    private int goalHome;

    private int goalAway;

    private String result;

    private List<CommentResponse> comments;

    public SingleMatchResponse(Matches matchInfoDto){
        id = matchInfoDto.getId();
        homeTeamName = matchInfoDto.getHomeTeamName();
        awayTeamName = matchInfoDto.getAwayTeamName();
        referee = matchInfoDto.getReferee();
        city = matchInfoDto.getCity();
        stadiumName = matchInfoDto.getStadiumName();
        dateAndTime = matchInfoDto.getDateAndTime();
        status = matchInfoDto.getStatus();
        isFinished = matchInfoDto.isFinished();
        goalHome = matchInfoDto.getGoalHome();
        goalAway = matchInfoDto.getGoalAway();
        result = matchInfoDto.getResult();
        comments = CommentResponse.fromCommentList(matchInfoDto.getComments());
    }

}
