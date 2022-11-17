package com.grove.tfb_backend.referee;


import com.grove.tfb_backend.referee.refereeDto.VoteRequest;
import com.grove.tfb_backend.user.userDto.GeneralHttpResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/referee")
public class RefereeController {

    private final RefereeService refereeService;

    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }




    @PostMapping("/vote")
    public GeneralHttpResponse<String> voteReferee(@RequestBody VoteRequest vote){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            refereeService.voteReferee(vote);
        }
        catch (Exception e){
            response.setReturnObject(e.getMessage());
            response.setStatus("400");
        }
        return response;
    }


}
