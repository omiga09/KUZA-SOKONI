package com.kuza.kuzasokoni.domain.client.repositories;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("""
    SELECT c.id AS id,
           c.firstName AS firstName,
           c.secondName AS secondName,
           c.lastName AS lastName,
           c.phoneNumber AS phoneNumber,
           c.email AS email,
           c.dateOfBirth AS dateOfBirth,
           c.gender AS gender,
           c.status AS status,
           c.isVerified AS isVerified
    FROM Client c
""")
    List<ClientView> findAllClientViews();

    @Query("""
    SELECT c.id AS id,
           c.firstName AS firstName,
           c.secondName AS secondName,
           c.lastName AS lastName,
           c.phoneNumber AS phoneNumber,
           c.email AS email,
           c.dateOfBirth AS dateOfBirth,
           c.gender AS gender,
           c.status AS status,
           c.isVerified AS isVerified
    FROM Client c
    WHERE c.id = :id
""")
    Optional<ClientView> findClientViewById(@Param("id") Long id);

    @Query("""
        SELECT c.id AS id,
               c.firstName AS firstName,
               c.guarantors AS guarantors
        FROM Client c
        WHERE c.id = :id
    """)
    Optional<ClientGuarantorView> findClientGuarantors(@Param("id") Long id);

    @Query("SELECT c.documentation FROM Client c WHERE c.id = :id")
    Optional<DocumentationView> findDocumentationByClientId(Long id);
}
