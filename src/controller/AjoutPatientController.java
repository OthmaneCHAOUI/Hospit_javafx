package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DoctorPatient;
import model.DoctorPatientDAO;

public class AjoutPatientController {

    @FXML private TextField fieldNom;
    @FXML private TextField fieldPrenom;
    @FXML private DatePicker fieldDateNaissance;
    @FXML private TextField fieldCnie;
    @FXML private TextField fieldVille;
    @FXML private TextField fieldTelephone;
    @FXML private TextField fieldAdresse;
    @FXML private ComboBox<String> fieldSexe;

    private int idDoctor;

    public void setIdDoctor(int id) {
        this.idDoctor = id;
    }

    @FXML
    public void handleAjouter() {
        DoctorPatient newPatient = new DoctorPatient(
                fieldNom.getText(),
                fieldPrenom.getText(),
                fieldDateNaissance.getValue(),
                fieldCnie.getText(),
                fieldVille.getText(),
                fieldTelephone.getText(),
                fieldAdresse.getText(),
                fieldSexe.getValue()
        );

        DoctorPatientDAO.inserer(newPatient, idDoctor);

        Stage stage = (Stage) fieldNom.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCancel() {
        Stage stage = (Stage) fieldNom.getScene().getWindow();
        stage.close();
    }
}
