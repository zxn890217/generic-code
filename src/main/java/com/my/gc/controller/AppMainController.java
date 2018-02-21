package com.my.gc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ZXN on 2017/12/30.
 */
@Controller
@RequestMapping(value="/app")
public class AppMainController {
    @RequestMapping(value="/toMain")
    public String toMain(){
        return "main";
    }

    @RequestMapping(value="/main/toEntity")
    public String toEntity(){
        return "config/entity";
    }


    @RequestMapping(value="/main/toQueryModel")
    public String toQueryModel(){
        return "config/queryModel";
    }

    @RequestMapping(value="/main/toDataBase")
    public String toDataBase(){
        return "config/database";
    }

    @RequestMapping(value="/main/toTables")
    public String toTables(){
        return "config/tables";
    }

    @RequestMapping(value="/main/toDao")
    public String toDao(){
        return "config/dao";
    }

    @RequestMapping(value="/main/toService")
    public String toService(){
        return "config/service";
    }

    @RequestMapping(value="/main/toController")
    public String toController(){
        return "config/controller";
    }

    @RequestMapping(value="/main/toPage")
    public String toList(){
        return "config/page";
    }
}
