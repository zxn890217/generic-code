package com.my.gc.utils;

import com.my.gc.model.*;
import freemarker.template.*;
import freemarker.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zxn on 2018/1/6.
 */
public class FreemarkerUtil {
    public static Template getTemplateByName(String name) {
        Template template = null;

        try {
            Configuration configuration = new Configuration(new Version("2.3.23"));
            configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/static/templates");
            template = configuration.getTemplate(name);
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

    public static void genTemplate(String name, Object params, OutputStream outputStream){
        Template template = getTemplateByName(name);
        try {
            PrintWriter pw = new PrintWriter(outputStream);
            template.process(params, new PrintWriter(outputStream));
            pw.close();
            outputStream.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
