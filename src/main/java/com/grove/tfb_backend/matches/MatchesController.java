package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("api/v1/match")
public class MatchesController {

    @Autowired
    private final MatchesService matchesService;

    public MatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @PostMapping("/add_match")
    public GeneralHttpResponse<String> addMatchByAdmin(@RequestBody AddMatch newMatch) {
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            matchesService.addMatchByAdmin(newMatch);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<SingleMatchResponse> matchInfo(@PathVariable Long id){
        GeneralHttpResponse<SingleMatchResponse> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getMatchInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all")
    public GeneralHttpResponse<List<Matches>> allMatchesInfo(){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllMatches());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("home/{team_id}")
    public GeneralHttpResponse<List<Matches>> allHomeMatchesInfo(@PathVariable Long team_id){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllHomeTeamMatches(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("away/{team_id}")
    public GeneralHttpResponse<List<Matches>> allAwayMatchesInfo(@PathVariable Long team_id){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllAwayTeamMatches(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all/{team_id}")
    public GeneralHttpResponse<List<Matches>> allTeamMatchesInfo(@PathVariable Long team_id){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllTeamMatches(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("today")
    public GeneralHttpResponse<List<Matches>> allTeamMatchesInfo(){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllTodaysMatches());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }
}
