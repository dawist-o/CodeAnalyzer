package com.dawist_o.CodeAnalyzer.HalsteadMetrics.models;

import javafx.beans.property.SimpleMapProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.AbstractMap;
import java.util.HashMap;

//generate auto setters getters toString and equals
@Data
public class HalsteadMetricsModel {
    private HashMap<String, Integer> operators;
    private HashMap<String, Integer> operands;
    private AbstractMap.SimpleEntry<String, Integer> qwe;
    private int program_dictionary;
    private int program_length;
    private int program_V;

    public HalsteadMetricsModel(HashMap<String, Integer> operators, HashMap<String, Integer> operands,
                                int program_dictionary, int program_length, int program_V) {

        this.operators = operators;
        this.operands = operands;
        this.program_dictionary = program_dictionary;
        this.program_length = program_length;
        this.program_V = program_V;
    }
}
