package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

@Data
public class InputEndpoint extends Definition {
    String subName;
    OutputType outputType;

    public InputEndpoint() {
        this.inputs = null;
        Output output = new Output(outputType, 1);
        this.outputs.add(output);
    }
}
