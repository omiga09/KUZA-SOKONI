package com.kuza.kuzasokoni.domain.client.dtos.query;


import java.util.List;

public interface ClientGuarantorView {
    Long getId();
    String getFirstName();
    List<GuarantorView> getGuarantors();
}