package com.yvettemuki.editorservice.Utils;
import javafx.scene.control.Cell;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

public class XMLParser {

    public static void extractModelData(String xml) throws Exception {
        System.out.println("----------------------------------------");
        Document document = DocumentHelper.parseText(xml);
        Element mxGraphModel = document.getRootElement();
        List<Element> elements = mxGraphModel.elements();
        Element root = elements.get(0);

        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
            Element mxCell = it.next();
            for (Iterator<Element> it2 = mxCell.elementIterator(); it2.hasNext();) {
                Element cellContent = it2.next();
                if (cellContent.getName().equals("Object")) {
                    Element data = cellContent.element("Object");
                    String dataType = data.attribute(2).getValue();
                    if (dataType.equals("definition")) {
                        String type = data.attribute("type").getValue();
                        String name = data.attribute("name").getValue();
                        System.out.println(type + " " + name);

                        List<Element> inOutputs = data.elements(); //get array of the definition

                    }
                }
            }
        }


    }
}
