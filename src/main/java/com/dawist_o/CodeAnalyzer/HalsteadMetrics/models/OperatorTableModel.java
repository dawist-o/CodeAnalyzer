package com.dawist_o.CodeAnalyzer.HalsteadMetrics.models;

import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;

import java.util.Map;

@Data
public class OperatorTableModel {
    private Map.Entry<String, Integer> operator;
    private SimpleIntegerProperty j;

    public OperatorTableModel(Map.Entry<String, Integer> operator, int j) {
        this.operator = operator;
        this.j = new SimpleIntegerProperty(j);
    }
}
