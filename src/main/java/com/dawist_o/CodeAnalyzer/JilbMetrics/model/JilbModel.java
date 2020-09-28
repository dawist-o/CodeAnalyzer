package com.dawist_o.CodeAnalyzer.JilbMetrics.model;

import lombok.AllArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class JilbModel {
    // (n) switch-case branches == (n-1) if-else
    // CL==if_count or switch-case_count-1 - количество условных операторов
    // cl==CL/operators. кол-во условных операторов / но общее количетсво операторов

    // CLI==if_count-1 or switch-case_count-2 - максимальный уровень вложенности

    private final HashMap<String, Integer> operators; // get all operators count
    private final int max_nesting_level; //CLI

    public int get_CL() {
        //int big_CL = 0;
        //big_CL+=operators.get("if");
        //big_CL+=operators.get("case");
        return operators.get("case")+operators.get("if");
    }

    public int get_cl() {
        int total_operators_count = 0;
        for (Map.Entry<String, Integer> op : operators.entrySet())
            total_operators_count += op.getValue();
        return get_CL()/total_operators_count;
    }

    public int getMax_nesting_level() {
        return max_nesting_level;
    }
}
