package com.my.gc.model;

/**
 * Created by zxn on 2018/1/4.
 */
public class ServiceClass {
    private String className;
    private String serviceName;
    private String packagePath;
    private String baseService;
    private String baseServiceName;
    private boolean get;
    private boolean insert;
    private boolean update;
    private boolean delete;
    private boolean sensitiveUpdate;
    private boolean exists;
    private boolean findByPage;
    private boolean count;
    private boolean query;

    public ServiceClass(){}

    public ServiceClass(String className, String packagePath, String baseService) {
        this.className = className;
        this.packagePath = packagePath;
        this.baseService = baseService;
        if(baseService!=null)
            this.baseServiceName = baseService.substring(baseService.lastIndexOf(".")+1);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getBaseService() {
        return baseService;
    }

    public void setBaseService(String baseService) {
        if(baseService!=null)
            this.baseServiceName = baseService.substring(baseService.lastIndexOf(".")+1);
        else
            this.baseServiceName =null;
        this.baseService = baseService;
    }

    public String getBaseServiceName() {
        return baseServiceName;
    }

    public void setBaseServiceName(String baseServiceName) {
        this.baseServiceName = baseServiceName;
    }

    public boolean isGet() {
        return get;
    }

    public void setGet(boolean get) {
        this.get = get;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
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

    public boolean isSensitiveUpdate() {
        return sensitiveUpdate;
    }

    public void setSensitiveUpdate(boolean sensitiveUpdate) {
        this.sensitiveUpdate = sensitiveUpdate;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isFindByPage() {
        return findByPage;
    }

    public void setFindByPage(boolean findByPage) {
        this.findByPage = findByPage;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }
}
