package com.kuza.kuzasokoni.common.authentication.entity;

import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String phoneNumber
                ;
        private String password;

        @Column(nullable = false, length = 4)
        private String pin;

        @Enumerated(EnumType.STRING)
        private VerificationStatus isVerified = VerificationStatus.UNVERIFIED;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<UserEntity> userEntities = new ArrayList<>();
    }
