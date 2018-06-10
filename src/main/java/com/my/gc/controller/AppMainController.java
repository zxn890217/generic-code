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

    @RequestMapping(value="/main/toDataBase")
    public String toDataBase(){
        return "config/database";
    }

    @RequestMapping(value="/main/toTables")
    public String toTables(){
        return "config/tables";
    }
}
