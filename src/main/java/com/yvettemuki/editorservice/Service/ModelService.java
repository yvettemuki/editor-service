package com.yvettemuki.editorservice.Service;

import com.yvettemuki.editorservice.Model.Instance;
import com.yvettemuki.editorservice.Model.Model;
import com.yvettemuki.editorservice.Utils.XMLParser;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    public boolean generateModel(Instance instance) throws Exception {
        String xml = instance.getXml();
        System.out.println(xml);
        XMLParser.extractModelData(xml);
        return true;
    }

}
