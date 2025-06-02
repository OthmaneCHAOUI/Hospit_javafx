package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.DBConnection;
import model.DoctorPatient;
import model.DoctorPatientDAO;
import model.Medicament;
import model.MedicamentDAO;
import model.RendezVous;
import model.RendezVousDAO;
import model.TestMedical;
import model.TestMedicalDAO;

public class DoctorMTRController {

    @FXML
    private TableColumn<Medicament, Void> Medicament_column_action;

    @FXML
    private TableColumn<Medicament, LocalDate> Medicament_column_date;

    @FXML
    private TableColumn<Medicament, String> Medicament_column_description;

    @FXML
    private TableColumn<Medicament, Integer> Medicament_column_id;

    @FXML
    private TableColumn<Medicament, String> Medicament_column_nom;

    @FXML
    private TableColumn<Medicament, String> Medicament_column_periode;

    @FXML
    private TableColumn<RendezVous, Void> Rendez_column_action;

    @FXML
    private TableColumn<RendezVous, LocalDate> Rendez_column_date;

    @FXML
    private TableColumn<RendezVous, LocalTime> Rendez_column_heure;

    @FXML
    private TableColumn<RendezVous, Integer> Rendez_column_id;

    @FXML
    private TableColumn<RendezVous, String> Rendez_column_raison;

    @FXML
    private TableColumn<RendezVous, String> Rendez_column_statut;

    @FXML
    private TableColumn<TestMedical, Void> Test_column_action;

    @FXML
    private TableColumn<TestMedical, LocalDate> Test_column_date;

    @FXML
    private TableColumn<TestMedical, Integer> Test_column_id;

    @FXML
    private TableColumn<TestMedical, String> Test_column_nom;

    @FXML
    private TableColumn<TestMedical, String> Test_column_result;

    @FXML
    private TableColumn<TestMedical, String> Test_column_statut;

    @FXML
    private TableColumn<TestMedical, String> Test_column_type;

    @FXML
    private Button btn_ajouterMedicament;

    @FXML
    private Button btn_ajouterRendez;

    @FXML
    private Button btn_ajouterTest;

    @FXML
    private Button btn_exporterMedicament;

    @FXML
    private Button btn_exporterRendez;

    @FXML
    private Button btn_exporterTest;

    @FXML
    private Button btn_medicament;

    @FXML
    private Button btn_parametre;

    @FXML
    private Button btn_rendezvous;

    @FXML
    private Button btn_test;

    @FXML
    private TextField f_rechercheMedicament;

    @FXML
    private TextField f_rechercheRendez;

    @FXML
    private TextField f_rechercheTest;

    @FXML
    private Text field_doctor;

    @FXML
    private AnchorPane interfaceMedicament;

    @FXML
    private AnchorPane interfaceRendezVous;

    @FXML
    private AnchorPane interfaceTest;

    @FXML
    private TableView<Medicament> table_patientM;
    
    private ObservableList<Medicament> medicamentList;

    @FXML
    private TableView<TestMedical> table_patientT;
    
    private ObservableList<TestMedical> testList;

    @FXML
    private TableView<RendezVous> table_patientR;
    
    private ObservableList<RendezVous> rendezVousList;
    
