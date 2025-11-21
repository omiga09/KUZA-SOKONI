package com.kuza.kuzasokoni.domain.client.repositories;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
       c.submittedAt AS submittedAt,
       c.entityTypes AS entityTypes
FROM Client c
""")
    Page<ClientView> findAllClientViews(Pageable pageable);

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
       c.submittedAt AS submittedAt,
       c.entityTypes AS entityTypes
FROM Client c
WHERE 
    LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
    OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
    OR LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))
    OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    Page<ClientView> searchClientViews(@Param("search") String search, Pageable pageable);

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
       c.submittedAt AS submittedAt,
       c.entityTypes AS entityTypes
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


    @Query("""
SELECT c.id AS id,
       c.firstName AS firstName,
       g AS guarantors
FROM Client c
LEFT JOIN c.guarantors g
WHERE c.id = :clientId
""")
    Optional<ClientGuarantorView> findClientGuarantors(@Param("clientId") Long clientId);

    Page<Client> findAllByActive(Boolean active, Pageable pageable);

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
       c.submittedAt AS submittedAt,
       c.entityTypes AS entityTypes
FROM Client c
WHERE c.phoneNumber = :phoneNumber
""")
    Optional<ClientView> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
