package com.yvettemuki.editorservice;

import javax.servlet.http.HttpServletResponse;
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
        String path = folderPath;
        File file = new File(path);
        File[] files = file.listFiles(); //traverse the folder
        return files;
    }
}
