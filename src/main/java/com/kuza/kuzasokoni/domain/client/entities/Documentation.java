package com.kuza.kuzasokoni.domain.client.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documentation {
    private String nidaNumber;
    private String baruaReference;
    private String kitambulishoType;
    private String kitambulishoNumber;


}
