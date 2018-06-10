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

    public DaoClass(){}

    public DaoClass(String className, String packagePath, String baseDao, ModelClass entityClass) {
        this.className = className;
        this.packagePath = packagePath;
        this.baseDao = baseDao;
        this.baseDaoName = baseDao.substring(baseDao.lastIndexOf(".")+1);
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
}
