package com.dawist_o.CodeAnalyzer.HalsteadMetrics.models;

import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;

import java.util.Map;

@Data
public class OperandTableModel {
    private Map.Entry<String,Integer> operand;
    private SimpleIntegerProperty i;

    public OperandTableModel(Map.Entry<String, Integer> operator, int i) {
        this.operand = operator;
        this.i = new SimpleIntegerProperty(i);
    }
}
