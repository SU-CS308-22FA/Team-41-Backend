package com.grove.tfb_backend.comment;

import com.grove.tfb_backend.comment.commentDto.CommentResponse;
import com.grove.tfb_backend.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);
}
