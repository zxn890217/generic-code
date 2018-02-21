package com.my.gc.model;

/**
 * Created by zxn on 2018/1/4.
 */
public class ControllerClass {
    private String className;
    private String packagePath;
    private String baseController;
    private String baseControllerName;
    private boolean detail;
    private boolean add;
    private boolean update;
    private boolean delete;
    private boolean exists;
    private boolean list;
    private boolean query;

    public ControllerClass(){}

    public ControllerClass(String className, String packagePath, String baseController) {
        this.className = className;
        this.packagePath = packagePath;
        this.baseController = baseController;
        if(baseController!=null)
            this.baseControllerName = baseController.substring(baseController.lastIndexOf(".")+1);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getBaseController() {
        return baseController;
    }

    public void setBaseController(String baseController) {
        if(baseController!=null)
            this.baseControllerName = baseController.substring(baseController.lastIndexOf(".")+1);
        else
            this.baseControllerName = null;
        this.baseController = baseController;
    }

    public String getBaseControllerName() {
        return baseControllerName;
    }

    public void setBaseControllerName(String baseControllerName) {
        this.baseControllerName = baseControllerName;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }
}
