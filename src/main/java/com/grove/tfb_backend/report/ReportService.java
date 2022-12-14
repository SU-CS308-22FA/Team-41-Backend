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

    /**
     * Handles a report request.
     *
     * @param reportRequest the report request to handle
     *
     * @throws IllegalStateException if the reporter or reported user or comment is not found, or if the reporter has already
     *                               reported the comment, or if the reporter is the same as the reported user
     */
    public void handleReport(ReportRequest reportRequest) {
        if (!usersDao.existsById(reportRequest.getReporterId())) throw new IllegalStateException("REPORTER NOT FOUND!");
        if (!usersDao.existsById(reportRequest.getReportedUserId())) throw new IllegalStateException("REPORTED USER NOT FOUND!");
        if (!commentDao.existsById(reportRequest.getCommentId())) throw new IllegalStateException("COMMENT NOT FOUND!");
        if (reportDao.findReportByReporterIdAndCommentId(reportRequest.getReporterId(), reportRequest.getCommentId()) != null) throw new IllegalStateException("ALREADY REPORTED BY YOU!");
        if(reportRequest.getReporterId().equals(reportRequest.getReportedUserId())) throw new IllegalStateException("YOU CANNOT REPORT YOUR OWN COMMENT!");

        Report report = new Report(reportRequest);
        reportDao.save(report);
    }

    /**
     * Returns a list of active reports.
     *
     * @return a list of active reports, each containing the report and the corresponding comment
     */
    public List<ReportResponseInstance> getActiveReports() {
        List<Report> reports = reportDao.findReportByIsActiveTrue();
        List<ReportResponseInstance> toBeReturned = new ArrayList<>();
        for (Report report: reports){
            CommentResponse comment = new CommentResponse(commentDao.findCommentById(report.getCommentId()));
            toBeReturned.add(new ReportResponseInstance(report,comment));
        }
        return toBeReturned;
    }

    /**
     * Closes a report.
     *
     * @param req the request to close a report, containing the issuer's ID and the report's ID
     *
     * @throws IllegalStateException if the issuer is not an admin or the report is not found
     */
    @Transactional
    public void closeReport(ReportCloseRequest req) {
        Users user = usersDao.findUserById(req.getIssuerId());

        if (user == null || !user.isAdmin()) throw new IllegalStateException("NOT ENOUGH PERMISSIONS!");

        Report report = reportDao.findReportById(req.getId());

        if (report == null) throw new IllegalStateException("REPORT NOT FOUND!");

        report.setActive(false);


    }
}
