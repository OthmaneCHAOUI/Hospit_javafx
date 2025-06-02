package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVous {
    private int id, idDoctor;
    private String cniePatient, raison, statut;
    private LocalDate dateRendezVous;
    private LocalTime heure;

    public RendezVous() {}

    public RendezVous(int id, int idDoctor, String cniePatient, LocalDate dateRendezVous, LocalTime heure, String raison, String statut) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.cniePatient = cniePatient;
        this.dateRendezVous = dateRendezVous;
        this.heure = heure;
        this.raison = raison;
        this.statut = statut;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }
    public String getCniePatient() { return cniePatient; }
    public void setCniePatient(String cniePatient) { this.cniePatient = cniePatient; }
    public LocalDate getDateRendezVous() { return dateRendezVous; }
    public void setDateRendezVous(LocalDate dateRendezVous) { this.dateRendezVous = dateRendezVous; }
    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}