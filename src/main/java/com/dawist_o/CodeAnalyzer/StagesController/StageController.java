package com.dawist_o.CodeAnalyzer.StagesController;

import com.dawist_o.CodeAnalyzer.FileParser.FileParser;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller.HalsteadController;
import com.dawist_o.CodeAnalyzer.JilbMetrics.controller.JilbController;
import com.dawist_o.CodeAnalyzer.MainScreen.controller.MainController;
import com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.controller.SpenAndChepinController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StageController {

    //TODO
    //fix fxml path from "/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml" to main_screen.fxml
    public static Stage window;

    public static void setMainScene() {
        Scene scene = new Scene(
                getParent(MainController.class, "/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml")
        );
        window.setTitle("Code analyzer");
        window.setScene(scene);
        window.show();
    }

    public static void openHalsteadMetrics(File fileForParse) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HalsteadController.class.getResource("/com.dawist_o.CodeAnalyzer.mainScreen/halstead_screen.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        FileParser fileParser = new FileParser(fileForParse);
        FileParser.ParseResult result = fileParser.parseFile();

        HalsteadController controller = loader.getController();

        controller.initData(result);

        window.setTitle("Halstead metrics");
        window.setScene(scene);
        window.show();
    }

    public static void openJilbMetrics() {
        Scene scene = new Scene(
                getParent(JilbController.class, "/com.dawist_o.CodeAnalyzer.mainScreen/jilb_screen.fxml"));
        window.setTitle("Jilb metrics");
        window.setScene(scene);
        window.show();
    }

    public static void openSpenAndChepinMetrics() {
        Scene scene = new Scene(
                getParent(SpenAndChepinController.class, "/com.dawist_o.CodeAnalyzer.mainScreen/spen&chepin_screen.fxml"));

        window.setTitle("Spen and Chepin metrics");
        window.setScene(scene);
        window.show();
    }

    private static Parent getParent(Class<?> controllerClass, String path) {
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
