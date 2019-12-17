package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

@Data
public class Input {
    InputType inputType;
    Integer index;

    public Input(InputType inputType, Integer index) {
        this.inputType = inputType;
        this.index = index;
    }
}
