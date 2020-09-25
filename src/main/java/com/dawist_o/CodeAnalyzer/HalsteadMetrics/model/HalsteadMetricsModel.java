package com.dawist_o.CodeAnalyzer.HalsteadMetrics.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

//generate auto setters getters toString and equals
@Data
@AllArgsConstructor
public class HalsteadMetricsModel {
    private HalsteadMetricsTable halsteadMetricsTable;
    private int program_dictionary;
    private int program_lenght;
    private int program_V;
}
