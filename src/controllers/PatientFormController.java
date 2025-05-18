package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Patient;
import models.PatientDAO;

public class PatientFormController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField cnieField;
    @FXML private PasswordField motDePassField;
    @FXML private DatePicker dateNaissancePicker;
    @FXML private Button creerButton;

    private PatientDAO patientDAO = new PatientDAO();

    @FXML
    private void initialize() {
        creerButton.setOnAction(event -> ajouterPatient());
    }

    private void ajouterPatient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String cnie = cnieField.getText();
        String motDePasse = motDePassField.getText();
        var dateNaissance = dateNaissancePicker.getValue();

        Patient patient = new Patient(0, nom, prenom, cnie, motDePasse, dateNaissance);
        patientDAO.addPatient(patient);
    }
}