package main;

import javafx.application.Application;
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
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_view.fxml"));

        // titre et taille de fenetre
        primaryStage.setTitle("Hospit");
        Scene scene = new Scene(root);

        // associe la scene
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