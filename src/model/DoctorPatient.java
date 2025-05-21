package models;

import java.time.LocalDate;

public class DoctorPatient {
    private int id;
    private int idDoctor;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String cnie;
    private String ville;
    private String telephone;
    private String adresse;
    private String sexe;

    public DoctorPatient() {}

    public DoctorPatient(int id, int idDoctor, String nom, String prenom, LocalDate dateNaissance, String cnie, String ville, String telephone, String adresse, String sexe) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.cnie = cnie;
        this.ville = ville;
        this.telephone = telephone;
        this.adresse = adresse;
        this.sexe = sexe;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getCnie() { return cnie; }
    public void setCnie(String cnie) { this.cnie = cnie; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
}