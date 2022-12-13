package com.grove.tfb_backend.comment.commentDto;


import com.grove.tfb_backend.comment.Comment;
import com.grove.tfb_backend.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String username;

    private Long userId;

    private LocalDateTime dop;

    private String body;

    public CommentResponse(Comment f){
        id = f.getId();
        username = f.getUser().getName();
        userId = f.getUser().getId();
        dop = f.getDop();
        body = f.getBody();
    }

    public static List<CommentResponse> fromCommentList(List<Comment> comments){
        return comments.stream().map(item -> new CommentResponse(item)).collect(Collectors.toList());
    }
}
