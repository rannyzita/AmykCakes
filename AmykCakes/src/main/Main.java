package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/ui/TelaInicial.fxml"));
            Scene cena = new Scene(p);
            cena.getStylesheets().add(getClass().getResource("/ui/style.css").toExternalForm());
            primaryStage.setTitle("Amyk Cakes - Sistema");
            primaryStage.setFullScreen(true);
            primaryStage.setScene(cena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}