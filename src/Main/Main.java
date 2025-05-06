package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application JavaFX.
 * Elle charge et affiche l'interface utilisateur définie dans ProductView.fxml.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // configuration des boutons
        
        
        // Charge le fichier FXML et configure la scène
        Parent root = FXMLLoader.load(getClass().getResource("/Views/View.fxml"));
        primaryStage.setTitle("Hospit"); // Titre de la fenêtre
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/Views/style.css").toExternalForm());
        primaryStage.setScene(scene); // Associe la scène
        primaryStage.show(); // Affiche la fenêtre
    }

    public static void main(String[] args) {
        System.out.println("launching application ...");
        launch(args); // Démarre l'application
    }
}