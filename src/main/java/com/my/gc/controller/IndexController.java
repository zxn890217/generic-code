package com.my.gc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zxn on 2018/7/18.
 */
@Controller
public class IndexController {
    @RequestMapping(value="/")
    public String toMain(){
        return "main";
    }
}
