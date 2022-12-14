package com.grove.tfb_backend.report.reportDto;

import com.grove.tfb_backend.comment.Comment;
import com.grove.tfb_backend.comment.commentDto.CommentResponse;
import com.grove.tfb_backend.report.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportResponseInstance {

    private Long id;

    private Long reporterId;

    private CommentResponse comment;

    private LocalDateTime date;

    private String type;

    public ReportResponseInstance(Report report, CommentResponse comment){
        id = report.getId();
        reporterId = report.getReporterId();
        this.comment = comment;
        date = report.getDateTime();
        type = report.getType();

    }
}
