package com.backend.leavemanagementsystem.controller;

import com.backend.leavemanagementsystem.dto.*;
import com.backend.leavemanagementsystem.service.HrService;
import com.backend.leavemanagementsystem.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hr")
public class HrController {

    private final JwtService jwtService;
    private final HrService hrService;

    public HrController(JwtService jwtService, HrService hrService) {
        this.jwtService = jwtService;
        this.hrService = hrService;
    }

    //    @GetMapping("/check")
    //    public String checkHrAccess(HttpServletRequest request) {
    //        // Grab "Authorization: Bearer <token>"
    //        String authHeader = request.getHeader("Authorization");
    //        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    //            return "❌ No JWT found in request";
    //        }
    //
    //        String token = authHeader.substring(7); // remove "Bearer "
    //        String username = jwtService.extractUsername(token);
    //
    //        return "✅ JWT is valid. Logged in as: " + username;
    //    }

    @PostMapping("/add-employee")
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto request) {
        return ResponseEntity.ok(hrService.addEmployee(request));
    }

    @PostMapping("/apply-leave")
    public ResponseEntity<LeaveRequestResponseDto> applyLeave(
            @RequestBody LeaveRequestDto leaveRequestDto, HttpServletRequest request) {
        String username = extractUsernameFromRequest(request);
        if (username == null) {
            return ResponseEntity.status(401).build();
        }

        LeaveRequestResponseDto response = hrService.applyLeave(leaveRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/leave/update-status")
    public ResponseEntity<LeaveRequestResponseDto> updateLeaveStatus(@RequestBody LeaveStatusUpdateRequest request) {
        return ResponseEntity.ok(hrService.updateLeaveStatus(request));
    }

    @GetMapping("/employee/{id}/leave-balance")
    public ResponseEntity<LeaveBalanceResponseDto> getEmployeeLeaveBalance(@PathVariable("id") Long employeeId) {
        return ResponseEntity.ok(hrService.getLeaveBalance(employeeId));
    }

    private String extractUsernameFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
    }
}
