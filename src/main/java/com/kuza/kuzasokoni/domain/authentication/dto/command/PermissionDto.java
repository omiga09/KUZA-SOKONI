package com.kuza.kuzasokoni.domain.authentication.dto.command;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private Long id;
    private String name;
    private String method;
    private String endpoint;

    private String description;


}