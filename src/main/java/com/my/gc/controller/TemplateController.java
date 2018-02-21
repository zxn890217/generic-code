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
        template.getEntityClass().setColumns(t.getEntityClass().getColumns());
        template.getQueryClass().setColumns(t.getQueryClass().getColumns());
        t.setEntityClass(template.getEntityClass());
        t.setQueryClass(template.getQueryClass());
        t.setPageClass(template.getPageClass());
        t.setControllerClass(template.getControllerClass());
        t.setServiceClass(template.getServiceClass());
        template.getDaoClass().setAutoPrimaryKey(t.getDaoClass().getAutoPrimaryKey());
        template.getDaoClass().setAutoPrimaryKeyFieldName(t.getDaoClass().getAutoPrimaryKeyFieldName());
        t.setDaoClass(template.getDaoClass());
        t.setQueryClassPath(t.getQueryClass().getPackagePath() + "." + t.getQueryClass().getClassName());
        t.setEntityClassPath(t.getEntityClass().getPackagePath() + "." + t.getEntityClass().getClassName());
        t.setServiceClassPath(t.getServiceClass().getPackagePath() + "." + t.getServiceClass().getClassName());
        t.getServiceClass().setBaseService(template.getServiceClass().getBaseService());
        t.getDaoClass().setBaseDao(template.getDaoClass().getBaseDao());
        t.getControllerClass().setBaseController(template.getControllerClass().getBaseController());
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
            FreemarkerUtil.genTemplate("list.html", t, new FileOutputStream(new File(floder.getPath() + "/list.html")));
            FreemarkerUtil.genTemplate("add.html", t, new FileOutputStream(new File(floder.getPath() + "/add.html")));
            FreemarkerUtil.genTemplate("update.html", t, new FileOutputStream(new File(floder.getPath() + "/update.html")));
            FreemarkerUtil.genTemplate("detail.html", t, new FileOutputStream(new File(floder.getPath() + "/detail.html")));
            FreemarkerUtil.genTemplate("Entity.java", t, new FileOutputStream(new File(floder.getPath() + "/" + t.getEntityClass().getClassName() + ".java")));
            FreemarkerUtil.genTemplate("Query.java", t, new FileOutputStream(new File(floder.getPath() + "/" + t.getQueryClass().getClassName() + ".java")));
            String filename = new String("代码".getBytes("gb2312" ), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename="+ filename+".zip");
            //response.addHeader("Content-Length", "" + floder.length());
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
