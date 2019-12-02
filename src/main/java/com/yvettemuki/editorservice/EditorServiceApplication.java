package com.yvettemuki.editorservice;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import net.minidev.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "content-type",methods = {})
@RequestMapping(value = "/api")
@SpringBootApplication
public class EditorServiceApplication {

    @RequestMapping(value = "/test", method = GET)
    public String home() {
        return "hello world!";
    }


    @RequestMapping(value = "/picture", method = POST)
    public Object getPicture(@RequestBody Picture picture) {
        try {
            exportImage(picture.getWidth(), picture.getHeight(), picture.getXml());
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("failed", true);
        }
        JSONObject json = new JSONObject();
        json.put("success", true);
        return json;

    }

    @RequestMapping(value = "/download", method = POST)
    public void downloadFile(@RequestBody Picture picture, HttpServletResponse res) throws Exception {
        exportImage(picture.getWidth(), picture.getHeight(), picture.getXml());
        System.out.println("finished saving picture ... " + System.currentTimeMillis() + "ms");
        File file = new File("./pictures/graphImage.png");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "graphImage.png");
//        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        return new ResponseEntity<byte[]>(FileUtils.toByteArray(file.toString()), headers, HttpStatus.CREATED);
        if(!file.exists()) {
            throw new IOException("文件不可以为空");
        }
        res.setContentType("application/force-download");
        res.addHeader("Content-Disposition", "attachment;fileName=" + "graphImage");
        res.setHeader("Context-Type", "application/xmsdownload");

        byte[] buffer = new byte[1024];
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        try {
            fin = new FileInputStream(file);
            bin = new BufferedInputStream(fin);
            OutputStream outs = res.getOutputStream();
            int i = bin.read(buffer);
            while (i != -1) {
                outs.write(buffer, 0, i);
                i = bin.read(buffer);
            }
            System.out.println("download successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("file output error");
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

    public static void main(String[] args) {
        SpringApplication.run(EditorServiceApplication.class, args);
    }

}
