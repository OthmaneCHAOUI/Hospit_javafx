package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PatientDashboardController {
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private VBox mainVBox;
    @FXML
    private MenuButton doctorMenu;
    @FXML
    private Hyperlink linkMedicaments;
    @FXML
    private Hyperlink linkTests;
    @FXML
    private Hyperlink linkRendezvous;

    private Patient patient;
    private Doctor currentDoctor;

    public void initialize() {
        linkMedicaments.setOnAction(e -> loadMedicamentsTable());
        linkTests.setOnAction(e -> loadTestsTable());
        linkRendezvous.setOnAction(e -> loadRendezVousTable());
        profileImage.setOnMouseClicked(this::showProfileEdit);
        usernameLabel.setOnMouseClicked(this::showProfileEdit);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        usernameLabel.setText(patient.getNom() + " " + patient.getPrenom());
        try {
            for (Doctor doc : DoctorPatientDAO.getDoctors(patient.getCnie())) {
                MenuItem item = new MenuItem(doc.getNom() + " " + doc.getPrenom());
                item.setOnAction(e -> {
                    currentDoctor = doc;
                    loadMedicamentsTable();
                });
                doctorMenu.getItems().add(item);
            }
            // Charge les médicaments d'un des docteurs au démarrage (si existant)
            if (!doctorMenu.getItems().isEmpty()) {
                currentDoctor = DoctorPatientDAO.getDoctors(patient.getCnie()).get(0);
                doctorMenu.setText(currentDoctor.getNom() + " " + currentDoctor.getPrenom());
                loadMedicamentsTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadMedicamentsTable() {
        try {
            List<Medicament> list = new MedicamentDAO().getMedicaments(patient.getCnie(), currentDoctor.getId());
            TableView<Medicament> table = new TableView<>();

            TableColumn<Medicament, String> colNom = new TableColumn<>("Nom");
            colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom()));

            TableColumn<Medicament, String> colDescription = new TableColumn<>("Description");
            colDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));

            TableColumn<Medicament, String> colDateDebut = new TableColumn<>("Date Début");
            colDateDebut.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateDebut().toString()));

            TableColumn<Medicament, String> colPeriode = new TableColumn<>("Période (jours)");
            colPeriode.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getPeriode())));

            table.getColumns().addAll(colNom, colDescription, colDateDebut, colPeriode);
            table.getItems().addAll(list);

            refreshTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTestsTable() {
        try {
            List<TestMedical> list = new TestMedicalDAO().getTests(patient.getCnie(), currentDoctor.getId());
            TableView<TestMedical> table = new TableView<>();

            TableColumn<TestMedical, String> colType = new TableColumn<>("Type de Test");
            colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTypeTest()));

            TableColumn<TestMedical, String> colResultat = new TableColumn<>("Résultat");
            colResultat.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getResultat()));

            TableColumn<TestMedical, String> colDate = new TableColumn<>("Date du Test");
            colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateTest().toString()));

            TableColumn<TestMedical, String> colTypeDoctor = new TableColumn<>("Type Docteur");
            colTypeDoctor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTypeDoctor()));

            TableColumn<TestMedical, String> colStatut = new TableColumn<>("Statut");
            colStatut.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatut()));

            table.getColumns().addAll(colType, colResultat, colDate, colTypeDoctor, colStatut);
            table.getItems().addAll(list);

            refreshTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadRendezVousTable() {
        try {
            List<RendezVous> list = new RendezVousDAO().getRendezVouss(patient.getCnie(), currentDoctor.getId());
            TableView<RendezVous> table = new TableView<>();

            TableColumn<RendezVous, String> colDate = new TableColumn<>("Date");
            colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateRendezVous().toString()));

            TableColumn<RendezVous, String> colHeure = new TableColumn<>("Heure");
            colHeure.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHeure().toString()));

            TableColumn<RendezVous, String> colRaison = new TableColumn<>("Raison");
            colRaison.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRaison()));

            TableColumn<RendezVous, String> colStatut = new TableColumn<>("Statut");
            colStatut.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatut()));

            table.getColumns().addAll(colDate, colHeure, colRaison, colStatut);
            table.getItems().addAll(list);

            refreshTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshTable(TableView<?> table) {
        mainVBox.getChildren().removeIf(node -> node instanceof TableView);
        mainVBox.getChildren().add(table);
    }

    public void showProfileEdit(MouseEvent event) {
        VBox form = new VBox(10);

        // Champs
        TextField nomField = new TextField(patient.getNom());
        TextField prenomField = new TextField(patient.getPrenom());
        TextField villeField = new TextField(patient.getVille());
        TextField emailField = new TextField(patient.getEmail());
        TextField telephoneField = new TextField(patient.getTelephone());
        TextField adresseField = new TextField(patient.getAdresse());
        DatePicker dateNaissancePicker = new DatePicker(patient.getDateNaissance());
        ComboBox<String> sexeCombo = new ComboBox<>();
        sexeCombo.getItems().addAll("M", "F");
        sexeCombo.setValue(patient.getSexe());
        PasswordField motDePasseField = new PasswordField();
        motDePasseField.setText(patient.getMotDePasse());
        // motDePasseField.setPromptText("Nouveau mot de passe (laisser vide pour garder l'actuel)");

        // Appliquer les classes CSS
        nomField.getStyleClass().add("field");
        prenomField.getStyleClass().add("field");
        villeField.getStyleClass().add("field");
        emailField.getStyleClass().add("field");
        telephoneField.getStyleClass().add("field");
        adresseField.getStyleClass().add("field");
        dateNaissancePicker.getStyleClass().add("field");
        sexeCombo.getStyleClass().add("field");
        motDePasseField.getStyleClass().add("field");

        // Labels
        Label nomLabel = new Label("Nom");
        Label prenomLabel = new Label("Prénom");
        Label villeLabel = new Label("Ville");
        Label emailLabel = new Label("Email");
        Label telephoneLabel = new Label("Téléphone");
        Label adresseLabel = new Label("Adresse");
        Label dateLabel = new Label("Date de naissance");
        Label sexeLabel = new Label("Sexe");
        Label mdpLabel = new Label("Mot de passe");

        nomLabel.getStyleClass().add("label");
        prenomLabel.getStyleClass().add("label");
        villeLabel.getStyleClass().add("label");
        emailLabel.getStyleClass().add("label");
        telephoneLabel.getStyleClass().add("label");
        adresseLabel.getStyleClass().add("label");
        dateLabel.getStyleClass().add("label");
        sexeLabel.getStyleClass().add("label");
        mdpLabel.getStyleClass().add("label");

        // Bouton
        Button saveButton = new Button("Enregistrer");
        saveButton.getStyleClass().add("enregi-button");

        saveButton.setOnAction(e -> {
            patient.setNom(nomField.getText());
            patient.setPrenom(prenomField.getText());
            patient.setVille(villeField.getText());
            patient.setEmail(emailField.getText());
            patient.setTelephone(telephoneField.getText());
            patient.setAdresse(adresseField.getText());
            patient.setDateNaissance(dateNaissancePicker.getValue());
            patient.setSexe(sexeCombo.getValue());
            patient.setMotDePasse(motDePasseField.getText());

            // if (!motDePasseField.getText().trim().isEmpty()) {
            //     patient.setMotDePasse(motDePasseField.getText().trim());
            // }

            new PatientDAO().updatePatient(patient);
            usernameLabel.setText(patient.getNom());
            mainVBox.getChildren().remove(form);
        });

        form.getChildren().addAll(
            nomLabel, nomField,
            prenomLabel, prenomField,
            villeLabel, villeField,
            emailLabel, emailField,
            telephoneLabel, telephoneField,
            adresseLabel, adresseField,
            dateLabel, dateNaissancePicker,
            sexeLabel, sexeCombo,
            mdpLabel, motDePasseField,
            saveButton
        );

        mainVBox.getChildren().removeIf(node -> node instanceof TableView || node instanceof VBox);
        mainVBox.getChildren().add(form);
    }
}