package com.yvettemuki.editorservice.Controller;

import com.yvettemuki.editorservice.Model.ModelData;
import com.yvettemuki.editorservice.Model.Picture;
import com.yvettemuki.editorservice.Utils.FileUtils;
import com.yvettemuki.editorservice.Model.GenCodeData;
import com.yvettemuki.editorservice.Service.ModelService;
import com.yvettemuki.editorservice.Service.ImageService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "content-type",methods = {})
public class ModelController {

    @Autowired
    ImageService imageService;
    @Autowired
    ModelService modelService;

    @RequestMapping(value = "/test", method = GET)
    public String home() {
        return "hello world!";
    }


    @RequestMapping(value = "/save", method = POST)
    public Object save(@RequestBody ModelData modelData) {
        if (!imageService.isValidateName(modelData.getName())) {
            System.out.println("failed to save picture ... " + System.currentTimeMillis() + "ms");
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("msg", "the model have been exist, name should be unique!");
            return json;
        }
        try {
            imageService.saveModel(modelData.getName(), modelData.getModelXml(), modelData.getSvgXml());
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("success", false);
        }
        System.out.println("finished saving picture ... " + System.currentTimeMillis() + "ms");
        JSONObject json = new JSONObject();
        json.put("success", true);
        return json;

    }

    @RequestMapping(value = "/downloadPNG", method = POST)
    public void downloadFile(@RequestBody Picture picture, HttpServletResponse res) throws Exception {
        imageService.exportImage("model", picture.getWidth(), picture.getHeight(), picture.getXml());
        System.out.println("finished saving picture ... " + System.currentTimeMillis() + "ms");
        File file = new File("./pictures/model.png");
        if(!file.exists()) {
            throw new IOException("file is empty!");
        }
        res.setContentType("application/force-download");
        res.setHeader("Context-Type", "application/xmsdownload");
        FileUtils.toFileStream(file, res);
    }

    @RequestMapping(value = "/getModels", method = GET)
    public List<ModelData> getModels() throws Exception{
        ArrayList<ModelData> list = imageService.getAllModels("./models", "./picmodels");
        return list;
    }

    @RequestMapping(value = "/generateCode", method = POST)
    public void generateCode(@RequestBody GenCodeData genCodeData) throws Exception {
        //use the typflow java model to get the return value
        modelService.generateModel(genCodeData);
    }



}
