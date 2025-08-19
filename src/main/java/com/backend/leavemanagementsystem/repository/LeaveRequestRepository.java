package com.backend.leavemanagementsystem.repository;

import com.backend.leavemanagementsystem.entity.Employee;
import com.backend.leavemanagementsystem.entity.LeaveRequest;
import com.backend.leavemanagementsystem.utils.LeaveStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query(
            """
        SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
        FROM LeaveRequest l
        WHERE l.employee = :employee
        AND l.status IN :statuses
        AND l.startDate <= :endDate
        AND l.endDate >= :startDate
    """)
    boolean existsByEmployeeAndStatusInAndDateRange(
            @Param("employee") Employee employee,
            @Param("statuses") List<LeaveStatus> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
