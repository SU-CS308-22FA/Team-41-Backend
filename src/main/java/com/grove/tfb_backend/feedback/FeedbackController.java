package com.grove.tfb_backend.feedback;


import com.grove.tfb_backend.feedback.feedbackDto.FeedbackRequest;
import com.grove.tfb_backend.feedback.feedbackDto.FeedbackResponse;
import com.grove.tfb_backend.user.userDto.GeneralHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/feedback")
@CrossOrigin
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @PostMapping
    public GeneralHttpResponse<String> feedback(@RequestBody FeedbackRequest feedback){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try {
            feedbackService.feedback(feedback);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping
    public GeneralHttpResponse<List<FeedbackResponse>> getAllFeedback(){
        GeneralHttpResponse<List<FeedbackResponse>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(feedbackService.getAllFeedback());
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }
}
