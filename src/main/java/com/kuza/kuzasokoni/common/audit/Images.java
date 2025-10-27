package com.kuza.kuzasokoni.common.audit;


import com.kuza.kuzasokoni.common.utils.EntityType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Builder
@Setter
@Getter
public class Images extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String path;


    @Column(nullable = false)
    private EntityType entityType;


    @Column(nullable = false)
    private Long clientId;
}
