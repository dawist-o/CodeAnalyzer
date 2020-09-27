package com.dawist_o.CodeAnalyzer.mainScreen;

import com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller.HalsteadController;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.HalsteadMetricsModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class CodeAnalyzer extends Application {

    //TODO
    //fix fxml path from "/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml" to main_screen.fxml

    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CodeAnalyzer.window = primaryStage;

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(CodeAnalyzer.class.getResource("/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml"));
        System.out.println(CodeAnalyzer.class);
        System.out.println(loader.getLocation());

        Parent root = loader.load();

        Scene scene = new Scene(root);

        window.setTitle("Code analyzer");
        window.setScene(scene);
        window.show();
    }

    //TODO правильно ли делать переход скринов здесь или создавать для этого отдельный класс а тут только main?
    public static void openHalsteadMetrics() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(HalsteadController.class.getResource("/com.dawist_o.CodeAnalyzer.mainScreen/halstead_screen.fxml"));
        System.out.println(loader.getLocation());
        System.out.println(HalsteadController.class);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene=new Scene(root);

        window.setTitle("Halstead metrics");
        window.setScene(scene);
        window.show();

    }
}
