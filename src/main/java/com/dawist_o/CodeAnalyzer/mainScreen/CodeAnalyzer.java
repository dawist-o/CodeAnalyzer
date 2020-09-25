package com.dawist_o.CodeAnalyzer.mainScreen;

import com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller.HalsteadController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeAnalyzer  extends Application {

    //TODO
    //fix fxml path from "/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml" to main_screen.fxml

    public static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader();

        loader.setLocation(getClass().getResource("/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml"));

        Parent root=loader.load();

        Scene scene=new Scene(root);
        CodeAnalyzer.stage =stage;

        stage.setTitle("Code analyzer");
        stage.setScene(scene);
        stage.show();
    }

    //TODO правильно ли делать переход скринов здесь или создавать для этого отдельный класс а тут только main?
    public static void openHalsteadMetrics(){
        FXMLLoader loader=new FXMLLoader();

        loader.setLocation(HalsteadController.class.getResource("/com.dawist_o.CodeAnalyzer.mainScreen/halstead_screen.fxml"));
        System.out.println(loader.getLocation());

        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene=new Scene(root);

        stage.setTitle("Halstead metrics");
        stage.setScene(scene);
        stage.show();

    }
}
