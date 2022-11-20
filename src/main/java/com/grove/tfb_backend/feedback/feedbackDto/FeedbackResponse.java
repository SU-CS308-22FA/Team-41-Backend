package com.grove.tfb_backend.feedback.feedbackDto;


import com.grove.tfb_backend.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackResponse {

    private Long id;

    private Long userId;

    private String topic;

    private String body;

    public FeedbackResponse(Feedback f){
        id = f.getId();
        userId = f.getUser().getId();
        topic = f.getTopic();
        body = f.getBody();
    }
}
