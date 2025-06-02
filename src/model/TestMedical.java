package model;

import java.time.LocalDate;

public class TestMedical {
    private int id,idDoctor;
    private String cniePatient, typeTest, resultat, typeDoctor, statut;
    private LocalDate dateTest;

    public TestMedical(String string, String string1, LocalDate toLocalDate, String string2) {}
public TestMedical(){}
    public TestMedical(int id,String typeTest, String resultat, LocalDate dateTest, String typeDoctor, String statut) {
        this.id = id ;
        this.typeTest = typeTest;
        this.resultat = resultat;
        this.dateTest = dateTest;
        this.typeDoctor = typeDoctor;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }
    public String getCniePatient() { return cniePatient; }
    public void setCniePatient(String cniePatient) { this.cniePatient = cniePatient; }
    public String getTypeTest() { return typeTest; }
    public void setTypeTest(String typeTest) { this.typeTest = typeTest; }
    public String getResultat() { return resultat; }
    public void setResultat(String resultat) { this.resultat = resultat; }
    public LocalDate getDateTest() { return dateTest; }
    public void setDateTest(LocalDate dateTest) { this.dateTest = dateTest; }
    public String getTypeDoctor() { return typeDoctor; }
    public void setTypeDoctor(String typeDoctor) { this.typeDoctor = typeDoctor; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}