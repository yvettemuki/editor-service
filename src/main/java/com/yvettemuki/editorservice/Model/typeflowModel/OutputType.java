package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

@Data
public class OutputType {
    String name;

    public OutputType(){}

    public OutputType(String name) {
        this.name = name;
    }
}
