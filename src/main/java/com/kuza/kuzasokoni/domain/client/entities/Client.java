package com.kuza.kuzasokoni.domain.client.entities;

import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.common.audit.Images;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "clients",
        indexes = {
                @Index(name = "idx_client_status", columnList = "status"),
                @Index(name = "idx_client_verification", columnList = "is_verified")
        }
)
public class Client extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String secondName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerified;


    // fix relations and liquibase

    @Column(nullable = false)
    private List<EntityType> entityTypes;

    @Embedded
    private Documentation documentation;


    @ElementCollection
    @CollectionTable(name = "client_guarantors", joinColumns = @JoinColumn(name = "client_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number")),
            @AttributeOverride(name = "isVerified", column = @Column(name = "is_verified"))
    })
    private List<Guarantor> guarantors = new ArrayList<>();

}
