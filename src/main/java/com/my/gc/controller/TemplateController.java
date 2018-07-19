package com.my.gc.controller;

import com.my.gc.config.TemplateConfig;
import com.my.gc.model.*;
import com.my.gc.utils.FreemarkerUtil;
import com.my.gc.utils.JdbcUtil;
import com.my.gc.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zxn on 2018/1/4.
 */
@Controller
@RequestMapping(value="/template")
public class TemplateController {
    @Autowired
    private TemplateConfig config;

    @RequestMapping(value = "/getTemplates")
    @ResponseBody
    public ResponseResult getTemplates(String tableName) {
        Template t = new Template(tableName, config);
        return new ResponseResult(true, "获取代码模板成功", t);
    }

    @RequestMapping(value = "/download")
    public void download(Template template, HttpServletRequest request, HttpServletResponse response) {
        Template t = new Template(template.getTableName(), config);
        String path = request.getServletContext().getRealPath("/") + "/temp/";
        File floder = new File(path);
        if (floder.exists()) {
            File[] subFiles = floder.listFiles();
            if(subFiles!=null && subFiles.length>0){
                for(File f : subFiles){
                    f.delete();
                }
            }
            floder.delete();
        }
        floder.mkdir();
        try {
            FreemarkerUtil.genTemplate("mybatis.xml", t, new FileOutputStream(new File(floder.getPath() + "/" + JdbcUtil.toFirstCharLowerCase(t.getEntityClass().getClassName()) + ".xml")));
            FreemarkerUtil.genTemplate("Dao.java", t, new FileOutputStream(new File(floder.getPath() + "/" + t.getDaoClass().getClassName() + ".java")));
            FreemarkerUtil.genTemplate("Service.java", t, new FileOutputStream(new File(floder.getPath() + "/" + t.getServiceClass().getClassName() + ".java")));
            FreemarkerUtil.genTemplate("Controller.java", t, new FileOutputStream(new File(floder.getPath() + "/" + t.getControllerClass().getClassName() + ".java")));
            FreemarkerUtil.genTemplate("Entity.java", t, new FileOutputStream(new File(floder.getPath() + "/" + t.getEntityClass().getClassName() + ".java")));
            FreemarkerUtil.genTemplate("table.vue", t, new FileOutputStream(new File(floder.getPath() + "/table.vue")));
            FreemarkerUtil.genTemplate("add.vue", t, new FileOutputStream(new File(floder.getPath() + "/add.vue")));
            FreemarkerUtil.genTemplate("update.vue", t, new FileOutputStream(new File(floder.getPath() + "/update.vue")));
            FreemarkerUtil.genTemplate("detail.vue", t, new FileOutputStream(new File(floder.getPath() + "/detail.vue")));
            FreemarkerUtil.genTemplate("zh.js", t, new FileOutputStream(new File(floder.getPath() + "/zh.js")));
            FreemarkerUtil.genTemplate("en.js", t, new FileOutputStream(new File(floder.getPath() + "/en.js")));
            String filename = new String(template.getTableName().getBytes("gb2312" ), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename="+ filename+".zip");
            response.setContentType("application/octet-stream");
            //创建zip输出流
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            File[] files = floder.listFiles();
            for(File f : files){
                ZipUtils.doCompress(f, out);
                response.flushBuffer();
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //floder.delete();
    }
}
