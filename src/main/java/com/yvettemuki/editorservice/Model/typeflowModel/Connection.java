package com.yvettemuki.editorservice.Model.typeflowModel;

import lombok.Data;

@Data
public class Connection {
    String fromInstanceId;
    Integer outputIndex;
    String toInstanceId;
    Integer inputIndex;
}
