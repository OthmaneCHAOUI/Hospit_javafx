package controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Doctor;
import model.DoctorDAO;
import model.Patient;
import model.PatientDAO;

public class LoginController {

    @FXML
    private Button btn_connexion;

    @FXML
    private TextField f_cnie;

    @FXML
    private PasswordField f_password;

    @FXML
    private RadioButton rd_doctor;

    @FXML
    private RadioButton rd_patient;

    @FXML
    private ToggleGroup type;
    
    @FXML
    private Hyperlink hyper_doctor;

    @FXML
    private Hyperlink hyper_patient;
    
     @FXML
    private Label label_incorrect;
    
    
    @FXML
    public void GoToFormDocteur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/doctor_form.fxml")) ;
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void GoToFormPatient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patient_form.fxml")) ;
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void goToPatientDashboard(ActionEvent event, Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patient_dashboard_view.fxml"));
        Parent root = loader.load();

        // Récupération du contrôleur et passage du patient
        PatientDashboardController controller = loader.getController();
        controller.setPatient(patient); // méthode à créer dans PatientDashboardController

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
     public void goToDoctorDashboard(ActionEvent event, int idDoctor,String nomDoctor) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/doctor_dashboard_view.fxml"));
        Parent root = loader.load();

        // Récupération du contrôleur et passage du patient
        DoctorDashboardController controller = (DoctorDashboardController) loader.getController();
        controller.setnomDoctor(nomDoctor);
        controller.setIdDoctor(idDoctor); // méthode à créer dans PatientDashboardController

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
    @FXML
    public void ButtonConnexion(ActionEvent event) throws SQLException, IOException{
        if(f_cnie.getText().trim().isEmpty() || f_password.getText().trim().isEmpty() || type.getSelectedToggle() == null){ 
            if (f_cnie.getText().trim().isEmpty()) {
                f_cnie.setPromptText("CNIE est obligatoire");
                f_cnie.setStyle("-fx-prompt-text-fill : red");
                f_cnie.getStyleClass().add("field-error");
            }
            if (f_password.getText().trim().isEmpty()) {
                f_password.setPromptText("Password est obligatoire");
                f_password.setStyle("-fx-prompt-text-fill : red");
                f_password.getStyleClass().add("field-error");
            }
            if (type.getSelectedToggle() == null) {
               rd_doctor.setStyle("-fx-text-fill: red; -fx-radio-color: red;");
               rd_patient.setStyle("-fx-text-fill: red; -fx-radio-color: red;");
            }

        }else{
            if(rd_doctor.isSelected()){
                Doctor doctor = DoctorDAO.checkDoctorByCniePassword(f_cnie.getText(), f_password.getText());
                if(doctor != null){
                    System.out.println("Connexion reussi : Bienvenue Dr."+doctor.getNom()+" "+doctor.getPrenom());
                    goToDoctorDashboard(event,doctor.getId(),doctor.getNom());
                }else{
                    System.out.println("Echec de connexion");
                    label_incorrect.setStyle("-fx-opacity : 1.0");
                }
            }else{
                Patient patient = PatientDAO.checkPatientByCniePassword(f_cnie.getText(), f_password.getText());
                if(patient != null){
                          System.out.println("Connexion reussi : Bienvenue "+patient.getNom()+" "+patient.getPrenom());
                          goToPatientDashboard(event, patient);
                }else{
                        System.out.println("Echec de connexion");
                        label_incorrect.setStyle("-fx-opacity : 1.0");
                }
            }
        
        }
    }
}
