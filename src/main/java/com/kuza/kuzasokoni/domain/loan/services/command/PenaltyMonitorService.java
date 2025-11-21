package com.kuza.kuzasokoni.domain.loan.services.command;

import org.springframework.data.domain.Pageable;

public interface PenaltyMonitorService {
    void checkLateInstallments();
}
