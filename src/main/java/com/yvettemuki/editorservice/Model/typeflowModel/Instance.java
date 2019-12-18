package com.yvettemuki.editorservice.Model.typeflowModel;

import com.yvettemuki.editorservice.Model.typeflowModel.Definition;
import lombok.Data;

@Data
public class Instance {
    String id;
    Definition definition;

    public Instance(String id, Definition definition) {
        this.id = id;
        this.definition = definition;
    }
}
