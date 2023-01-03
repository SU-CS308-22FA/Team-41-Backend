package com.grove.tfb_backend.referee;


import com.grove.tfb_backend.referee.refereeDto.AllReferees;
import com.grove.tfb_backend.referee.refereeDto.OneReferee;
import com.grove.tfb_backend.referee.refereeDto.RefereeVoteRequest;
import com.grove.tfb_backend.referee.refereeDto.VoteRequest;
import com.grove.tfb_backend.user.userDto.GeneralHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public GeneralHttpResponse<List<AllReferees>> getAllReferees(){
        GeneralHttpResponse<List<AllReferees>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(refereeService.getAllReferees());
        }
        catch (Exception e){

            response.setStatus("400: "+ e.getMessage());
        }
        return response;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<OneReferee> getReferee(@PathVariable Long id){
        GeneralHttpResponse<OneReferee> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(refereeService.getReferee(id));
        }
        catch (Exception e){
            response.setStatus("400: "+ e.getMessage());
        }
        return response;
    }

    @PostMapping("/refereeVote")
    public GeneralHttpResponse<String> handleRefereeVote(@RequestBody RefereeVoteRequest voteRequest){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            refereeService.handleRefereeVote(voteRequest);
        }
        catch (Exception e){
            response.setReturnObject(e.getMessage());
            response.setStatus("400");
        }
        return response;
    }


}
