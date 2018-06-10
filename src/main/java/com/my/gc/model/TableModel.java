package com.my.gc.model;

import java.util.List;
import java.util.Set;
/**
 * 表结构封装
 * */
public class TableModel {
	//表名
	private String tableName;
	//主键列
	private ColumnModel primaryKeyColumn;
	//列
	private List<ColumnModel> columns;
	//需要引入包
	private Set<String> imports;
	//表备注
	private String remarks;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ColumnModel getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(ColumnModel primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public Set<String> getImports() {
		return imports;
	}

	public void setImports(Set<String> imports) {
		this.imports = imports;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
