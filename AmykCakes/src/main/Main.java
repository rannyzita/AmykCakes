package main;
import model.Personalizacao;

import java.awt.Image;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/TelaInicial.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            scene.getStylesheets().add(getClass().getResource("/ui/style.css").toExternalForm());
            primaryStage.setTitle("Amyk Cakes - Sistema");
            primaryStage.setFullScreen(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
