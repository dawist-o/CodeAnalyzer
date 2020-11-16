package com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;

import java.util.Map;

import com.dawist_o.CodeAnalyzer.FileParser.FileParser;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.OperandTableModel;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.OperatorTableModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import static com.dawist_o.CodeAnalyzer.StagesController.StageController.setMainStage;

public class HalsteadController {
    @FXML
    private TextField dictionary_field;
    @FXML
    private TextField length_field;
    @FXML
    private TextField volume_field;
    @FXML
    private TextField totalUniqueOperators;
    @FXML
    private TextField totalOperators;
    @FXML
    private TextField totalOperands;
    @FXML
    private TextField totalUniqueOperands;

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
        setMainStage();
    }

    public void initData(FileParser.ParseResult result) {
        fillOperatorsTable(result.getOperators());
        fillOperandsTable(result.getOperands());

        totalUniqueOperands.setText(Integer.toString(result.getTotalUniqueOperands()));
        totalOperands.setText(Integer.toString(result.getTotalOperands()));

        totalUniqueOperators.setText(Integer.toString(result.getTotalUniqueOperators()));
        totalOperators.setText(Integer.toString(result.getTotalOperators()));

        //Program dictionary
        String dictionary_string = String.format("n = %d + %d = %d",
                result.getTotalUniqueOperands(), result.getTotalUniqueOperators(), result.getProgrammDictionnary());
        dictionary_field.setText(dictionary_string);
        //Program length
        String length_string = String.format("N = %d + %d = %d",
                result.getTotalOperands(), result.getTotalOperators(), result.getProgrammLength());
        length_field.setText(length_string);
        //Program volume
        String volume_string = String.format("V = %d*log2(%d) = %d",
                result.getProgrammLength(), result.getProgrammDictionnary(), result.getVolume());
        volume_field.setText(volume_string);
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

    @FXML
    public void showAboutAuthorsMessage(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Authors");
        alert.setHeaderText("Authors:");
        alert.setContentText("Backend:Ivan Pletinskiy\nFrontend & controllers: Kovalenko Vladislav");
        alert.showAndWait();
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
