package controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Patient;
import model.PatientDAO;

public class PatientFormController {

    @FXML
    private Button btn_retour;

    @FXML
    private TextField cnieField;

    @FXML
    private Button creerButton;

    @FXML
    private Label label_error_reussi;

    @FXML
    private PasswordField motDePassField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    void ButtonCree(ActionEvent event) throws SQLException {
        if(nomField.getText().trim().isEmpty() || prenomField.getText().trim().isEmpty() || cnieField.getText().trim().isEmpty() || motDePassField.getText().trim().isEmpty()){ 
            if (nomField.getText().trim().isEmpty()) {
                nomField.setPromptText("Nom est obligatoire");
                nomField.setStyle("-fx-prompt-text-fill : red");
                nomField.getStyleClass().add("field-error");
            }
            if (prenomField.getText().trim().isEmpty()) {
                prenomField.setPromptText("Prenom est obligatoire");
                prenomField.setStyle("-fx-prompt-text-fill : red");
                prenomField.getStyleClass().add("field-error");
            }
            if (motDePassField.getText().trim().isEmpty()) {
                motDePassField.setPromptText("Mot de pass est obligatoire");
                motDePassField.setStyle("-fx-prompt-text-fill : red");
                motDePassField.getStyleClass().add("field-error");
            }
            if (cnieField.getText().trim().isEmpty()) {
                cnieField.setPromptText("CNIE est obligatoire");
                cnieField.setStyle("-fx-prompt-text-fill : red");
                cnieField.getStyleClass().add("field-error");
            }
        }else{
        Patient patient = new Patient(nomField.getText(),prenomField.getText(),cnieField.getText(),motDePassField.getText());
        System.out.println(nomField.getText()+prenomField.getText()+cnieField.getText()+motDePassField.getText());
        int etat = PatientDAO.inserer(patient);
         if( etat == 1){
             label_error_reussi.setStyle("-fx-opacity : 1.0;-fx-text-fill: green");
             label_error_reussi.setText("Compte créé avec succès !");
             
             PauseTransition pause = new PauseTransition(Duration.seconds(2));
             pause.setOnFinished(e -> {
                 try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login_view.fxml")) ;
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                 }catch(IOException ex){
                     ex.printStackTrace();
                 }
             });
             pause.play();
         }
         else if(etat == -1){
             label_error_reussi.setStyle("-fx-opacity : 1.0;-fx-text-fill: red");
             label_error_reussi.setText("Un compte existe déjà avec ce CNIE.");
         }else{
             label_error_reussi.setStyle("-fx-opacity : 1.0;-fx-text-fill: red");
             label_error_reussi.setText("Erreur lors de la création du compte. Veuillez réessayer.");
         }
        }
    }

    @FXML
    void ButtonRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_view.fxml")) ;
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
