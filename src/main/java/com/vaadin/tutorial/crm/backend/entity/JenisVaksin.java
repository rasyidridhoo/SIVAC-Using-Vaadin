package com.vaadin.tutorial.crm.backend.entity;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
@Entity
public class JenisVaksin extends AbstractEntity {
  private String name;
  @OneToMany(mappedBy = "jenisVaksin", fetch = FetchType.EAGER)
  private List<Vaksinasi> employees = new LinkedList<>();
  public JenisVaksin() {
  }
  public JenisVaksin(String name) {
  setName(name);
  }
  public String getName() {
  return name;
  }
  public void setName(String name) {
  this.name = name;
  }
  public List<Vaksinasi> getEmployees() {
  return employees;
  }
}