package com.kuza.kuzasokoni.domain.authentication.entity;

import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.common.enums.UserType;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_number")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable=false)
    private String secondName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 4)
    private String pin;

    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_user_types", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_type")
    private List<UserType> userType = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_entity_types", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "entity_type")
    private List<EntityType> entityType = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerified = VerificationStatus.VERIFIED;

    @Column(name = "password_expiry_date")
    private LocalDate passwordExpiryDate;

    @Column(name = "password_expired")
    private boolean passwordExpired = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEntity> userEntities = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Client client;
}