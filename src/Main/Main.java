package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application JavaFX.
 * Elle charge et affiche l'interface utilisateur de Connexion définie dans Connection.fxml.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML et configure la scène
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Connection.fxml"));

        // titre et taille de fenetre
        primaryStage.setTitle("Hospit"); // Titre de la fenêtre
        Scene scene = new Scene(root, 400, 400);

        // associe le style CSS
        scene.getStylesheets().add(getClass().getResource("/Views/style.css").toExternalForm());

        // assocue la scene
        primaryStage.setScene(scene);

        // afficher fenetre
        primaryStage.show();
    }

    public static void main(String[] args) {
        // lancement de l'application
        System.out.println("launching application ...");
        launch(args);
    }
}