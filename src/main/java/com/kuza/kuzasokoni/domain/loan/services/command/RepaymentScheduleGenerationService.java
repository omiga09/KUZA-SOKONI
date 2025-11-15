package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.UpdateRepaymentScheduleCommand;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;

import java.time.LocalDate;
import java.util.List;

public interface RepaymentScheduleGenerationService {
    List<RepaymentSchedule> generateSchedule(Loan loan, LocalDate repaymentStartDate);
    void updateRepaymentSchedule(UpdateRepaymentScheduleCommand cmd);
}
