package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString(callSuper = true)
public class InputEndpoint extends Definition {
    String subName;
    OutputType outputType;

    public InputEndpoint(String name, OutputType outputType) {
        this.name = name;
        this.inputs = null;
        this.outputType = outputType;
        Output output = new Output(this.outputType, 1);
        this.outputs = new ArrayList<>();
        outputs.add(output);
    }

}
