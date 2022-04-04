package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.JenisVaksin;
import com.vaadin.tutorial.crm.backend.entity.Vaksinasi;
import com.vaadin.tutorial.crm.backend.repository.JenisVaksinRepository;
import com.vaadin.tutorial.crm.backend.repository.VaksinasiRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VaksinasiService {
  private static final Logger LOGGER = Logger.getLogger(VaksinasiService.class.getName());
  private VaksinasiRepository vaksinasiRepository;
  private JenisVaksinRepository jenisVaksinRepository;
  public VaksinasiService(VaksinasiRepository vaksinasiRepository, JenisVaksinRepository jenisVaksinRepository) {
  this.vaksinasiRepository = vaksinasiRepository;
  this.jenisVaksinRepository = jenisVaksinRepository;
  }
  public List<Vaksinasi> findAll() {

      return vaksinasiRepository.findAll();
  }
    public List<Vaksinasi> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return vaksinasiRepository.findAll();
        } else {
            return vaksinasiRepository.search(filterText);
        }
    }
  public long count() {

      return vaksinasiRepository.count();
  }
  public void delete(Vaksinasi vaksinasi) {

      vaksinasiRepository.delete(vaksinasi);
  }
  public void save(Vaksinasi vaksinasi) {
  if (vaksinasi == null) {
  LOGGER.log(Level.SEVERE,
  "Data Vaksinasi kosong. Apakah kamu yakin telah menghubungkan form dengan aplikasi ?");
  return;
  }
  vaksinasiRepository.save(vaksinasi);
  }
  @PostConstruct
  public void populateTestData() {
    if (jenisVaksinRepository.count() == 0) {
      jenisVaksinRepository.saveAll(
              Stream.of("Sinovac", "Moderna", "AstraZeneca")
                              .map(JenisVaksin::new)
                              .collect(Collectors.toList()));
    }
    if (vaksinasiRepository.count() == 0) {
      Random r = new Random(0);
      List<JenisVaksin> companies = jenisVaksinRepository.findAll();
    }
  }
}