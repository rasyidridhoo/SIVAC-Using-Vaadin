package com.vaadin.tutorial.crm.backend.entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Entity
public class Vaksinasi extends AbstractEntity implements Cloneable {
  public enum Status {
  DosisPertama,DosisKedua
  }
  @NotNull
  @NotEmpty
  private String nama = "";
  @NotNull
  @NotEmpty
  private String umur = "";
  @NotNull
  @NotEmpty
  private String nik = "";
  @NotNull
  @NotEmpty
  private String waktu = "";
  @ManyToOne
  @JoinColumn(name = "jenisVaksin_id")
  private JenisVaksin jenisVaksin;
  @Enumerated(EnumType.STRING)
  @NotNull
  private Vaksinasi.Status status;
  @Email
  @NotNull
  @NotEmpty
  private String email = "";
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
  this.status = status;
  }
  public String getnik() {
  return nik;
  }
  public void setnik(String nik) {
    this.nik = nik;
  }
  public String getWaktu() {
    return waktu;
  }public void setwaktu(String waktu) {
    this.waktu = waktu;
  }
  public String getumur() {
        return umur;
    }public void setumur(String umur) {
        this.umur = umur;
    }
  public String getnama() {
    return nama;
  }
  public void setnama(String nama) {
    this.nama = nama;
  }
  public void setJenisVaksin(JenisVaksin jenisVaksin) {
    this.jenisVaksin = jenisVaksin;
  }
  public JenisVaksin getJenisVaksin() {
    return jenisVaksin;
  }
  @Override
  public String toString() {
    return nama + " " + nik;
  }
}