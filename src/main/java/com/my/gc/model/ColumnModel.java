package com.my.gc.model;

/**
 * 列模型
 */
public class ColumnModel {
	private boolean isPrimaryKey;
	private boolean isAutoIncrement;
	private String columnName;
	private String dataType;
	private String typeName;
	private String columnClassName;
	private String fieldName;
	private String fieldType;
	private int columnSize;
	private String columnDef;
	private String remarks;
	/**
	 * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
	 * YES -- 该列可以有空值;
	 * NO -- 该列不能为空;
	 * 空字符串--- 不知道该列是否可为空
	 */
	private int nullable;
	private int maxLength;

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ColumnModel [columnName=" + columnName + ", dataType="
				+ dataType + ", typeName=" + typeName + ", columnClassName="
				+ columnClassName + ", fieldName=" + fieldName + ", fieldType="
				+ fieldType + ", columnSize=" + columnSize + ", columnDef="
				+ columnDef + ", remarks=" + remarks + "]";
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public String getColumnClassName() {
		return columnClassName;
	}

	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getNullable() {
		return nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isObject(){
		if (dataType.equals("int") || dataType.equals("short") || dataType.equals("byte") || dataType.equals("float")
				|| dataType.equals("double") || dataType.equals("long") || dataType.equals("boolean")
				|| dataType.equals("char"))
			return false;
		return true;
	}

}

