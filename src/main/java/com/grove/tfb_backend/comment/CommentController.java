package com.grove.tfb_backend.comment;


import com.grove.tfb_backend.comment.commentDto.CommentRequest;
import com.grove.tfb_backend.comment.commentDto.CommentResponse;
import com.grove.tfb_backend.feedback.FeedbackService;
import com.grove.tfb_backend.feedback.feedbackDto.FeedbackRequest;
import com.grove.tfb_backend.feedback.feedbackDto.FeedbackResponse;
import com.grove.tfb_backend.user.userDto.GeneralHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public GeneralHttpResponse<String> comment(@RequestBody CommentRequest comment){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try {
            commentService.comment(comment);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping
    public GeneralHttpResponse<List<CommentResponse>> getAllComment(){
        GeneralHttpResponse<List<CommentResponse>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(commentService.getAllComment());
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }
}
