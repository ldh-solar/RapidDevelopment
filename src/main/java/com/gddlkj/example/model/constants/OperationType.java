package com.gddlkj.example.model.constants;

import lombok.Getter;
import lombok.Setter;

public enum OperationType {
    /**
     * 操作类型
     */
    LOGIN("login"),
    UNKNOWN("unknown"),
    DELETE("delete"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert");

    @Getter
    @Setter
    private String value;

    OperationType(String s) {
        this.value = s;
    }
}
