package com.dawist_o.CodeAnalyzer.HalsteadMetrics.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class HalsteadMetricsModel {
    private IntegerProperty j;
    private StringProperty operator;
    private IntegerProperty j_count;
    private IntegerProperty i;
    private StringProperty operand;
    private IntegerProperty i_count;
    private int program_dictionary;
    private int program_lenght;
    private int program_V;

    public HalsteadMetricsModel(IntegerProperty j, StringProperty operator,
                                IntegerProperty j_count, IntegerProperty i,
                                StringProperty operand, IntegerProperty i_count) {
        this.j = j;
        this.operator = operator;
        this.j_count = j_count;
        this.i = i;
        this.operand = operand;
        this.i_count = i_count;
    }
}
