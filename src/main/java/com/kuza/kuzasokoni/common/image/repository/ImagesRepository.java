package com.kuza.kuzasokoni.common.image.repository;

import com.kuza.kuzasokoni.common.audit.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images,Long> {

}
