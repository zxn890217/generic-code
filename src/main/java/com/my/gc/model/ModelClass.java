package com.my.gc.model;

import com.my.gc.utils.JdbcUtil;

import java.util.List;
import java.util.Set;

/**
 * Created by zxn on 2018/1/4.
 */
public class ModelClass {
    //类名
    private String className;
    //包路径
    private String packagePath;
    //引入类
    private Set<String> imports;
    //类注释
    private String classAnnotation;
    //继承类
    private String extendsClass;
    //属性列
    private List<ColumnModel> columns;

    public ModelClass(){}

    public ModelClass(TableModel tm, String packagePath){
        this.className = JdbcUtil.getEntityName(tm.getTableName());
        this.packagePath = packagePath;
        this.imports = tm.getImports();
        this.columns = tm.getColumns();
        this.classAnnotation = tm.getRemarks();
    }

    public ModelClass(TableModel tm, String packagePath, String extendsClass){
        this.className = "Q"+JdbcUtil.getEntityName(tm.getTableName());
        this.packagePath = packagePath;
        this.imports = tm.getImports();
        this.extendsClass = extendsClass;
        this.columns = tm.getColumns();
        this.classAnnotation = tm.getRemarks();
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

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public String getClassAnnotation() {
        return classAnnotation;
    }

    public void setClassAnnotation(String classAnnotation) {
        this.classAnnotation = classAnnotation;
    }

    public String getExtendsClass() {
        return extendsClass;
    }

    public void setExtendsClass(String extendsClass) {
        this.extendsClass = extendsClass;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }
}
