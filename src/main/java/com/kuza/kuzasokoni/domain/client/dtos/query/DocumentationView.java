package com.kuza.kuzasokoni.domain.client.dtos.query;

import com.kuza.kuzasokoni.domain.loan.enums.DocumentStatus;

public interface DocumentationView {
    String getNidaNumber();
    String getBaruaReference();
    String getKitambulishoType();
    String getKitambulishoNumber();
    DocumentStatus getStatus();
    Boolean getIsVerified();
}
