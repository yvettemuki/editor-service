package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PureFunction extends Definition {
    public PureFunction(String name, List<Input> inputs, List<Output> outputs) {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
    }
}
