package com.yvettemuki.editorservice.Utils;
import com.yvettemuki.editorservice.Model.typeflowModel.Input;
import com.yvettemuki.editorservice.Model.typeflowModel.InputType;
import com.yvettemuki.editorservice.Model.typeflowModel.Output;
import com.yvettemuki.editorservice.Model.typeflowModel.OutputType;
import javafx.scene.control.Cell;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
                    Element dataEle = cellContent.element("Object");
                    if (dataEle != null ) {
                        String dataType = dataEle.attribute(2).getValue();
                        if (dataType.equals("definition")) {
                            String type = dataEle.attribute("type").getValue();
                            String name = dataEle.attribute("name").getValue();
                            System.out.println(type + " " + name);

                            List<Element> inOutputs = dataEle.elements(); //get array of the definition
                            Element inputsEle = inOutputs.get(0);
                            List<Input> inputs = extractInOutputs(inputsEle, InputType.class, Input.class);

                            Element outputsEle = inOutputs.get(1);
                            List<Output> outputs = extractInOutputs(outputsEle, OutputType.class, Output.class);
//                            Element alternativeOutputsEle = inOutputs.get(2);
//                            Element exceptionOutputsEle = inOutputs.get(3);
                            System.out.println("----------------------------------------");
                            for (Input input: inputs) {
                                System.out.println(input.getInputType().getName());
                            }
                            System.out.println("----------------------------------------");
                            for (Output output: outputs) {
                                System.out.println(output.getOutputType().getName());
                            }
                            System.out.println("----------------------------------------");
                        }
                    }
                }
            }
        }


    }

    public static <T1,T2> List<T2> extractInOutputs(Element element, Class<T1> inOutTypeClazz, Class<T2> inOutputClazz) throws Exception{
        List<T2> inOutputs = new ArrayList<>();
        for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
            Element item = it.next();
            Integer index = Integer.valueOf(item.attribute("index").getValue());
            String id = item.attribute("id").getValue();
            Constructor<T1> inOutTypeConstructor = inOutTypeClazz.getConstructor(String.class);
            T1 inOutType = inOutTypeConstructor.newInstance(id);
            Constructor<T2> inOutputsConstructor = inOutputClazz.getConstructor(inOutTypeClazz, Integer.class);
            T2 inOutput = inOutputsConstructor.newInstance(inOutType, index);
            inOutputs.add(inOutput);
        }
        return inOutputs;
    }
}
