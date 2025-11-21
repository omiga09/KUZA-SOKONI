package com.kuza.kuzasokoni.domain.client.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "clients",
        indexes = {
                @Index(name = "idx_client_status", columnList = "status"),
                @Index(name = "idx_client_verification", columnList = "is_verified"),
                @Index(name = "idx_client_phone", columnList = "phone_number"),
                @Index(name = "idx_client_email", columnList = "email")
        }
)
public class Client extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Second name is required")
    private String secondName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^255\\d{9}$", message = "Phone number must start with 255 and contain 12 digits.")
    private String phoneNumber;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank
    private String gender;

    @NotBlank
    private String address;

    @Column(name = "enabled", columnDefinition = "BIT(1)")
    private Boolean enabled = true;

    @Column(name = "active", columnDefinition = "BIT(1)")
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerified = VerificationStatus.UNVERIFIED;

    @ElementCollection(targetClass = EntityType.class)
    @CollectionTable(name = "client_entity_types", joinColumns = @JoinColumn(name = "client_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private List<EntityType> entityTypes;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Documentation documentation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ElementCollection
    @CollectionTable(name = "client_guarantors", joinColumns = @JoinColumn(name = "client_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name")),
            @AttributeOverride(name = "relationship", column = @Column(name = "relationship")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number")),
            @AttributeOverride(name = "address", column = @Column(name = "address")),
            @AttributeOverride(name = "isVerified", column = @Column(name = "is_verified", columnDefinition = "BIT(1)"))
    })
    private List<Guarantor> guarantors;
}
