package com.grove.tfb_backend.report.reportDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportCloseRequest {

    private Long id;

    private Long issuerId;
}
