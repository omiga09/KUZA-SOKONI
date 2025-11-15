package com.kuza.kuzasokoni.common.authentication.entity;

import com.kuza.kuzasokoni.common.utils.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private EntityType entityType;//SOKONI,KILIMO,BODA

        private Long entityId;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_entity_roles",
                joinColumns = @JoinColumn(name = "user_entity_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;
    }

