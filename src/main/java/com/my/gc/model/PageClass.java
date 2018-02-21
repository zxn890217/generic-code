package com.my.gc.model;

/**
 * Created by zxn on 2018/1/5.
 */
public class PageClass {
    private String baseUrl;
    private boolean listPage;
    private boolean addPage;
    private boolean updatePage;
    private boolean detailPage;

    public PageClass(){}

    public PageClass(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isListPage() {
        return listPage;
    }

    public void setListPage(boolean listPage) {
        this.listPage = listPage;
    }

    public boolean isAddPage() {
        return addPage;
    }

    public void setAddPage(boolean addPage) {
        this.addPage = addPage;
    }

    public boolean isUpdatePage() {
        return updatePage;
    }

    public void setUpdatePage(boolean updatePage) {
        this.updatePage = updatePage;
    }

    public boolean isDetailPage() {
        return detailPage;
    }

    public void setDetailPage(boolean detailPage) {
        this.detailPage = detailPage;
    }
}
