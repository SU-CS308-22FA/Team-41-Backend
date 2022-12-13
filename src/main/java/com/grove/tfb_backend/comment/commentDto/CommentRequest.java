package com.grove.tfb_backend.comment.commentDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {

    private Long userId;

    private LocalDateTime dop;

    private String body;
}
