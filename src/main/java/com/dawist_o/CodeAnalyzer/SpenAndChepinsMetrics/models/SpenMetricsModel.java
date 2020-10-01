package com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

@Data
public class SpenMetricsModel {
    private SimpleIntegerProperty count;
    private SimpleStringProperty identifier;

    public SpenMetricsModel(int count, String identifier) {
        this.count = new SimpleIntegerProperty(count);
        this.identifier = new SimpleStringProperty(identifier);
    }
}
