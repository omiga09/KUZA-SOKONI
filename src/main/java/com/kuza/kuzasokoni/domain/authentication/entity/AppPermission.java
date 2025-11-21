package com.kuza.kuzasokoni.domain.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_permission", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    private String endpoint;

    @Column(nullable = false, unique = true)
    private String method;

    private String description;
}



