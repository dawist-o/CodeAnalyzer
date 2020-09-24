package com.dawist_o.CodeAnalyzer.mainScreen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onFstButtonClicked(ActionEvent event) {
        System.out.println("Hello world");
    }

    @FXML
    void initialize() {

    }
}