package com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.controller;

import com.dawist_o.CodeAnalyzer.FileParser.FileParser;
import com.dawist_o.CodeAnalyzer.FileParser.FileParserForChepin;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.OperatorTableModel;
import com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.models.ChepinModel;
import com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.models.SpenMetricsModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.setMainStage;

public class SpenAndChepinController {

    @FXML
    private TextField totalCount;
    @FXML
    private TextField chepinResult;
    @FXML
    private TextField wrChepinResult;
    @FXML
    private TextField pField;
    @FXML
    private TextField mField;
    @FXML
    private TextField sField;
    @FXML
    private TextField tField;
    @FXML
    private TextField tResult;
    @FXML
    private TextField sResult;
    @FXML
    private TextField mResult;
    @FXML
    private TextField pResult;
    @FXML
    private TextField pWRField;
    @FXML
    private TextField mWRField;
    @FXML
    private TextField sWRField;
    @FXML
    private TextField tWRField;
    @FXML
    private TextField tWRResult;
    @FXML
    private TextField sWRResult;
    @FXML
    private TextField mWRResult;
    @FXML
    private TextField pWRResult;

    @FXML
    private TableView<SpenMetricsModel> spenTable;
    @FXML
    private TableColumn<SpenMetricsModel, String> identifietColumn;

    @FXML
    private TableColumn<SpenMetricsModel, Integer> countColumn;

    @FXML
    void initialize() {
        identifietColumn.setCellValueFactory(data -> data.getValue().getIdentifier());
        countColumn.setCellValueFactory(data -> data.getValue().getCount().asObject());

    }

    private void fillSpenTable(List<FileParserForChepin.Operand> result) {
        ObservableList<SpenMetricsModel> operatorsList = FXCollections.observableArrayList();
        int total = 0;
        for (FileParserForChepin.Operand operand : result) {
            if(operand.getCount()==0) continue;
            operatorsList.add(new SpenMetricsModel(operand.getCount() - 1, operand.getName()));
            total += operand.getCount() - 1;
        }
        totalCount.setText(Integer.toString(total));
        spenTable.getItems().addAll(operatorsList);
    }

    public void initData(Pair<FileParserForChepin.ChapinResult, FileParserForChepin.ChapinResult> result) {
        fillSpenTable(result.getFirst().getOperands());
        fillChepinTable(result.getFirst());
        fillChepinWRTable(result.getSecond());
    }

    private void fillChepinTable(FileParserForChepin.ChapinResult result) {
        pField.setText(result.getPCount());
        mField.setText(result.getMCount());
        sField.setText(result.getCCount());
        tField.setText(result.getTCount());
        
        pResult.setText(result.getPVariables());
        mResult.setText(result.getMVariables());
        sResult.setText(result.getCVariables());
        tResult.setText(result.getTVariables());
        
        chepinResult.setText(result.getChepinMetric());
    }

    private void fillChepinWRTable(FileParserForChepin.ChapinResult result) {
        pWRField.setText(result.getPCount());
        mWRField.setText(result.getMCount());
        sWRField.setText(result.getCCount());
        tWRField.setText(result.getTCount());

        pWRResult.setText(result.getPVariables());
        mWRResult.setText(result.getMVariables());
        sWRResult.setText(result.getCVariables());
        tWRResult.setText(result.getTVariables());

        wrChepinResult.setText(result.getChepinMetric());
    }

    @FXML
    public void onBackButtonPressed() {
        setMainStage();
    }
}

