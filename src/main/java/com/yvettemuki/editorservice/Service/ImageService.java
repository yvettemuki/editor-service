package com.yvettemuki.editorservice.Service;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import com.yvettemuki.editorservice.FileUtils;
import com.yvettemuki.editorservice.Model.Model;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.imageio.ImageIO;
import javax.swing.text.Style;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

@Service
public class ImageService {

    public boolean isValidateName(String name) {
        String modelPath = "./models";
        String picPath = "./picmodels";
        String fullName = name + ".xml";
        if (isValidateInFiles(fullName, modelPath) && isValidateInFiles(fullName, picPath)) {
            return true;
        }
        return false;
    }

    protected boolean isValidateInFiles(String fileName, String path) {
        File[] files = FileUtils.getAllFileInfoOfFolder(path);
        for (File f: files) {
            System.out.println(f.getName());
            if (fileName.equals(f.getName())) {
                return false;
            }
        }
        return true;
    }

    public void saveModel(String name, String modelXml, String svgXml) throws Exception {
        String modelFile = "./models";
        String picFile = "./picmodels";
        String modelPath = modelFile + "/" + name + ".xml";
        String picPath = picFile + "/" + name + ".xml";
        FileUtils.writeStringFile(modelXml, modelPath);
        FileUtils.writeStringFile(svgXml, picPath);
    }

    public void exportImage(String name, int width, int height, String xml) throws Exception {
        String path = "./pictures";
        String fileName = "/" + name;
        File png = new File(path + fileName + ".png");
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdir();
        }
        BufferedImage image = mxUtils.createBufferedImage(width, height, Color.WHITE);
        Graphics2D g2 = image.createGraphics();
        mxUtils.setAntiAlias(g2, true, true);
        mxGraphicsCanvas2D gc2 = new mxGraphicsCanvas2D(g2);
        parseXmlSax(xml, gc2);
        ImageIO.write(image, "png", png);
    }

    protected void parseXmlSax(String xml, mxICanvas2D canvas) throws
            SAXException, ParserConfigurationException, IOException {
        //Creates SAX handler for drawing to graphics handle
        mxSaxOutputHandler handler = new mxSaxOutputHandler(canvas);

        //Create SAX parser for handler
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setContentHandler(handler);

        //Renders XML data into image
        reader.parse(new InputSource(new StringReader(xml)));
    }

    public ArrayList<Model> getAllModels(String modelPath, String picPath) throws Exception{
        ArrayList<Model> modelList = new ArrayList<>();
        File[] modelFiles = FileUtils.getAllFileInfoOfFolder(modelPath);
        File[] picFiles = FileUtils.getAllFileInfoOfFolder(picPath);
        if (modelFiles.length != picFiles.length) {
            return null;
        }
        for (int i = 0; i < modelFiles.length; i++) {
            Model model = new Model();
            System.out.println(modelFiles[i].getName());
            System.out.println(picFiles[i].getName());
            model.setName(modelFiles[i].getName()); //including .xml
            model.setModelXml(FileUtils.toXmlString(modelFiles[i].getPath()));
            model.setSvgXml(FileUtils.toXmlString(picFiles[i].getPath()));
            modelList.add(model);
        }
        return modelList;
    }
}
