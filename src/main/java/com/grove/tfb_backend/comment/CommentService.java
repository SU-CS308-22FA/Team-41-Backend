package com.grove.tfb_backend.comment;


import com.grove.tfb_backend.comment.commentDto.CommentRequest;
import com.grove.tfb_backend.comment.commentDto.CommentResponse;
import com.grove.tfb_backend.feedback.Feedback;
import com.grove.tfb_backend.feedback.FeedbackDao;
import com.grove.tfb_backend.feedback.feedbackDto.FeedbackRequest;
import com.grove.tfb_backend.feedback.feedbackDto.FeedbackResponse;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.UsersDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentDao commentDao;
    private final UsersDao usersDao;

    public CommentService(CommentDao commentDao, UsersDao usersDao) {
        this.commentDao = commentDao;
        this.usersDao = usersDao;
    }

    @Transactional
    public void comment(CommentRequest comment) {
        Users user = usersDao.findUserById(comment.getUserId());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        Comment commentDb = new Comment();
        commentDb.setUser(user);
        commentDb.setDop(LocalDateTime.now());
        commentDb.setBody(comment.getBody());

        commentDao.save(commentDb);

    }

    public List<CommentResponse> getAllComment() {
        List<Comment> comments = commentDao.findAll();
        List<CommentResponse> toBeReturned = new ArrayList<>();
        for (Comment c: comments){
            CommentResponse commentResponse = new CommentResponse(c);
            toBeReturned.add(commentResponse);
        }
        return toBeReturned;
    }
}
