package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.RepaymentScheduleCommand;
import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleCommandServiceImpl implements RepaymentScheduleCommandService {

    private final RepaymentScheduleRepository scheduleRepository;
    private final LoanRepository loanRepository;

    @Override
    public RepaymentScheduleView create(RepaymentScheduleCommand command) {
        Loan loan = loanRepository.findById(command.loanId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        RepaymentSchedule schedule = new RepaymentSchedule();
        schedule.setLoan(loan);
        schedule.setDays(command.days());
        schedule.setExpectedDate(command.expectedDate());
        schedule.setPrincipalDue(command.principalDue());
        schedule.setOutstanding(command.outstanding());
        schedule.setInterest(command.interest());
        schedule.setFees(command.fees());
        schedule.setPenalty(command.penalty());

        BigDecimal totalDue = command.principalDue()
                .add(command.interest())
                .add(command.fees())
                .add(command.penalty());

        schedule.setTotalDue(totalDue);
        schedule.setTotalPaid(BigDecimal.ZERO);
        schedule.setStatus(ScheduleStatus.PENDING);
        schedule.setLateBy(0);

        RepaymentSchedule savedSchedule = scheduleRepository.save(schedule);
        loan.setRepaymentSchedule(savedSchedule);
        loanRepository.save(loan);


        return scheduleRepository.findProjectedById(savedSchedule.getId())
                .orElseThrow(() -> new EntityNotFoundException("RepaymentSchedule not found"));
    }

}
