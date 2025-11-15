package com.kuza.kuzasokoni.domain.client.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documentation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 20)
    private String nidaNumber;

    @NotBlank
    private String kitambulishoType;

    @NotBlank
    private String kitambulishoFileName;

    @NotBlank
    private String baruaFileName;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;
}

