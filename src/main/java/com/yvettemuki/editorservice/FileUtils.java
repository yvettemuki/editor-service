package com.yvettemuki.editorservice;

import java.io.*;

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
}
