package com.dawist_o.CodeAnalyzer.JilbMetrics.controller;

import java.util.HashMap;

import com.dawist_o.CodeAnalyzer.FileParser.FileParser;
import com.dawist_o.CodeAnalyzer.JilbMetrics.model.JilbMetricsModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.setMainStage;

public class JilbController {

    @FXML
    private TextField big_cl_field;
    @FXML
    private TextField cl_field;
    @FXML
    private TextField cli_field;

    @FXML
    void initialize() {
    }

    @FXML
    public void onBackButtonPressed() {
        setMainStage();
    }

    public void initData(FileParser.ParseResult result) {
        big_cl_field.setText("CL = " + result.getCL());
        cl_field.setText(String.format("cl = %.3f", result.getCl()));
        cli_field.setText("CLI = " + result.getCLI());
        System.out.println("Total operators count = " +
                result.getOperators().values().stream().reduce(0, Integer::sum));
    }
}