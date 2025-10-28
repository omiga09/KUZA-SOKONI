package com.kuza.kuzasokoni.domain.client.repositories;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.dtos.query.GuarantorView;
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
       c.address AS address,
       c.status AS status,
       c.isVerified AS isVerified,
       c.createdAt AS createdAt,
       c.updatedAt AS updatedAt,
       c.submittedAt AS submittedAt
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
       c.address AS address,
       c.status AS status,
       c.isVerified AS isVerified,
       c.createdAt AS createdAt,
       c.updatedAt AS updatedAt,
       c.submittedAt AS submittedAt
FROM Client c
WHERE c.id = :id
""")
    Optional<ClientView> findClientViewById(@Param("id") Long id);

    @Query("""
SELECT d.id AS id,
       d.nidaNumber AS nidaNumber,
       d.kitambulishoType AS kitambulishoType,
       d.kitambulishoFileName AS kitambulishoFileName,
       d.baruaFileName AS baruaFileName
FROM Client c
JOIN c.documentation d
WHERE c.id = :clientId
""")
    Optional<DocumentationView> findDocumentationViewByClientId(@Param("clientId") Long clientId);

   // @Query("SELECT g.name AS name, g.relationship AS relationship, g.phoneNumber AS phoneNumber, g.address AS address, g.isVerified AS isVerified FROM Guarantor g WHERE g.client.id = :clientId")
   // List<GuarantorView> findGuarantorsByClientId(@Param("clientId") Long clientId);

    @Query("""
SELECT c.id AS id,
       c.firstName AS firstName,
       g AS guarantors
FROM Client c
LEFT JOIN c.guarantors g
WHERE c.id = :clientId
""")
    Optional<ClientGuarantorView> findClientGuarantors(@Param("clientId") Long clientId);


}
