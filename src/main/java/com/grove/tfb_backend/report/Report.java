package com.grove.tfb_backend.report;


import com.grove.tfb_backend.comment.Comment;
import com.grove.tfb_backend.report.reportDto.ReportRequest;
import com.grove.tfb_backend.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reportedUserId;

    private Long commentId;

    private String type;

    private Long reporterId;

    private LocalDateTime dateTime;

    private boolean isActive;

    public Report(ReportRequest rr){
        reportedUserId = rr.getReportedUserId();
        commentId = rr.getCommentId();
        type = rr.getType();
        reporterId = rr.getReporterId();
        dateTime = LocalDateTime.now();
        isActive = true;
    }



}
