package com.grove.tfb_backend.feedback;


import com.grove.tfb_backend.feedback.feedbackDto.FeedbackRequest;
import com.grove.tfb_backend.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users user;

    private String topic;

    private String body;


}
