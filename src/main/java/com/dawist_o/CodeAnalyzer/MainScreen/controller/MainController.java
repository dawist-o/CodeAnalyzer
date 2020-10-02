package com.dawist_o.CodeAnalyzer.MainScreen.controller;

import java.io.File;
import com.dawist_o.CodeAnalyzer.StagesController.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.*;

public class MainController {

    @FXML
    private TextField filePathField;

    private static final String NO_SUCH_FILE_PATH_MSG = "File with path: %s  doesn't exist or isn't .js file";

    @FXML
    void onFirstButtonClicked() {
        String filePath = filePathField.getText();
        if (isValidFilePath(filePath)) {
            openHalsteadMetrics(new File(filePath));
        } else {
            showInvalidFileWindow();
        }
    }

    @FXML
    void onSecondButtonClicked() {
        String filePath = filePathField.getText();
        if (isValidFilePath(filePath)) {
            openJilbMetrics(new File(filePath));
        } else {
            showInvalidFileWindow();
        }
    }
    @FXML
    void onThrdButtonClicked() {
        String filePath = filePathField.getText();
        if (isValidFilePath(filePath)) {
            openSpenAndChepinMetrics(new File(filePath));
        } else {
            showInvalidFileWindow();
        }
    }

    @FXML
    void onBrowseButtonClicked() {
        File fileForRead = chooseFile();
        if (fileForRead != null)
            filePathField.setText(fileForRead.getPath());
    }

    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JavaScript File", "*js"));
        return fileChooser.showOpenDialog(StageController.window);
    }

    private void showInvalidFileWindow() {
        Alert programInfo = new Alert(Alert.AlertType.INFORMATION);
        programInfo.setTitle("Error");
        programInfo.setHeaderText(null);
        programInfo.setContentText(String.format(NO_SUCH_FILE_PATH_MSG, filePathField.getText()));
        programInfo.showAndWait();
    }

    private boolean isValidFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists() && filePath.endsWith(".js"))
            return true;
        return false;
    }

    @FXML
    void initialize() {

    }
}