package com.dawist_o.CodeAnalyzer;

import com.dawist_o.CodeAnalyzer.StagesController.StageController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.setMainStage;

public class CodeAnalyzer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        StageController.window = primaryStage;
        setMainStage();
    }
}
