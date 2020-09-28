package com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;

import java.util.HashMap;
import java.util.Map;
import com.dawist_o.CodeAnalyzer.HalsteadMetrics.models.HalsteadMetricsModel;
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

    private HalsteadMetricsModel model;

    public void setModel(HalsteadMetricsModel model) {
        this.model = model;
    }

    @FXML
    void initialize() {
//        lib_field.setText(Integer.toString(model.getProgram_dictionary()));
        //      length_field.setText(Integer.toString(model.getProgram_length()));
        //    v_field.setText(Integer.toString(model.getProgram_V()));
        jColumn.setCellValueFactory(p -> p.getValue().getJ().asObject());
        operatorsColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getOperator().getKey()));
        j_countColumn.setCellValueFactory(p ->
                new SimpleIntegerProperty(p.getValue().getOperator().getValue()).asObject());
        iColumn.setCellValueFactory(p -> p.getValue().getI().asObject());
        operandsColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getOperand().getKey()));
        i_countColumn.setCellValueFactory(p ->
                new SimpleIntegerProperty(p.getValue().getOperand().getValue()).asObject());
    }
    @FXML
    public void onBackButtonPressed(){
        setMainScene();
    }
    @FXML
    void test() {
        HashMap<String, Integer> fst = new HashMap<>();
        fst.put(">", 2);
        fst.put("==", 7);

        HashMap<String, Integer> scnd = new HashMap<>();
        scnd.put("&", 6);
        scnd.put("||", 3);
        ObservableList<OperatorTableModel> list = FXCollections.observableArrayList();
        int i = 1;
        for (Map.Entry<String, Integer> q : fst.entrySet()) {
            list.add(new OperatorTableModel(q, i));
            i++;
        }
        operatorsTable.getItems().addAll(list);
    }
}
