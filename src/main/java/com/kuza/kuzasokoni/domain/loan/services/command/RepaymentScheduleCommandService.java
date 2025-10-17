package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.RepaymentScheduleCommand;
import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;

public interface RepaymentScheduleCommandService {
    RepaymentScheduleView create(RepaymentScheduleCommand command);

}
