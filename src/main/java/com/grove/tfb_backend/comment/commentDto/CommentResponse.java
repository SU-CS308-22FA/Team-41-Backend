package com.grove.tfb_backend.comment.commentDto;


import com.grove.tfb_backend.comment.Comment;
import com.grove.tfb_backend.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

    private Long id;

    private Long userId;

    private LocalDateTime dop;

    private String body;

    public CommentResponse(Comment f){
        id = f.getId();
        userId = f.getUser().getId();
        dop = f.getDop();
        body = f.getBody();
    }
}
