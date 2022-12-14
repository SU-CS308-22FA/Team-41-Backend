package com.grove.tfb_backend.report.reportDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportRequest {

    private Long reportedUserId;

    private Long commentId;

    private String type;

    private Long reporterId;
}
