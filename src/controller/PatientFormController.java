package controller;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.Patient;
import model.PatientDAO;

import java.time.LocalDate;

public class PatientFormController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField cnieField;

    @FXML
    private PasswordField motDePassField;

    @FXML
    private DatePicker dateNaissancePicker;

    @FXML
    private Button creerButton;

    private PatientDAO patientDAO;

    public PatientFormController() {
        patientDAO = new PatientDAO();
    }

    @FXML
    public void ajouterPatient() throws SQLException {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String cnie = cnieField.getText().trim();
        String motDePasse = motDePassField.getText().trim();
        LocalDate dateNaissance = dateNaissancePicker.getValue();

        // Validation simple
        if (nom.isEmpty() || prenom.isEmpty() || cnie.isEmpty() || motDePasse.isEmpty() || dateNaissance == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier unicité CNIE
        if (patientDAO.findByCnie(cnie) != null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Un patient avec cette CNIE existe déjà.");
            return;
        }

        // Créer et ajouter le patient
        Patient patient = new Patient(nom, prenom, cnie, motDePasse);
        patientDAO.addPatient(patient);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Patient ajouté avec succès.");

        // Réinitialiser le formulaire
        nomField.clear();
        prenomField.clear();
        cnieField.clear();
        motDePassField.clear();
        dateNaissancePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToLoginView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}