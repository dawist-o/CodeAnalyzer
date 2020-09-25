package com.dawist_o.CodeAnalyzer.mainScreen;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.dawist_o.CodeAnalyzer.FileParser.FileParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import static com.dawist_o.CodeAnalyzer.mainScreen.CodeAnalyzer.openHalsteadMetrics;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField field;

    @FXML
    private URL location;

    private static final String NO_SUCH_FILE_PATH_MSG="File with path: %s  does't exist or isn't .js file";

    @FXML
    void onFstButtonClicked(ActionEvent event) {
        String filePath=field.getText();
        if(isValidFilePath(filePath)){
            openHalsteadMetrics();
        } else{
            showInvalidFileWindow();
        }
    }

    @FXML
    void onBrowseButtonClicked(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("JavaScript File","*js"));
        field.setText(fileChooser.showOpenDialog(CodeAnalyzer.stage).getPath());
    }

    private static void showInvalidFileWindow(){
        Alert programInfo = new Alert(Alert.AlertType.INFORMATION);
        programInfo.setTitle("Error");
        programInfo.setContentText(NO_SUCH_FILE_PATH_MSG);
        programInfo.showAndWait();
    }

    private boolean isValidFilePath(String filePath){
        File file=new File(filePath);
        if(file.exists() && filePath.endsWith(".js"))
            return true;
        return false;
    }

    @FXML
    void initialize() {

    }
}