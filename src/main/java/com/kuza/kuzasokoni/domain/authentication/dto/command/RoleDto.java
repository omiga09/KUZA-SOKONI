package com.kuza.kuzasokoni.domain.authentication.dto.command;

import com.kuza.kuzasokoni.common.utils.EntityType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleDto {
    private Long id;
    private String name;
    private EntityType entityType;
    private Set<PermissionDto> permissions = new HashSet<>();
    private Set<Long> permissionIds = new HashSet<>();

}