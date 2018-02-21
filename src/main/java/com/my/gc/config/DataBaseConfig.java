package com.my.gc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by zxn on 2018/1/4.
 */
@Component
@ConfigurationProperties(prefix = "database")
public class DataBaseConfig {
    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        DataBaseConfig.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        DataBaseConfig.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        DataBaseConfig.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        DataBaseConfig.password = password;
    }
}
