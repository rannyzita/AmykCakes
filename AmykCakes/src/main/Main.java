package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.TelaInicialController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/TelaInicial.fxml"));
            Parent root = loader.load();
            
            TelaInicialController controller = loader.getController();
            
            Scene scene = new Scene(root);
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