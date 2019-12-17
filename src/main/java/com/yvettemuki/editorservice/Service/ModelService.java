package com.yvettemuki.editorservice.Service;

import com.yvettemuki.editorservice.Model.GenCodeData;
import com.yvettemuki.editorservice.Utils.XMLParser;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    public boolean generateModel(GenCodeData data) throws Exception {
        String xml = data.getXml();
        System.out.println(xml);
        XMLParser.extractModelData(xml);
        return true;
    }

}
