package com.grove.tfb_backend.feedback.feedbackDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackRequest {

    private Long userId;

    private String topic;

    private String body;
}
