package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DoctorPatient;
import model.DoctorPatientDAO;

import java.sql.SQLException;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DoctorDashboardController {

    @FXML
    private Text field_doctor;
    @FXML
    private Button btn_ajouter;

    @FXML
    private Button btn_exporter;

    @FXML
    private Button btn_parametre;

    @FXML
    private TableColumn<DoctorPatient, String> column_adresse;
    
     @FXML
    private TableColumn<DoctorPatient, Void> column_action;
    @FXML
    private TableColumn<DoctorPatient, String> column_cnie;

    @FXML
    private TableColumn<DoctorPatient, LocalDate> column_date;

    @FXML
    private TableColumn<DoctorPatient, String> column_nom;

    @FXML
    private TableColumn<DoctorPatient, String> column_prenom;

    @FXML
    private TableColumn<DoctorPatient, String> column_sexe;

    @FXML
    private TableColumn<DoctorPatient, String> column_tele;

    @FXML
    private TableColumn<DoctorPatient, String> column_ville;

    @FXML
    private TextField f_recherche;

    @FXML
    private TableView<DoctorPatient> table_patient;

    private ObservableList<DoctorPatient> patientsList;

    private int idDoctor;
    private String nomDoctor;  
    
    public String getnomDoctor() {
        return nomDoctor;
    }
    public void setnomDoctor(String nomDoctor) {
        this.nomDoctor = nomDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
        try {
            field_doctor.setText(nomDoctor);
            loadPatients(); // ✅ Chargement des patients ici après que l'id soit défini
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            // Initialisation des colonnes
            column_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            column_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            column_date.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            column_cnie.setCellValueFactory(new PropertyValueFactory<>("cnie"));
            column_ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
            column_tele.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            column_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            column_sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
            addActionButtonsToTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addActionButtonsToTable() {
    Callback<TableColumn<DoctorPatient, Void>, TableCell<DoctorPatient, Void>> cellFactory = new Callback<>() {
        @Override
        public TableCell<DoctorPatient, Void> call(final TableColumn<DoctorPatient, Void> param) {
            return new TableCell<>() {
                private final Button btnVoir = new Button();
                private final Button btnSupprimer = new Button();
                private final HBox pane = new HBox(35); // espacement 10 px

                {
                    // Configure le bouton "Voir" avec une image
                    ImageView iconVoir = new ImageView(new Image(getClass().getResourceAsStream("/resources/info.png")));
                    iconVoir.setFitHeight(18);
                    iconVoir.setFitWidth(18);
                    btnVoir.setGraphic(iconVoir);
                    btnVoir.setStyle("-fx-background-color: transparent;");

                    // Configure le bouton "Supprimer" avec une image
                    ImageView iconSupprimer = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
                    iconSupprimer.setFitHeight(18);
                    iconSupprimer.setFitWidth(18);
                    btnSupprimer.setGraphic(iconSupprimer);
                    btnSupprimer.setStyle("-fx-background-color: transparent;");

                    pane.getChildren().addAll(btnVoir, btnSupprimer);

                    btnVoir.setOnAction(event -> {
                        DoctorPatient patient = getTableView().getItems().get(getIndex());
                        voirPatient(patient);
                    });

                    btnSupprimer.setOnAction(event -> {
                        DoctorPatient patient = getTableView().getItems().get(getIndex());
                        supprimerPatient(patient);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(pane);
                    }
                }
            };
        }
    };

    column_action.setCellFactory(cellFactory);
}

private void voirPatient(DoctorPatient patient) {
    // Affiche les détails du patient (tu peux ouvrir une nouvelle fenêtre ou un dialogue)
    System.out.println("Voir patient : " + patient.getNom() + " " + patient.getPrenom());
    // TODO : Implémenter la logique d'affichage des détails
}

private void supprimerPatient(DoctorPatient patient) {
    // Confirmer la suppression
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation suppression");
    alert.setHeaderText(null);
    alert.setContentText("Voulez-vous vraiment supprimer le patient " + patient.getNom() + " ?");
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            DoctorPatientDAO.delete(patient.getCnie(), idDoctor);
            try {
                loadPatients();  // recharge la liste après suppression
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });
}


    private void loadPatients() throws SQLException {
        patientsList = FXCollections.observableArrayList(DoctorPatientDAO.getPatients(idDoctor));
        table_patient.setItems(patientsList);
    }

    @FXML
public void Ajouter(ActionEvent event) {
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Ajouter un patient");
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);

    TextField tfNom = new TextField();
    tfNom.setPromptText("Nom");

    TextField tfPrenom = new TextField();
    tfPrenom.setPromptText("Prénom");

    DatePicker dpDateNaissance = new DatePicker();
    dpDateNaissance.setPromptText("Date de naissance");

    TextField tfCnie = new TextField();
    tfCnie.setPromptText("CNI");

    TextField tfVille = new TextField();
    tfVille.setPromptText("Ville");

    TextField tfTelephone = new TextField();
    tfTelephone.setPromptText("Téléphone");

    TextField tfAdresse = new TextField();
    tfAdresse.setPromptText("Adresse");

    ComboBox<String> cbSexe = new ComboBox<>();
    cbSexe.getItems().addAll("M", "F");
    cbSexe.setPromptText("Sexe");

    Button btnAjouter = new Button("Ajouter");
    Button btnAnnuler = new Button("Annuler");

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(15));
    vbox.getChildren().addAll(
        new Label("Nom:"), tfNom,
        new Label("Prénom:"), tfPrenom,
        new Label("Date de naissance:"), dpDateNaissance,
        new Label("CNI:"), tfCnie,
        new Label("Ville:"), tfVille,
        new Label("Téléphone:"), tfTelephone,
        new Label("Adresse:"), tfAdresse,
        new Label("Sexe:"), cbSexe,
        new HBox(10, btnAnnuler, btnAjouter)
    );

    btnAnnuler.setOnAction(e -> dialogStage.close());

    btnAjouter.setOnAction(e -> {
        // Vérification des champs obligatoires
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || dpDateNaissance.getValue() == null
                || tfCnie.getText().isEmpty() || cbSexe.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Nom, Prénom, Date, CNI, Sexe).");
            alert.showAndWait();
            return;
        }

        try {
            DoctorPatient patient = new DoctorPatient();
            patient.setNom(tfNom.getText());
            patient.setPrenom(tfPrenom.getText());
            patient.setDateNaissance(dpDateNaissance.getValue()); // LocalDate
            patient.setCnie(tfCnie.getText());
            patient.setVille(tfVille.getText());
            patient.setTelephone(tfTelephone.getText());
            patient.setAdresse(tfAdresse.getText());
            patient.setSexe(cbSexe.getValue());

            // ⚠️ Vérifie que idDoctor est bien initialisé
            if (idDoctor == 0) {
                throw new IllegalStateException("Identifiant du médecin non défini.");
            }

            DoctorPatientDAO.inserer(patient, idDoctor); // Appel DAO

            dialogStage.close();
            loadPatients(); // Rechargement de la table

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Erreur lors de l'ajout du patient");
            alert.setContentText("Détails : " + ex.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur inattendue");
            alert.setContentText("Détails : " + ex.getMessage());
            alert.showAndWait();
        }
    });

    Scene scene = new Scene(vbox);
    dialogStage.setScene(scene);
    dialogStage.showAndWait();
}

    @FXML
    void Exporter(ActionEvent event) {
        System.out.println("Exporter les données (fonction non implémentée)");
    }

    @FXML
    void Parametre(ActionEvent event) {
        System.out.println("Paramètres");
    }

    @FXML
    void Recherche(ActionEvent event) {
        String keyword = f_recherche.getText().toLowerCase();
        ObservableList<DoctorPatient> filtered = FXCollections.observableArrayList();

        for (DoctorPatient p : patientsList) {
            if (p.getNom().toLowerCase().contains(keyword) ||
                p.getPrenom().toLowerCase().contains(keyword) ||
                p.getCnie().toLowerCase().contains(keyword)) {
                filtered.add(p);
            }
        }

        table_patient.setItems(filtered);
    }
}
