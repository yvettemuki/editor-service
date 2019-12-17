package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

@Data
public class InputType {
    String name;

    public InputType(){}

    public InputType(String name) {
        this.name = name;
    }
}
