package com.yvettemuki.editorservice.Utils;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.yvettemuki.editorservice.Model.typeflowModel.*;
import org.dom4j.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLParser {

    public static Model extractModelData(String xml, String stringifyXml) throws Exception {
        System.out.println("----------------------------------------");
        List<Definition> definitions = new ArrayList<>();
        List<Instance> instances = new ArrayList<>();

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

                            //the version of input and output type
                            List<Element> inOutputs = dataEle.elements(); //get array of the definition
                            Element inputsEle = inOutputs.get(0);
                            List<Input> inputs = extractInOutputs(inputsEle, InputType.class, Input.class);
                            Element outputsEle = inOutputs.get(1);
                            List<Output> outputs = extractInOutputs(outputsEle, OutputType.class, Output.class);
                            Definition definition = genDefinition(type, name, inputs, outputs);
                            if (definition != null) {
                                definitions.add(definition);
                                Instance instance = new Instance(definition.getName(), definition);
                                instances.add(instance);
                            }

                        }
                    }
                }
            }
        }
        System.out.println("--------------definitions--------------");
        for(Definition definition: definitions) {
            System.out.println(definition.toString());
        }

        mxGraphModel graphModel = getMxGraphModel(stringifyXml);
        List<Connection> connections = extractConnections(root, graphModel);

        return null;

    }

    public static List<Connection> extractConnections(Element root, mxGraphModel graphModel) {
        List<Connection> connections = new ArrayList<>();
        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
            Element cellEle = it.next();
            Attribute edgeAttr = cellEle.attribute("edge");
            if (edgeAttr != null) {
                String resourceId = cellEle.attribute("source").getValue();
                mxCell resource = (mxCell)graphModel.getCell(resourceId);
                String cellType = resource.getStyle();
                if (!cellType.equals("inout_node")) {
                    String fromInstanceId = (String)resource.getValue();
                    String targetId = cellEle.attribute("target").getValue();
                    mxCell target = (mxCell)graphModel.getCell(targetId);
                    System.out.println("@@@@@@@@@@   " + target);
                }
            }
        }


        return connections;
    }

    public static mxGraphModel getMxGraphModel(String xml) {
        mxGraphModel graphModel = new mxGraphModel();
        System.out.println(xml);
        try {
            org.w3c.dom.Document doc = mxXmlUtils.parseXml(xml);
            mxCodec codec = new mxCodec(doc);
            codec.decode(doc.getDocumentElement(), graphModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graphModel;
    }

    public static Definition genDefinition(String type, String name, List<Input> inputs, List<Output> outputs) {
        switch (type) {
            case "InputEndpoint": return new InputEndpoint(name, outputs.get(0).getOutputType());
            case "PureFunction": return new PureFunction(name, inputs, outputs);
            case "OutputEndpoint": return new OutputEndpoint(name, inputs, outputs);
            default: return null;
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
