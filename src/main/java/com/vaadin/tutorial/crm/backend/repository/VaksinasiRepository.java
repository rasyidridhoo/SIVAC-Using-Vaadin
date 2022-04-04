package com.vaadin.tutorial.crm.backend.repository;

import com.vaadin.tutorial.crm.backend.entity.Vaksinasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VaksinasiRepository extends JpaRepository<Vaksinasi, Long> {
    @Query("select c from Vaksinasi c " +
            "where lower(c.nama) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(c.umur) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(c.nik) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(c.waktu) like lower(concat('%', :searchTerm, '%'))")
    List<Vaksinasi> search(@Param("searchTerm") String searchTerm);
}