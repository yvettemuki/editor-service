package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

import java.util.List;

@Data
public class Flow {
    String name;
    List<Instance> instances;
    List<Connection> connections;
}
