package com.grove.tfb_backend.report;


import com.grove.tfb_backend.report.reportDto.ReportCloseRequest;
import com.grove.tfb_backend.report.reportDto.ReportRequest;
import com.grove.tfb_backend.report.reportDto.ReportResponseInstance;
import com.grove.tfb_backend.user.userDto.GeneralHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/report")
public class ReportController {


    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @PostMapping
    public GeneralHttpResponse<String> handleReport(@RequestBody ReportRequest reportRequest){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            reportService.handleReport(reportRequest);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping("/all")
    public GeneralHttpResponse<List<ReportResponseInstance>> getActiveReports(){
        GeneralHttpResponse<List<ReportResponseInstance>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(reportService.getActiveReports());
        }
        catch (Exception e){
            response.setStatus("400: "+ e.getMessage());
        }
        return response;
    }

    @PostMapping("/close")
    public GeneralHttpResponse<String> closeReport(@RequestBody ReportCloseRequest req){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            reportService.closeReport(req);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }
}
