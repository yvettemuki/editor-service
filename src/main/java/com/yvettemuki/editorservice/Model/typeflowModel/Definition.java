package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;
import java.util.List;

@Data
public class Definition {
    String name;
    List<Input> inputs;
    List<Output> outputs;
}
