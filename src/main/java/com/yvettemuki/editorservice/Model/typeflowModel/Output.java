package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

@Data
public class Output {
    OutputType outputType;
    Integer index;

    public Output(OutputType outputType, Integer index) {
        this.outputType = outputType;
        this.index = index;
    }
}
