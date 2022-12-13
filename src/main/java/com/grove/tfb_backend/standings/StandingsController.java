package com.grove.tfb_backend.standings;


import com.grove.tfb_backend.user.userDto.GeneralHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/standings")
public class StandingsController {

    private final StandingsService standingsService;

    public StandingsController(StandingsService standingsService) {
        this.standingsService = standingsService;
    }



    @GetMapping
    public GeneralHttpResponse<List<Standings>> getStandings(){
        GeneralHttpResponse<List<Standings>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(standingsService.getStandings());
        }
        catch (Exception e){
            response.setStatus("400: "+ e.getMessage());
        }
        return response;
    }
}
