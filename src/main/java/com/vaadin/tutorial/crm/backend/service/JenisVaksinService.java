package com.vaadin.tutorial.crm.backend.service;
import com.vaadin.tutorial.crm.backend.entity.JenisVaksin;
import com.vaadin.tutorial.crm.backend.repository.JenisVaksinRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JenisVaksinService {
  private JenisVaksinRepository jenisVaksinRepository;
  public JenisVaksinService(JenisVaksinRepository jenisVaksinRepository) {
    this.jenisVaksinRepository = jenisVaksinRepository;
  }
  public List<JenisVaksin> findAll() {
    return jenisVaksinRepository.findAll();
  }

    public Map<String, Integer> getStats() {
      HashMap<String, Integer> stats = new HashMap<>();
      findAll().forEach(jenisVaksin ->
              stats.put(jenisVaksin.getName(), jenisVaksin.getEmployees().size()));
      return stats;
    }
}