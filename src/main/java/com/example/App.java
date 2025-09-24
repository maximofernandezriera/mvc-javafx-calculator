package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
            getClass().getResource("/com/example/calculator/view/calculator.fxml")
        );
        // Tamaño inicial cómodo para que no se corte la ventana
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("Calculadora MVC - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
