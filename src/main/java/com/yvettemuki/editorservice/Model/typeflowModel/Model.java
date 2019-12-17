package com.yvettemuki.editorservice.Model.typeflowModel;

import com.yvettemuki.editorservice.Model.typeflowModel.Definition;
import lombok.Data;

import java.util.List;

@Data
public class Model {
    String name;
    List<Definition> definitions;
}
