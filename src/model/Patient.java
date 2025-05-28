package model;

public class Patient {
    private int id;
    private String nom, prenom, cnie, motDePasse;

    public Patient() {}

    public Patient(String nom, String prenom, String cnie, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.cnie = cnie;
        this.motDePasse = motDePasse;
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
}