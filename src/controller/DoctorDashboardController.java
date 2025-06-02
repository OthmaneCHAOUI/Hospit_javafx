package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DoctorPatient;
import model.DoctorPatientDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Doctor;
import model.DoctorDAO;

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
    
    public int getidDoctor() {
        return idDoctor;
    }
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

public void voirPatient(DoctorPatient patient) {
    try {
        // Charger le FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DoctorMTR_view.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur
        DoctorMTRController controller = loader.getController();

        // Passer idDoctor et cnie au contrôleur
        controller.setDoctorId(idDoctor);
        controller.setCniePatient(patient.getCnie());
        controller.setNomPatient(patient.getNom());
        controller.setPrenomPatient(patient.getPrenom());

        // Créer la nouvelle scène
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}



private void supprimerPatient(DoctorPatient patient) {
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

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, tfNom, tfVille);
    HBox hbox2 = new HBox(10, tfPrenom, tfTelephone);
    HBox hbox3 = new HBox(10, dpDateNaissance, tfAdresse);
    HBox hbox4 = new HBox(10, tfCnie, cbSexe);
    HBox hbox5 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(5));
    hbox4.setPadding(new Insets(5));
    hbox5.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(tfNom, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfPrenom, new Insets(0, 10, 0, 0));
    HBox.setMargin(dpDateNaissance, new Insets(0, 10, 0, 0));
    HBox.setMargin(tfCnie, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3, hbox4, hbox5);

    // Padding pour le VBox (espace intérieur tout autour)
    vbox.setPadding(new Insets(20));

    // Bordure grise, solide, coins arrondis 10px, épaisseur 2px
    vbox.setBorder(new Border(new BorderStroke(
        Color.GRAY,
        BorderStrokeStyle.SOLID,
        new CornerRadii(10),
        new BorderWidths(2)
    )));

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

            // ⚠ Vérifie que idDoctor est bien initialisé
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
    private Doctor currentDoctor ;
    public void setDoctor(Doctor doctor){
        this.currentDoctor = doctor ;
    }
    @FXML
    void Parametre(ActionEvent event) {
        Stage dialogStage = new Stage();
    dialogStage.setTitle("Modifier Votre info");
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);

    TextField tfNom = new TextField();
    tfNom.setPromptText("Nom");
    tfNom.setText(currentDoctor.getNom());
    
    TextField tfNomCabinet = new TextField();
    tfNomCabinet.setPromptText("Nom de cabinet");
    tfNomCabinet.setText(currentDoctor.getNomCabinet());
    
    TextField tfPrenom = new TextField();
    tfPrenom.setPromptText("Prenom");
    tfPrenom.setText(currentDoctor.getPrenom());
    
    TextField tfAdresse = new TextField();
    tfAdresse.setPromptText("Adresse de Cabinet");
    tfAdresse.setText(currentDoctor.getAdresseCabinet());

    TextField tfCnie = new TextField();
    tfCnie.setPromptText("CNIE");
    tfCnie.setText(currentDoctor.getCnie());

    TextField tftele = new TextField();
    tftele.setPromptText("Téléphone");
    tftele.setText(currentDoctor.getTelephone());
    
    TextField tfSpec = new TextField();
    tfSpec.setPromptText("Spécialité");
    tfSpec.setText(currentDoctor.getSpecialite());
    
    TextField tfmotPasse = new TextField();
    tfmotPasse.setPromptText("Mot de passe");
    tfmotPasse.setText(currentDoctor.getMotDePasse());

    Button btnAjouter = new Button("Modifier");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, tfNom, tfNomCabinet);
    HBox hbox2 = new HBox(10, tfPrenom, tfAdresse);
    HBox hbox3 = new HBox(10, tfCnie, tftele);
    HBox hbox4 = new HBox(10, tfSpec, tfmotPasse);
    HBox hbox5 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(tfNom, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfPrenom, new Insets(0, 10, 0, 0));
    HBox.setMargin(tfCnie, new Insets(0, 10, 0, 0));
    HBox.setMargin(tfSpec, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3,hbox4,hbox5);

    // Padding pour le VBox (espace intérieur tout autour)
    vbox.setPadding(new Insets(20));

    // Bordure grise, solide, coins arrondis 10px, épaisseur 2px
    vbox.setBorder(new Border(new BorderStroke(
        Color.GRAY,
        BorderStrokeStyle.SOLID,
        new CornerRadii(10),
        new BorderWidths(2)
    )));

    btnAnnuler.setOnAction(e -> dialogStage.close());

    btnAjouter.setOnAction(e -> {
        // Vérification des champs obligatoires
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() ||
                tfCnie.getText().isEmpty()|| tfSpec.getText().isEmpty()||
                tfmotPasse.getText().isEmpty() ||tfNomCabinet.getText() .isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Nom ,Prenom , Nom de Cabinet , CNIE ,Spécialité, Mot de passe ");
            alert.showAndWait();
            return;
        }

        try {
            Doctor newdoctor = new Doctor();
            newdoctor.setNom(tfNom.getText());
            newdoctor.setPrenom(tfPrenom.getText());
            newdoctor.setMotDePasse(tfmotPasse.getText());
            newdoctor.setAdresseCabinet(tfAdresse.getText()); // LocalDate
            newdoctor.setCnie(tfCnie.getText());
            newdoctor.setNomCabinet(tfNomCabinet.getText());
            newdoctor.setSpecialite(tfSpec.getText());
            newdoctor.setTelephone(tftele.getText());
            newdoctor.setId(idDoctor);
            field_doctor.setText(tfNom.getText());

            DoctorDAO.update(newdoctor); // Appel DAO
            dialogStage.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Erreur lors de l'ajout du medicament");
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