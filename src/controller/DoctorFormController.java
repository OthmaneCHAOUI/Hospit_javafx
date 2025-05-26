package controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DoctorDAO;
import model.Doctor;

public class DoctorFormController {

    @FXML
    private Button btn_cree;

    @FXML
    private TextField f_addresse;

    @FXML
    private TextField f_cnie;

    @FXML
    private TextField f_motdepass;

    @FXML
    private TextField f_nom;

    @FXML
    private TextField f_nom_cabinet;

    @FXML
    private TextField f_prenom;

    @FXML
    private TextField f_specialite;

    @FXML
    private TextField f_telephone;
    
    @FXML
    private Button btn_retour;
    
    @FXML
    private Label label_error_reussi;
    
    @FXML
    void ButtonRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_view.fxml")) ;
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ButtonCree(ActionEvent event) throws SQLException {
        if(f_nom.getText().trim().isEmpty() || f_prenom.getText().trim().isEmpty() || f_specialite.getText().trim().isEmpty() || f_motdepass.getText().trim().isEmpty() || f_cnie.getText().trim().isEmpty()){ 
            if (f_nom.getText().trim().isEmpty()) {
                f_nom.setPromptText("Nom est obligatoire");
                f_nom.setStyle("-fx-prompt-text-fill : red");
                f_nom.getStyleClass().add("field-error");
            }
            if (f_prenom.getText().trim().isEmpty()) {
                f_prenom.setPromptText("Prenom est obligatoire");
                f_prenom.setStyle("-fx-prompt-text-fill : red");
                f_prenom.getStyleClass().add("field-error");
            }
            if (f_specialite.getText().trim().isEmpty()) {
                f_specialite.setPromptText("Spécialité est obligatoire");
                f_specialite.setStyle("-fx-prompt-text-fill : red");
                f_specialite.getStyleClass().add("field-error");
            }
            if (f_motdepass.getText().trim().isEmpty()) {
                f_motdepass.setPromptText("Mot de pass est obligatoire");
                f_motdepass.setStyle("-fx-prompt-text-fill : red");
                f_motdepass.getStyleClass().add("field-error");
            }
            if (f_cnie.getText().trim().isEmpty()) {
                f_cnie.setPromptText("CNIE est obligatoire");
                f_cnie.setStyle("-fx-prompt-text-fill : red");
                f_cnie.getStyleClass().add("field-error");
            }
            if (f_nom_cabinet.getText().trim().isEmpty()) {
                f_nom_cabinet.setPromptText("Nom de cabinet est obligatoire");
                f_nom_cabinet.setStyle("-fx-prompt-text-fill : red");
                f_nom_cabinet.getStyleClass().add("field-error");
            }
        }else{
        Doctor d = new Doctor(f_nom.getText(),f_prenom.getText(),f_cnie.getText(),f_specialite.getText(),f_nom_cabinet.getText(),f_telephone.getText(),f_addresse.getText(),f_motdepass.getText());
        System.out.println(f_nom.getText()+f_prenom.getText()+f_cnie.getText()+f_specialite.getText()+f_nom_cabinet.getText()+f_telephone.getText()+f_addresse.getText()+f_motdepass.getText());
         if(DoctorDAO.inserer(d) == 1){
             label_error_reussi.setStyle("-fx-opacity : 1.0;-fx-text-fill: green");
             label_error_reussi.setText("Compte créé avec succès !");
             
             PauseTransition pause = new PauseTransition(Duration.seconds(2));
             pause.setOnFinished(e -> {
                 try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml")) ;
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
         else{
             label_error_reussi.setStyle("-fx-opacity : 1.0;-fx-text-fill: red");
             label_error_reussi.setText("Erreur lors de la création du compte. Veuillez réessayer.");
         }
        }
    }

}
