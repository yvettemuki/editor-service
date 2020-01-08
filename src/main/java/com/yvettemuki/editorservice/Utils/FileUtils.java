package com.yvettemuki.editorservice.Utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Array;
import java.util.List;

public class FileUtils {
    public static byte[] toByteArray(String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists()) {
            throw new FileNotFoundException(filename);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while(-1 != (len = in.read(buffer, 0, buf_size))) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    public static void toFileStream(File file, HttpServletResponse res) throws IOException {
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

    public static File[] getAllFileInfoOfFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            return null;
        }
        File[] files = file.listFiles(); //traverse the folder
        return files;
    }

    public static void writeStringFile(String xml, String filePath) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        File file = null;
        try {
            file = new File(filePath);
            if (!file.getParentFile().exists()) {
                throw new FileNotFoundException(filePath);
                //file.getParentFile().mkdirs();
            }
            reader = new BufferedReader(new StringReader(xml));
            writer = new BufferedWriter(new FileWriter(file));
            char buff[] = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            writer.flush();
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String toXmlString(String fileName) throws Exception {
        File file = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }

    public static boolean deleteFile(String path) throws IOException {
        System.out.println(path);
        File file = new File(path);
        if (file.delete()) {
            System.out.println("delete file " + file.getName() + " success!");
            return true;
        } else {
            System.out.println("delete file " + file.getName() + "fail");
            return false;
        }
    }
}
