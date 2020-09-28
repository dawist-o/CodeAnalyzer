package com.dawist_o.CodeAnalyzer.StagesController;

import com.dawist_o.CodeAnalyzer.CodeAnalyzer;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller.HalsteadController;
import com.dawist_o.CodeAnalyzer.JilbMetrics.controller.JilbController;
import com.dawist_o.CodeAnalyzer.MainScreen.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController {

    //TODO
    //fix fxml path from "/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml" to main_screen.fxml
    public static Stage window;

    public static void setMainScene() {
        Scene scene = new Scene(
                getParent(MainController.class,"/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml")
        );
        window.setTitle("Code analyzer");
        window.setScene(scene);
        window.show();
    }

    public static void openHalsteadMetrics() {
        Scene scene = new Scene(
                getParent(HalsteadController.class,"/com.dawist_o.CodeAnalyzer.mainScreen/halstead_screen.fxml"));
        window.setTitle("Halstead metrics");
        window.setScene(scene);
        window.show();
    }

    public static void openJilbMetrics() {
        Scene scene = new Scene(
                getParent(JilbController.class,"/com.dawist_o.CodeAnalyzer.mainScreen/jilb_screen.fxml"));
        window.setTitle("Jilb metrics");
        window.setScene(scene);
        window.show();
    }

    private static Parent getParent(Class controllerClass, String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(controllerClass.getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
