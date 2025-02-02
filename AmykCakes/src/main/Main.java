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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/TelaInicial.fxml")); // carrega a tela base
            Parent root = loader.load();
            
            // pega o controlador e passa o stage (se necessário)
            TelaInicialController controller = loader.getController();
            controller.setStage(primaryStage);

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