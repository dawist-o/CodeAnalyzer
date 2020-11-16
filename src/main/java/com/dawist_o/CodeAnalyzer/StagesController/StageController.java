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

    public static Stage window;

    public static void setMainStage() {
        setStage(MainController.class,
                "/com.dawist_o.CodeAnalyzer.mainScreen/main_screen.fxml", "Code analyzer");
    }

    private static FXMLLoader setStage(Class controllerClass, String path, String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(controllerClass.getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        window.setTitle(title);
        window.setScene(scene);
        window.show();
        return loader;
    }

    public static void openHalsteadMetrics(File fileForParse) {
        HalsteadController controller = setStage(HalsteadController.class,
                "/com.dawist_o.CodeAnalyzer.mainScreen/halstead_screen.fxml",
                "Halstead metrics")
                .getController();

        FileParser fileParser = new FileParser(fileForParse);
        FileParser.ParseResult result = fileParser.parseFile();
        controller.initData(result);
    }

    public static void openJilbMetrics(File fileForParse) {
        JilbController controller = setStage(JilbController.class,
                "/com.dawist_o.CodeAnalyzer.mainScreen/jilb_screen.fxml",
                "Jilb metrics").getController();

        FileParser fileParser = new FileParser(fileForParse);
        FileParser.ParseResult result = fileParser.parseFile();
        controller.initData(result);
    }

    public static void openSpenAndChepinMetrics(File fileForParse) {
        SpenAndChepinController model = setStage(SpenAndChepinController.class,
                "/com.dawist_o.CodeAnalyzer.mainScreen/spen&chepin_screen.fxml",
                "Spen and Chepin metrics").getController();
        FileParser fileParser = new FileParser(fileForParse);
        FileParser.ParseResult result = fileParser.parseFile();

    }


}
