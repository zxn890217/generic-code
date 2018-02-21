package com.my.gc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zxn on 2018/1/4.
 */
@Component
@ConfigurationProperties(prefix = "template")
public class TemplateConfig {
    private String entityPackagePath;
    private String baseQuery;
    private String daoPackagePath;
    private String servicePackagePath;
    private String controllerPackagePath;
    private String baseRequestMappingValue;
    private String baseDao;
    private String baseService;
    private String baseController;
    private String pageResponseClassPath;
    private String responseResultClassPath;

    public String getEntityPackagePath() {
        return entityPackagePath;
    }

    public void setEntityPackagePath(String entityPackagePath) {
        this.entityPackagePath = entityPackagePath;
    }

    public String getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(String baseQuery) {
        this.baseQuery = baseQuery;
    }

    public String getDaoPackagePath() {
        return daoPackagePath;
    }

    public void setDaoPackagePath(String daoPackagePath) {
        this.daoPackagePath = daoPackagePath;
    }

    public String getServicePackagePath() {
        return servicePackagePath;
    }

    public void setServicePackagePath(String servicePackagePath) {
        this.servicePackagePath = servicePackagePath;
    }

    public String getControllerPackagePath() {
        return controllerPackagePath;
    }

    public void setControllerPackagePath(String controllerPackagePath) {
        this.controllerPackagePath = controllerPackagePath;
    }

    public String getBaseRequestMappingValue() {
        return baseRequestMappingValue;
    }

    public void setBaseRequestMappingValue(String baseRequestMappingValue) {
        this.baseRequestMappingValue = baseRequestMappingValue;
    }

    public String getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(String baseDao) {
        this.baseDao = baseDao;
    }

    public String getBaseService() {
        return baseService;
    }

    public void setBaseService(String baseService) {
        this.baseService = baseService;
    }

    public String getBaseController() {
        return baseController;
    }

    public void setBaseController(String baseController) {
        this.baseController = baseController;
    }

    public String getPageResponseClassPath() {
        return pageResponseClassPath;
    }

    public void setPageResponseClassPath(String pageResponseClassPath) {
        this.pageResponseClassPath = pageResponseClassPath;
    }

    public String getResponseResultClassPath() {
        return responseResultClassPath;
    }

    public void setResponseResultClassPath(String responseResultClassPath) {
        this.responseResultClassPath = responseResultClassPath;
    }
}
