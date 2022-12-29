package com.grove.tfb_backend.teams;

import com.grove.tfb_backend.teams.TeamDto.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/team")
public class TeamsController {

    @Autowired
    private final TeamsService teamsService;


    public TeamsController(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<TeamInfo> teamInfo(@PathVariable Long id){
        GeneralHttpResponse<TeamInfo> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(teamsService.getTeamInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all")
    public GeneralHttpResponse<List<Teams>> allTeamInfos(){
        GeneralHttpResponse<List<Teams>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(teamsService.getAllTeams());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }
}
