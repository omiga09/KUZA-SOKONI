package com.kuza.kuzasokoni.domain.loan.controllers.command;


import com.kuza.kuzasokoni.domain.loan.dtos.command.RepaymentScheduleCommand;
import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.services.command.RepaymentScheduleCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/repayment-schedules")
@RequiredArgsConstructor
public class RepaymentScheduleCommandController {

    private final RepaymentScheduleCommandService service;

    @PostMapping
    public ResponseEntity<RepaymentScheduleView> create(@RequestBody RepaymentScheduleCommand command) {
        RepaymentScheduleView savedRepaymentScheduleView = service.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRepaymentScheduleView);
    }

}
