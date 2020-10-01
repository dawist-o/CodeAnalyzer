package com.dawist_o.CodeAnalyzer.JilbMetrics.controller;

import java.util.HashMap;

import com.dawist_o.CodeAnalyzer.JilbMetrics.model.JilbMetricsModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.setMainScene;

public class JilbController {

    @FXML
    private TextField big_cl_field;
    @FXML
    private TextField cl_field;
    @FXML
    private TextField cli_field;

    @FXML
    void initialize() {
        HashMap<String, Integer> fst = new HashMap<>();
        fst.put("if", 2);
        fst.put("case", 7);
        fst.put("==", 10);
        JilbMetricsModel jilbModel = new JilbMetricsModel(fst, 5);
        big_cl_field.setText("CL = " + jilbModel.get_CL());
        cl_field.setText("cl = " + jilbModel.get_cl());
        cli_field.setText("SLI = " + jilbModel.getMax_nesting_level());
    }

    @FXML
    public void onBackButtonPressed() {
        setMainScene();
    }
}