package com.grove.tfb_backend.feedback;


import com.grove.tfb_backend.feedback.feedbackDto.FeedbackRequest;
import com.grove.tfb_backend.feedback.feedbackDto.FeedbackResponse;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.UsersDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackDao feedbackDao;
    private final UsersDao usersDao;

    public FeedbackService(FeedbackDao feedbackDao, UsersDao usersDao) {
        this.feedbackDao = feedbackDao;
        this.usersDao = usersDao;
    }

    /**
     * Saves user feedback.
     *
     * @param feedback the feedback request containing the user's ID and the feedback topic and body
     *
     * @throws IllegalStateException if the user is not found
     */
    @Transactional
    public void feedback(FeedbackRequest feedback) {
        Users user = usersDao.findUserById(feedback.getUserId());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        Feedback feedbackDb = new Feedback();
        feedbackDb.setUser(user);
        feedbackDb.setTopic(feedback.getTopic());
        feedbackDb.setBody(feedback.getBody());

        feedbackDao.save(feedbackDb);

    }

    /**
     * Returns all user feedback.
     *
     * @return a list of feedback responses, each containing the feedback topic and body
     */
    public List<FeedbackResponse> getAllFeedback() {
        List<Feedback> feedbacks = feedbackDao.findAll();
        List<FeedbackResponse> toBeReturned = new ArrayList<>();
        for (Feedback f: feedbacks){
            FeedbackResponse feedbackResponse = new FeedbackResponse(f);
            toBeReturned.add(feedbackResponse);
        }
        return toBeReturned;
    }
}
