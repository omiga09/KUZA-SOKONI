package com.kuza.kuzasokoni.domain.client.dtos.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuarantorUpdateCommand {
    private Long clientId;
    private List<GuarantorCreateCommand> guarantors;
}
