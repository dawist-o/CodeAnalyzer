package com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;

import java.util.HashMap;
import java.util.Map;

import com.dawist_o.CodeAnalyzer.FileParser.FileParser;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.OperandTableModel;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.OperatorTableModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.setMainScene;

public class HalsteadController {
    @FXML
    private TextField lib_field;
    @FXML
    private TextField length_field;
    @FXML
    private TextField v_field;

    @FXML
    private TableColumn<OperatorTableModel, Integer> jColumn;
    @FXML
    private TableColumn<OperatorTableModel, String> operatorsColumn;
    @FXML
    private TableColumn<OperatorTableModel, Integer> j_countColumn;
    @FXML
    private TableColumn<OperandTableModel, Integer> iColumn;
    @FXML
    private TableColumn<OperandTableModel, String> operandsColumn;
    @FXML
    private TableColumn<OperandTableModel, Integer> i_countColumn;
    // j operator i operand
    @FXML
    private TableView<OperatorTableModel> operatorsTable;
    @FXML
    private TableView<OperandTableModel> operandsTable;

    @FXML
    void initialize() {
        jColumn.setCellValueFactory(data -> data.getValue().getJ().asObject());
        operatorsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOperator().getKey()));
        j_countColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getOperator().getValue()).asObject());

        iColumn.setCellValueFactory(data -> data.getValue().getI().asObject());
        operandsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOperand().getKey()));
        i_countColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getOperand().getValue()).asObject());
    }

    @FXML
    public void onBackButtonPressed() {
        setMainScene();
    }

    public void initData(FileParser.ParseResult result) {
        fillOperatorsTable(result.getOperators());
        fillOperandsTable(result.getOperands());
        lib_field.setText(Integer.toString(result.getProgrammDictionnary()));
        length_field.setText(Integer.toString(result.getProgrammLength()));
        v_field.setText(Integer.toString(result.getVolume()));
    }

    private void fillOperatorsTable(Map<String, Integer> map) {
        ObservableList<OperatorTableModel> operatorsList = FXCollections.observableArrayList();
        int i = 1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            operatorsList.add(new OperatorTableModel(entry, i));
            i++;
        }
        operatorsTable.getItems().addAll(operatorsList);
    }

    private void fillOperandsTable(Map<String, Integer> map) {
        ObservableList<OperandTableModel> operandsList = FXCollections.observableArrayList();
        int i = 1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            operandsList.add(new OperandTableModel(entry, i));
            i++;
        }
        operandsTable.getItems().addAll(operandsList);
    }
}
