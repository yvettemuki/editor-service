package com.yvettemuki.editorservice.Controller;

import com.yvettemuki.editorservice.FileUtils;
import com.yvettemuki.editorservice.Picture;
import com.yvettemuki.editorservice.Service.ImageService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "content-type",methods = {})
public class ModelController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/test", method = GET)
    public String home() {
        return "hello world!";
    }


    @RequestMapping(value = "/picture", method = POST)
    public Object getPicture(@RequestBody Picture picture) {
        try {
            imageService.exportImage(picture.getWidth(), picture.getHeight(), picture.getXml());
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
        imageService.exportImage(picture.getWidth(), picture.getHeight(), picture.getXml());
        System.out.println("finished saving picture ... " + System.currentTimeMillis() + "ms");
        File file = new File("./pictures/graphImage.png");
        if(!file.exists()) {
            throw new IOException("file is empty!");
        }
        res.setContentType("application/force-download");
        res.addHeader("Content-Disposition", "attachment;fileName=" + "graphImage");
        res.setHeader("Context-Type", "application/xmsdownload");
        FileUtils.toFileStream(file, res);
    }




}
