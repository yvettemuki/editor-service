package com.yvettemuki.editorservice.Service;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

@Service
public class ImageService {

    public void exportImage(int width, int height, String xml) throws Exception {
        String path = "./pictures";
        String processName = "/graphImage";
        File png = new File(path + processName + ".png");
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
}
