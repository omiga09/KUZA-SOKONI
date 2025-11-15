package com.kuza.kuzasokoni.common.authentication.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "app_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String method;

    }



