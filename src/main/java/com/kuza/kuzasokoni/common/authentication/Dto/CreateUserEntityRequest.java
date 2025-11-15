package com.kuza.kuzasokoni.common.authentication.Dto;

import com.kuza.kuzasokoni.common.utils.EntityType;
import lombok.Getter;
import lombok.Setter;

// CreateUserEntityRequest.java
@Getter
@Setter
public class CreateUserEntityRequest {
    private Long userId;
    private EntityType entityType;
    private Long entityId;
    private String roleName; // e.g., "ADMIN", "CUSTOMER"
}