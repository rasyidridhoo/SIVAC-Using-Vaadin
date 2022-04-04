package com.vaadin.tutorial.crm.backend.repository;

import com.vaadin.tutorial.crm.backend.entity.JenisVaksin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JenisVaksinRepository extends JpaRepository<JenisVaksin, Long> {
}
