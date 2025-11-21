package com.kuza.kuzasokoni.domain.authentication.dto.command;

import com.kuza.kuzasokoni.common.enums.UserType;
import com.kuza.kuzasokoni.common.utils.EntityType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateUserEntityRequest {

    private Long userId;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private Long entityId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserType> userType;

    private List<Long> roles;
}