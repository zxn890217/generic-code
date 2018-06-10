package com.my.gc.model;

import com.my.gc.config.TemplateConfig;
import com.my.gc.utils.JdbcUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxn on 2018/1/4.
 */
public class Template {
    private String tableName;
    private ModelClass entityClass;
    private String entityClassPath;
    private DaoClass daoClass;
    private String daoClassPath;
    private ServiceClass serviceClass;
    private String serviceClassPath;
    private ControllerClass controllerClass;
    private PageClass pageClass;
    private String pageResponseClassPath;
    private String pageResponseClassName;
    private String responseResultClassPath;
    private String responseResultClassName;
    private ColumnModel primaryKey;

    public Template(){}

    public Template(String tableName, TemplateConfig config){
        this.tableName = tableName;
        TableModel tm = JdbcUtil.getTableStructure(tableName);
        this.primaryKey = tm.getPrimaryKeyColumn();
        this.entityClass = new ModelClass(tm, config.getEntityPackagePath());
        this.daoClass = new DaoClass(entityClass.getClassName()+"Dao", config.getDaoPackagePath(), config.getBaseDao(), entityClass);
        this.serviceClass = new ServiceClass(entityClass.getClassName()+"Service", config.getServicePackagePath(), config.getBaseService());
        this.controllerClass = new ControllerClass(entityClass.getClassName()+"Controller", config.getControllerPackagePath(), config.getBaseController());
        this.pageClass = new PageClass("/"+JdbcUtil.toFirstCharLowerCase(entityClass.getClassName()));
        this.entityClassPath = entityClass.getPackagePath() + "." + entityClass.getClassName();
        this.daoClassPath = daoClass.getPackagePath()+"."+daoClass.getClassName();
        this.serviceClassPath = serviceClass.getPackagePath()+"."+serviceClass.getClassName();
        this.pageResponseClassPath = config.getPageResponseClassPath();
        this.responseResultClassPath = config.getResponseResultClassPath();
        if(pageResponseClassPath!=null)
            this.pageResponseClassName = pageResponseClassPath.substring(pageResponseClassPath.lastIndexOf(".")+1);
        if(responseResultClassPath!=null)
            this.responseResultClassName = responseResultClassPath.substring(responseResultClassPath.lastIndexOf(".")+1);

    }

    public Template(String tableName, ModelClass entityClass, DaoClass daoClass, ServiceClass serivceClass, ControllerClass controllerClass, PageClass pageClass, String pageResponseClassPath, String responseResultClassPath) {
        this.tableName = tableName;
        this.entityClass = entityClass;
        this.daoClass = daoClass;
        this.serviceClass = serivceClass;
        this.controllerClass = controllerClass;
        this.pageClass = pageClass;
        if(entityClass!=null) {
            this.entityClassPath = entityClass.getPackagePath() + "." + entityClass.getClassName();
            for(ColumnModel cm : entityClass.getColumns()){
                if(cm.isPrimaryKey())
                    primaryKey = cm;
            }
        }
        if(serivceClass!=null)
            this.serviceClassPath = serivceClass.getPackagePath()+"."+serivceClass.getClassName();
        this.pageResponseClassPath = pageResponseClassPath;
        this.responseResultClassPath = responseResultClassPath;
        if(pageResponseClassPath!=null)
            this.pageResponseClassName = pageResponseClassPath.substring(pageResponseClassPath.lastIndexOf(".")+1);
        if(responseResultClassPath!=null)
            this.responseResultClassName = responseResultClassPath.substring(responseResultClassPath.lastIndexOf(".")+1);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ModelClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(ModelClass entityClass) {
        this.entityClass = entityClass;
    }

    public String getEntityClassPath() {
        return entityClassPath;
    }

    public void setEntityClassPath(String entityClassPath) {
        this.entityClassPath = entityClassPath;
    }

    public DaoClass getDaoClass() {
        return daoClass;
    }

    public void setDaoClass(DaoClass daoClass) {
        this.daoClass = daoClass;
    }

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getServiceClassPath() {
        return serviceClassPath;
    }

    public void setServiceClassPath(String serviceClassPath) {
        this.serviceClassPath = serviceClassPath;
    }

    public ControllerClass getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(ControllerClass controllerClass) {
        this.controllerClass = controllerClass;
    }

    public PageClass getPageClass() {
        return pageClass;
    }

    public void setPageClass(PageClass pageClass) {
        this.pageClass = pageClass;
    }

    public String getPageResponseClassPath() {
        return pageResponseClassPath;
    }

    public void setPageResponseClassPath(String pageResponseClassPath) {
        this.pageResponseClassPath = pageResponseClassPath;
    }

    public String getPageResponseClassName() {
        return pageResponseClassName;
    }

    public void setPageResponseClassName(String pageResponseClassName) {
        this.pageResponseClassName = pageResponseClassName;
    }

    public String getResponseResultClassPath() {
        return responseResultClassPath;
    }

    public void setResponseResultClassPath(String responseResultClassPath) {
        this.responseResultClassPath = responseResultClassPath;
    }

    public String getResponseResultClassName() {
        return responseResultClassName;
    }

    public void setResponseResultClassName(String responseResultClassName) {
        this.responseResultClassName = responseResultClassName;
    }

    public ColumnModel getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKeys(ColumnModel primaryKey) {
        this.primaryKey = primaryKey;
    }
}
