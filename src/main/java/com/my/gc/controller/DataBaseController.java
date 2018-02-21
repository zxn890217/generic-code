package com.my.gc.controller;

import com.my.gc.config.DataBaseConfig;
import com.my.gc.model.ResponseResult;
import com.my.gc.model.TableModel;
import com.my.gc.utils.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zxn on 2018/1/4.
 */
@Controller
@RequestMapping(value="/database")
public class DataBaseController {
    @Autowired
    private DataBaseConfig config;

    @RequestMapping("/getConfig")
    @ResponseBody
    public DataBaseConfig getConfig(){
        return config;
    }

    @RequestMapping("/connect")
    @ResponseBody
    public ResponseResult connect(DataBaseConfig config){
        config.setDriver(config.getDriver());
        config.setUrl(config.getUrl());
        config.setUserName(config.getUserName());
        config.setPassword(config.getPassword());
        Connection conn = JdbcUtil.getConnection();
        if(conn == null){
            return new ResponseResult(false, "连接数据库失败");
        }
        List<TableModel> list = JdbcUtil.getTables(conn);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseResult(true, "连接数据库成功", list);
    }
}
