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
    private Button creerButton;

    private PatientDAO patientDAO;

    @FXML
    public void initialize(){
        patientDAO = new PatientDAO();
    }

    @FXML
    public void ajouterPatient() throws SQLException {
        boolean hasError = false;

        // Réinitialiser les styles
        nomField.setStyle("");
        prenomField.setStyle("");
        cnieField.setStyle("");
        motDePassField.setStyle("");

        if (nomField.getText().trim().isEmpty()) {
            nomField.setPromptText("Nom est obligatoire");
            nomField.setStyle("-fx-prompt-text-fill: red;");
            nomField.getStyleClass().add("field-error");
            hasError = true;
        }
        if (prenomField.getText().trim().isEmpty()) {
            prenomField.setPromptText("Prénom est obligatoire");
            prenomField.setStyle("-fx-prompt-text-fill: red;");
            prenomField.getStyleClass().add("field-error");
            hasError = true;
        }
        if (cnieField.getText().trim().isEmpty()) {
            cnieField.setPromptText("CNIE est obligatoire");
            cnieField.setStyle("-fx-prompt-text-fill: red;");
            cnieField.getStyleClass().add("field-error");
            hasError = true;
        }
        if (motDePassField.getText().trim().isEmpty()) {
            motDePassField.setPromptText("Mot de passe est obligatoire");
            motDePassField.setStyle("-fx-prompt-text-fill: red;");
            motDePassField.getStyleClass().add("field-error");
            hasError = true;
        }

        if (hasError) {
            return;
        }

        // Vérifier unicité CNIE
        if (patientDAO.trouverParCnie(cnieField.getText()) != null) {
            cnieField.clear();
            cnieField.setPromptText("CNIE déjà utilisée");
            cnieField.setStyle("-fx-prompt-text-fill: red;");
            cnieField.getStyleClass().add("field-error");
            return;
        }

        // Créer et ajouter le patient
        Patient patient = new Patient(
            nomField.getText(),
            prenomField.getText(),
            cnieField.getText(),
            motDePassField.getText()
        );
        patientDAO.ajouterPatient(patient);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Patient ajouté avec succès.");

        // Réinitialiser le formulaire
        nomField.clear();
        prenomField.clear();
        cnieField.clear();
        motDePassField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void allerVersLoginView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/connexion_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
