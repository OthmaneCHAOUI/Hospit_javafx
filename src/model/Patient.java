package model;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String nom, prenom, cnie, motDePasse, ville, telephone, adresse, sexe, compteCree, email;
    private LocalDate dateNaissance;

    public Patient() {}

    public Patient(String cnie, String motDePasse) {
        this.cnie = cnie;
        this.motDePasse = motDePasse;
    }

    public Patient(String nom, String prenom, String cnie, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.cnie = cnie;
        this.motDePasse = motDePasse;
    }

    public Patient(String nom, String prenom, String cnie, String motDePasse, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.cnie = cnie;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
    }

    public Patient(String nom, String prenom, String cnie, String motDePasse, String ville, String telephone, String adresse, String sexe, String compteCree, String email, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.cnie = cnie;
        this.motDePasse = motDePasse;
        this.ville = ville;
        this.telephone = telephone;
        this.adresse = adresse;
        this.sexe = sexe;
        this.compteCree = compteCree;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getCnie() { return cnie; }
    public void setCnie(String cnie) { this.cnie = cnie; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public LocalDate getDateNaissance() {return dateNaissance;}
    public void setDateNaissance(LocalDate dateNaissance) {this.dateNaissance = dateNaissance;}

    public String getVille() {return ville;}
    public void setVille(String ville) {this.ville = ville;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getCompteCree() { return compteCree; }
    public void setCompteCree(String compteCree) { this.compteCree = compteCree; }

    public int getAge() {
        if (dateNaissance == null) return 0;
        return java.time.Period.between(dateNaissance, java.time.LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        if (dateNaissance != null) {
            // Met à jour la date de naissance en fonction de l'âge donné (approximatif, au 1er janvier)
            this.dateNaissance = java.time.LocalDate.now().minusYears(age);
        }
    }
}