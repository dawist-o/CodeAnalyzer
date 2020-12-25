package com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.models;

import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

@Data
public class ChepinModel {
    private SimpleStringProperty p;
    private SimpleStringProperty m;
    private SimpleStringProperty s;
    private SimpleStringProperty t;

    public ChepinModel(String p, String m, String s, String t) {
        this.p=new SimpleStringProperty(p);
        this.m=new SimpleStringProperty(m);
        this.s=new SimpleStringProperty(s);
        this.t=new SimpleStringProperty(t);

    }
}
