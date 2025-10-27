package com.kuza.kuzasokoni.domain.client.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kuza.kuzasokoni.common.audit.Auditable;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Second name is required")
    private String secondName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^255\\d{9}$", message = "Guarantor's phone number must start with 255 and contain 12 digits.")
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


    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerified = VerificationStatus.UNVERIFIED;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Documentation documentation;

    @ElementCollection
    @CollectionTable(name = "client_guarantors", joinColumns = @JoinColumn(name = "client_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number")),
            @AttributeOverride(name = "isVerified", column = @Column(name = "is_verified"))
    })
    private List<Guarantor> guarantors = new ArrayList<>();

}
