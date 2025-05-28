package model;

/**
 *
 * @author BENDAMOU
 */
public class Doctor {
    private int id;
    private String nom;
    private String prenom;
    private String cnie;
    private String specialite;
    private String nomCabinet;
    private String telephone;
    private String adresseCabinet;
    // private String numeroInscription;
    private String motDePasse;

    public Doctor(){}

    public Doctor(String nom, String prenom, String cnie, String specialite, String nomCabinet, String telephone, String adresseCabinet, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.cnie = cnie;
        this.specialite = specialite;
        this.nomCabinet = nomCabinet;
        this.telephone = telephone;
        this.adresseCabinet = adresseCabinet;
        this.motDePasse = motDePasse;
    }
// ...existing code...

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getCnie() { return cnie; }
    public void setCnie(String cnie) { this.cnie = cnie; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getNomCabinet() { return nomCabinet; }
    public void setNomCabinet(String nomCabinet) { this.nomCabinet = nomCabinet; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresseCabinet() { return adresseCabinet; }
    public void setAdresseCabinet(String adresseCabinet) { this.adresseCabinet = adresseCabinet; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
