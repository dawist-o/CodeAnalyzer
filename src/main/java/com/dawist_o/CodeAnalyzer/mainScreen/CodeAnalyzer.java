package com.dawist_o.CodeAnalyzer.mainScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        System.out.println(loader.getLocation());

        Parent root=loader.load();

        Scene scene=new Scene(root);

        stage.setTitle("Code analyzer");
        stage.setScene(scene);
        stage.show();
    }
}
