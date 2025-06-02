package model;

import java.time.LocalDate;

public class Medicament {
    private int id, idDoctor, periode;
    private String cniePatient, nom, description;
    private LocalDate dateDebut;

    public Medicament() {}

    public Medicament(int id,String nom, LocalDate dateDebut, int periode, String description) {
        this.id = id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.periode = periode;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }
    public String getCniePatient() { return cniePatient; }
    public void setCniePatient(String cniePatient) { this.cniePatient = cniePatient; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public int getPeriode() { return periode; }
    public void setPeriode(int periode) { this.periode = periode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}