    @FXML
    public void initialize() {
        try {
            // Initialisation des colonnes
            Medicament_column_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            Medicament_column_date.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            Medicament_column_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            Medicament_column_periode.setCellValueFactory(new PropertyValueFactory<>("periode"));
            
            Test_column_nom.setCellValueFactory(new PropertyValueFactory<>("typeTest"));
            Test_column_date.setCellValueFactory(new PropertyValueFactory<>("dateTest"));
            Test_column_type.setCellValueFactory(new PropertyValueFactory<>("typeDoctor"));
            Test_column_result.setCellValueFactory(new PropertyValueFactory<>("resultat"));
            Test_column_statut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Colonnes des rendez-vous
            Rendez_column_date.setCellValueFactory(new PropertyValueFactory<>("dateRendezVous"));
            Rendez_column_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
            Rendez_column_raison.setCellValueFactory(new PropertyValueFactory<>("raison"));
            Rendez_column_statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
            addActionButtonsToTableMedicament();
            addActionButtonsToTableRendezVous();
            addActionButtonsToTableTest();
            f_rechercheMedicament.textProperty().addListener((observable, oldValue, newValue) -> {
                filterMedicaments(newValue);
            });
            f_rechercheRendez.textProperty().addListener((observable, oldValue, newValue) -> {
               filterRendezVous(newValue);
            });
            f_rechercheTest.textProperty().addListener((observable, oldValue, newValue) -> {
               filterTestMedical(newValue);
            });





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterMedicaments(String keyword) {
    FilteredList<Medicament> filteredList = new FilteredList<>(medicamentList, medicament -> {
        if (keyword == null || keyword.isEmpty()) return true;
        String lowerKeyword = keyword.toLowerCase();

        return medicament.getNom().toLowerCase().contains(lowerKeyword) ||
               medicament.getDescription().toLowerCase().contains(lowerKeyword) ||
               String.valueOf(medicament.getPeriode()).toLowerCase().contains(lowerKeyword) ||
               medicament.getDateDebut().toString().contains(lowerKeyword);
    });

    table_patientM.setItems(filteredList);
}


    private void filterRendezVous(String keyword) {
    FilteredList<RendezVous> filteredList = new FilteredList<>(rendezVousList, rendez -> {
        if (keyword == null || keyword.isEmpty()) return true;
        String lowerKeyword = keyword.toLowerCase();

        return rendez.getRaison().toLowerCase().contains(lowerKeyword) ||
               rendez.getStatut().toLowerCase().contains(lowerKeyword) ||
               rendez.getDateRendezVous().toString().contains(lowerKeyword) ||
               rendez.getHeure().toString().contains(lowerKeyword);
    });

    table_patientR.setItems(filteredList);
}

    private void filterTestMedical(String keyword) {
    FilteredList<TestMedical> filteredList = new FilteredList<>(testList, test -> {
        if (keyword == null || keyword.isEmpty()) return true;
        String lowerKeyword = keyword.toLowerCase();

        return test.getTypeTest().toLowerCase().contains(lowerKeyword) ||
               test.getTypeDoctor().toLowerCase().contains(lowerKeyword) ||
               test.getResultat().toLowerCase().contains(lowerKeyword) ||
               test.getStatut().toLowerCase().contains(lowerKeyword) ||
               test.getDateTest().toString().contains(lowerKeyword);
    });

    table_patientT.setItems(filteredList);
}

    
    private void loadMedicaments() {
        medicamentList = FXCollections.observableArrayList(MedicamentDAO.getMedicaments(cniePatient,idDoctor));
        table_patientM.setItems(medicamentList);
    }
    private void loadRendezVous(){
        rendezVousList = FXCollections.observableArrayList(RendezVousDAO.getRendezVous(cniePatient,idDoctor));
        table_patientR.setItems(rendezVousList);
    }
    private void loadTestMedical() {
        testList = FXCollections.observableArrayList(TestMedicalDAO.getTests(cniePatient,idDoctor));
        table_patientT.setItems(testList);
    }
    @FXML
    private Text nomPrenomPatient ;
    

    @FXML
    void Exporter(ActionEvent event) {

    }

    @FXML
    void Parametre(ActionEvent event) {

    }

    @FXML
    void Recherche(ActionEvent event) {

    }
    
    private boolean rdLoaded = false ;
    private boolean medicamentLoaded = true ;
    private boolean testLoaded = false ;
    private int inter = 1;
    @FXML
    void switchToRendezVous(ActionEvent event){
        
        if(!rdLoaded){
            loadRendezVous();
            rdLoaded = true ;
        }
        inter = 3;
        switchForm(3);
    }
    @FXML
    void switchToMedicament(ActionEvent event){
        
        if(!medicamentLoaded){
            loadMedicaments();
            medicamentLoaded = true ;
        }
        inter = 1;
        switchForm(1);
    }
    @FXML
    void switchToTest(ActionEvent event){
        
        if(!testLoaded){
            loadTestMedical();
            testLoaded = true ;
        }
        inter = 2;
       switchForm(2);
    }
    
    private int idDoctor;
    private String cniePatient;
    private String nomPatient;
    private String prenomPatient;

   

    public void setDoctorId(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setCniePatient(String cniePatient) {
        this.cniePatient = cniePatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }
     public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient = prenomPatient;
        String res = "" ;
        res = nomPatient +" "+ prenomPatient;
        nomPrenomPatient.setText(res);
        loadMedicaments();
    }
     
    public void switchForm(int choix){
        switch(choix){
                case 1 :
                    interfaceMedicament.setVisible(true);
                    interfaceRendezVous.setVisible(false);
                    interfaceTest.setVisible(false);
                    break ;
                case 2 :
                    interfaceTest.setVisible(true);
                    interfaceMedicament.setVisible(false);
                    interfaceRendezVous.setVisible(false);
                    break ;
                case 3 :
                    interfaceRendezVous.setVisible(true);
                    interfaceTest.setVisible(false);
                    interfaceMedicament.setVisible(false);
                    break ;
        }
    }
    
    
    private void addActionButtonsToTableMedicament() {
    Callback<TableColumn<Medicament, Void>, TableCell<Medicament, Void>> cellFactory = new Callback<>() {
        @Override
        public TableCell<Medicament, Void> call(final TableColumn<Medicament, Void> param) {
            return new TableCell<>() {
                private final Button btnVoir = new Button();
                private final Button btnSupprimer = new Button();
                private final HBox pane = new HBox(35); // espacement 10 px

                {
                    // Configure le bouton "Voir" avec une image
                    ImageView iconVoir = new ImageView(new Image(getClass().getResourceAsStream("/resources/edit.png")));
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
                        Medicament medicament = getTableView().getItems().get(getIndex());
                        voirMedicament(medicament);
                    });

                    btnSupprimer.setOnAction(event -> {
                        Medicament medicament = getTableView().getItems().get(getIndex());
                        supprimerMedicament(medicament);
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

    Medicament_column_action.setCellFactory(cellFactory);
}
    private void addActionButtonsToTableTest() {
    Callback<TableColumn<TestMedical, Void>, TableCell<TestMedical, Void>> cellFactory = new Callback<>() {
        @Override
        public TableCell<TestMedical, Void> call(final TableColumn<TestMedical, Void> param) {
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
                        TestMedical testMedical = getTableView().getItems().get(getIndex());
                        voirTestMedical(testMedical);
                    });

                    btnSupprimer.setOnAction(event -> {
                        TestMedical testMedical = getTableView().getItems().get(getIndex());
                        supprimerTestMedical(testMedical);
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

    Test_column_action.setCellFactory(cellFactory);
}
    
    private void addActionButtonsToTableRendezVous() {
    Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>> cellFactory = new Callback<>() {
        @Override
        public TableCell<RendezVous, Void> call(final TableColumn<RendezVous, Void> param) {
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
                        RendezVous rendezVous = getTableView().getItems().get(getIndex());
                        voirRendezVous(rendezVous);
                    });

                    btnSupprimer.setOnAction(event -> {
                        RendezVous rendezVous = getTableView().getItems().get(getIndex());
                        supprimerRendezVous(rendezVous);
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

    Rendez_column_action.setCellFactory(cellFactory);
}
    
    private void supprimerMedicament(Medicament medicament) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation suppression");
    alert.setHeaderText(null);
    alert.setContentText("Voulez-vous vraiment supprimer le medicament " + medicament.getNom() + " ?");
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            MedicamentDAO.delete(medicament.getId());
            loadMedicaments();  // recharge la liste après suppression
        }
    });
}
    
    private void voirMedicament(Medicament medicament){
        
        Stage dialogStage = new Stage();
    dialogStage.setTitle("Modifier le Medicament de "+""+nomPatient);
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);

    TextField tfNom = new TextField();
    tfNom.setText(medicament.getNom());

    DatePicker dateDedbut = new DatePicker();
    dateDedbut.setValue(medicament.getDateDebut());

    TextField tfperiode = new TextField();
    tfperiode.setText(String.valueOf(medicament.getPeriode()));

    TextField tfDescription = new TextField();
    tfDescription.setText(medicament.getDescription());

    Button btnAjouter = new Button("Ajouter");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, tfNom, dateDedbut);
    HBox hbox2 = new HBox(10, tfperiode, tfDescription);
    HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(tfNom, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfperiode, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
        if (tfNom.getText().isEmpty() || dateDedbut.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Nom, Date de Dedbut).");
            alert.showAndWait();
            return;
        }

        try {
            Medicament newmedicament = new Medicament();
            newmedicament.setNom(tfNom.getText());
            newmedicament.setDateDebut(dateDedbut.getValue()); // LocalDate
            newmedicament.setPeriode(Integer.parseInt(tfperiode.getText()));
            newmedicament.setDescription(tfDescription.getText());
            newmedicament.setId(medicament.getId());

            MedicamentDAO.editerMedicament(medicament); // Appel DAO
            dialogStage.close();
            loadMedicaments(); // Rechargement de la table

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
    
    private void supprimerTestMedical(TestMedical testMedical) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation suppression");
    alert.setHeaderText(null);
    alert.setContentText("Voulez-vous vraiment supprimer le TestMedicale " + testMedical.getTypeTest() + " ?");
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            TestMedicalDAO.delete(testMedical.getId());
            loadTestMedical();  // recharge la liste après suppression
        }
    });
}
    
    private void voirTestMedical(TestMedical testMedical){
        
         Stage dialogStage = new Stage();
    dialogStage.setTitle("Modifier le TestMedicale de "+""+nomPatient);
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);

    TextField tfNom = new TextField();
    tfNom.setText(testMedical.getTypeTest());

    DatePicker dateDedbut = new DatePicker();
    dateDedbut.setValue(testMedical.getDateTest());

    TextField tfResultat = new TextField();
    tfResultat.setText(testMedical.getResultat());

    ComboBox<String> cbStatut = new ComboBox<>();
    cbStatut.getItems().addAll("en attente", "effectué");
    cbStatut.setValue(testMedical.getStatut());
    ComboBox<String> cbType = new ComboBox<>();
    cbType.getItems().addAll("interne", "externe");
    cbType.setValue(testMedical.getTypeDoctor());

    Button btnAjouter = new Button("Modifier");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, tfNom, dateDedbut);
    HBox hbox2 = new HBox(10, tfResultat, cbStatut,cbType);
    HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(tfNom, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfResultat, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
        if (tfNom.getText().isEmpty() || dateDedbut.getValue() == null || cbStatut.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Nom, Date de Test et Statut).");
            alert.showAndWait();
            return;
        }

        try {
            TestMedical newtestMedical = new TestMedical();
            newtestMedical.setTypeTest(tfNom.getText());
            newtestMedical.setDateTest(dateDedbut.getValue()); // LocalDate
            newtestMedical.setResultat(tfResultat.getText());
            newtestMedical.setStatut(cbStatut.getValue());
            newtestMedical.setTypeDoctor(cbType.getValue());
            newtestMedical.setId(testMedical.getId());

            TestMedicalDAO.editerTest(newtestMedical); // Appel DAO
            dialogStage.close();
            loadTestMedical(); // Rechargement de la table

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
        
   
    
    private void supprimerRendezVous(RendezVous rendezVous) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation suppression");
    alert.setHeaderText(null);
    alert.setContentText("Voulez-vous vraiment supprimer ce rendezVous de DATE : " + rendezVous.getDateRendezVous() + " ?");
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            RendezVousDAO.delete(rendezVous.getId());
            loadRendezVous();  // recharge la liste après suppression
        }
    });
}
    
    private void voirRendezVous(RendezVous rendezVous){
        
        Stage dialogStage = new Stage();
    dialogStage.setTitle("Modifier le RendezVous de "+""+nomPatient);
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);


    DatePicker date = new DatePicker();
    date.setValue(rendezVous.getDateRendezVous());

    TextField tfRaisont = new TextField();
    tfRaisont.setText(rendezVous.getRaison());
    TextField tfHeure = new TextField();
    tfHeure.setText(String.valueOf(rendezVous.getHeure()));

    ComboBox<String> cbStatut = new ComboBox<>();
    cbStatut.getItems().addAll("absent", "à venir","présent");
    cbStatut.setValue(rendezVous.getStatut());

    Button btnAjouter = new Button("Ajouter");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, date, tfHeure);
    HBox hbox2 = new HBox(10, tfRaisont, cbStatut);
    HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(date, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfRaisont, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
        if (date.getValue() == null || cbStatut.getValue() == null || tfHeure.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Date , Heure et Statut).");
            alert.showAndWait();
            return;
        }

        try {
            RendezVous newrendezVous = new RendezVous();
            newrendezVous.setDateRendezVous(date.getValue());
            newrendezVous.setRaison(tfRaisont.getText()); // LocalDate
            newrendezVous.setHeure(LocalTime.parse(tfHeure.getText()));
            newrendezVous.setStatut(cbStatut.getValue());
            newrendezVous.setId(rendezVous.getId());

            RendezVousDAO.editerTest(rendezVous); // Appel DAO
            dialogStage.close();
            loadRendezVous(); // Rechargement de la table

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Erreur lors de la modification du RendezVous");
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
   public  void Ajouter(ActionEvent event) {
       
       if(inter == 1){
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Ajouter un Medicament");
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);

    TextField tfNom = new TextField();
    tfNom.setPromptText("Nom");

    DatePicker dateDedbut = new DatePicker();
    dateDedbut.setPromptText("Date de Debut");

    TextField tfperiode = new TextField();
    tfperiode.setPromptText("Periode(entier)");

    TextField tfDescription = new TextField();
    tfDescription.setPromptText("Description");

    Button btnAjouter = new Button("Ajouter");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, tfNom, dateDedbut);
    HBox hbox2 = new HBox(10, tfperiode, tfDescription);
    HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(tfNom, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfperiode, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
        if (tfNom.getText().isEmpty() || dateDedbut.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Nom, Date de Dedbut).");
            alert.showAndWait();
            return;
        }

        try {
            Medicament medicament = new Medicament();
            medicament.setNom(tfNom.getText());
            medicament.setDateDebut(dateDedbut.getValue()); // LocalDate
            medicament.setPeriode(Integer.parseInt(tfperiode.getText()));
            medicament.setDescription(tfDescription.getText());

            MedicamentDAO.inserer(medicament,cniePatient, idDoctor); // Appel DAO
            dialogStage.close();
            loadMedicaments(); // Rechargement de la table

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
       else if(inter == 2){
           Stage dialogStage = new Stage();
    dialogStage.setTitle("Ajouter un TestMedicale");
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);

    TextField tfNom = new TextField();
    tfNom.setPromptText("Nom");

    DatePicker dateDedbut = new DatePicker();
    dateDedbut.setPromptText("Date de Test");

    TextField tfResultat = new TextField();
    tfResultat.setPromptText("Resultat");

    ComboBox<String> cbStatut = new ComboBox<>();
    cbStatut.getItems().addAll("en attente", "effectué");
    cbStatut.setPromptText("Statut");
    ComboBox<String> cbType = new ComboBox<>();
    cbType.getItems().addAll("interne", "externe");
    cbType.setPromptText("Type de Doctor");

    Button btnAjouter = new Button("Ajouter");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, tfNom, dateDedbut);
    HBox hbox2 = new HBox(10, tfResultat, cbStatut,cbType);
    HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(tfNom, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfResultat, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
        if (tfNom.getText().isEmpty() || dateDedbut.getValue() == null || cbStatut.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Nom, Date de Test et Statut).");
            alert.showAndWait();
            return;
        }

        try {
            TestMedical testMedical = new TestMedical();
            testMedical.setTypeTest(tfNom.getText());
            testMedical.setDateTest(dateDedbut.getValue()); // LocalDate
            testMedical.setResultat(tfResultat.getText());
            testMedical.setStatut(cbStatut.getValue());
            testMedical.setTypeDoctor(cbType.getValue());

            TestMedicalDAO.inserer(testMedical,cniePatient, idDoctor); // Appel DAO
            dialogStage.close();
            loadTestMedical(); // Rechargement de la table

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
       else{
           Stage dialogStage = new Stage();
    dialogStage.setTitle("Ajouter un RendezVous");
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setResizable(false);


    DatePicker date = new DatePicker();
    date.setPromptText("Date de RendezVous");

    TextField tfRaisont = new TextField();
    tfRaisont.setPromptText("Raison");
    TextField tfHeure = new TextField();
    tfHeure.setPromptText("Heure (format x:x)");

    ComboBox<String> cbStatut = new ComboBox<>();
    cbStatut.getItems().addAll("absent", "à venir","présent");
    cbStatut.setPromptText("Statut");

    Button btnAjouter = new Button("Ajouter");
    Button btnAnnuler = new Button("Annuler");

    // Espacement entre les éléments dans les HBox
    HBox hbox1 = new HBox(10, date, tfHeure);
    HBox hbox2 = new HBox(10, tfRaisont, cbStatut);
    HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

    // Padding pour chaque HBox (espacement intérieur)
    hbox1.setPadding(new Insets(5));
    hbox2.setPadding(new Insets(5));
    hbox3.setPadding(new Insets(10));

    // Margin (marge extérieure) exemple : on ajoute une marge autour de tfNom et tfVille dans hbox1
    HBox.setMargin(date, new Insets(0, 10, 0, 0));  // marge droite de 10px
    HBox.setMargin(tfRaisont, new Insets(0, 10, 0, 0));
    HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

    VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
        if (date.getValue() == null || cbStatut.getValue() == null || tfHeure.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires (Date , Heure et Statut).");
            alert.showAndWait();
            return;
        }

        try {
            RendezVous rendezVous = new RendezVous();
            rendezVous.setDateRendezVous(date.getValue());
            rendezVous.setRaison(tfRaisont.getText()); // LocalDate
            rendezVous.setHeure(LocalTime.parse(tfHeure.getText()));
            rendezVous.setStatut(cbStatut.getValue());

            RendezVousDAO.inserer(rendezVous,cniePatient, idDoctor); // Appel DAO
            dialogStage.close();
            loadRendezVous(); // Rechargement de la table

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText("Erreur lors de l'ajout du RendezVous");
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
}
    
     

}
