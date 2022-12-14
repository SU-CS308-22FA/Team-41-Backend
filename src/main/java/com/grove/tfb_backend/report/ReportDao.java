package com.grove.tfb_backend.report;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    List<Report> findReportByActive(boolean active);
    Report findReportById(Long id);
}
