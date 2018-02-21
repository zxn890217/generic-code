package com.my.gc.model;

import com.my.gc.utils.JdbcUtil;

/**
 * Created by zxn on 2018/1/4.
 */
public class DaoClass {
    private String className;
    private String packagePath;
    private String baseDao;
    private String baseDaoName;
    private boolean get;
    private boolean insert;
    private boolean update;
    private boolean delete;
    private boolean sensitiveUpdate;
    private boolean updateByConditions;
    private boolean exists;
    private boolean findByPage;
    private boolean count;
    private boolean query;
    private String autoPrimaryKey;
    private String autoPrimaryKeyFieldName;

    public DaoClass(){}

    public DaoClass(String className, String packagePath, String baseDao, ModelClass entityClass) {
        this.className = className;
        this.packagePath = packagePath;
        this.baseDao = baseDao;
        this.baseDaoName = baseDao.substring(baseDao.lastIndexOf(".")+1);
        //this.autoPrimaryKey = autoPrimaryKey;
        if(entityClass.getColumns()!=null){
            for(ColumnModel cm : entityClass.getColumns()){
                if(cm.isPrimaryKey() && cm.isAutoIncrement()) {
                    autoPrimaryKey = cm.getColumnName();
                    autoPrimaryKeyFieldName = cm.getFieldName();
                }
            }
        }
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

    public String getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(String baseDao) {
        if(baseDao!=null)
            this.baseDaoName = baseDao.substring(baseDao.lastIndexOf(".")+1);
        else
            this.baseDaoName = null;
        this.baseDao = baseDao;
    }

    public String getBaseDaoName() {
        return baseDaoName;
    }

    public void setBaseDaoName(String baseDaoName) {
        this.baseDaoName = baseDaoName;
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

    public boolean isUpdateByConditions() {
        return updateByConditions;
    }

    public void setUpdateByConditions(boolean updateByConditions) {
        this.updateByConditions = updateByConditions;
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

    public String getAutoPrimaryKey() {
        return autoPrimaryKey;
    }

    public void setAutoPrimaryKey(String autoPrimaryKey) {
        this.autoPrimaryKey = autoPrimaryKey;
    }

    public String getAutoPrimaryKeyFieldName() {
        return autoPrimaryKeyFieldName;
    }

    public void setAutoPrimaryKeyFieldName(String autoPrimaryKeyFieldName) {
        this.autoPrimaryKeyFieldName = autoPrimaryKeyFieldName;
    }
}
