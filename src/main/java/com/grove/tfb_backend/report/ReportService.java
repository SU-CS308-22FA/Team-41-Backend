package com.grove.tfb_backend.report;


import com.grove.tfb_backend.comment.Comment;
import com.grove.tfb_backend.comment.CommentDao;
import com.grove.tfb_backend.comment.commentDto.CommentResponse;
import com.grove.tfb_backend.report.reportDto.ReportCloseRequest;
import com.grove.tfb_backend.report.reportDto.ReportRequest;
import com.grove.tfb_backend.report.reportDto.ReportResponseInstance;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.UsersDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportDao reportDao;
    private final CommentDao commentDao;
    private final UsersDao usersDao;

    public ReportService(ReportDao reportDao, CommentDao commentDao, UsersDao usersDao) {
        this.reportDao = reportDao;
        this.commentDao = commentDao;
        this.usersDao = usersDao;
    }

    public void handleReport(ReportRequest reportRequest) {
        if (!usersDao.existsById(reportRequest.getReporterId())) throw new IllegalStateException("REPORTER NOT FOUND!");
        if (!usersDao.existsById(reportRequest.getReportedUserId())) throw new IllegalStateException("REPORTED USER NOT FOUND!");
        if (!commentDao.existsById(reportRequest.getCommentId())) throw new IllegalStateException("COMMENT NOT FOUND!");

        Report report = new Report(reportRequest);
        reportDao.save(report);
    }

    public List<ReportResponseInstance> getActiveReports() {
        List<Report> reports = reportDao.findReportByActive(true);
        List<ReportResponseInstance> toBeReturned = new ArrayList<>();
        for (Report report: reports){
            CommentResponse comment = new CommentResponse(commentDao.findCommentById(report.getCommentId()));
            toBeReturned.add(new ReportResponseInstance(report,comment));
        }
        return toBeReturned;
    }

    @Transactional
    public void closeReport(ReportCloseRequest req) {
        Users user = usersDao.findUserById(req.getIssuerId());

        if (user == null || !user.isAdmin()) throw new IllegalStateException("NOT ENOUGH PERMISSIONS!");

        Report report = reportDao.findReportById(req.getId());

        if (report == null) throw new IllegalStateException("REPORT NOT FOUND!");

        report.setActive(false);


    }
}
