package com.my.gc.utils;

import com.my.gc.config.DataBaseConfig;
import com.my.gc.model.ColumnModel;
import com.my.gc.model.TableModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Component
public class JdbcUtil {
	private static DataBaseConfig config;

	@Autowired
	public void setConfig(DataBaseConfig config) {
		JdbcUtil.config = config;
	}

	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection(){
		try {

			Class.forName(config.getDriver());
			Properties properties = new Properties();
			properties.put("user", config.getUserName());
			properties.put("password", config.getPassword());
			properties.put("remarksReporting","true");//想要获取数据库结构中的注释，这个值是重点
			return DriverManager.getConnection(config.getUrl(), properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取表结构
	 * @param tableName
	 * @return
	 */
	public static TableModel getTableStructure(String tableName){
		List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();
		ColumnModel primaryKeyColumn = null;
		Set<String> imports = new HashSet<String>();
		try {
			//TODO 表相关
			//ResultSet tableSet = metaData.getTables(null, "%",tableName,new String[]{"TABLE"}); 
			//TODO 字段相关
			DatabaseMetaData dbMeta = getConnection().getMetaData();
			List<String> primaryKeys = getPrimaryKeys(dbMeta, tableName);
			ResultSet columnSet = dbMeta.getColumns(null,"%",tableName,"%");
			ColumnModel columnModel = null;
			while(columnSet.next()){
				columnModel = new ColumnModel();
				columnModel.setColumnName(columnSet.getString("COLUMN_NAME"));
				columnModel.setColumnSize(columnSet.getInt("COLUMN_SIZE"));
				columnModel.setDataType(columnSet.getString("DATA_TYPE"));
				columnModel.setRemarks(columnSet.getString("REMARKS"));
				columnModel.setTypeName(columnSet.getString("TYPE_NAME"));
				columnModel.setAutoIncrement(columnSet.getBoolean("IS_AUTOINCREMENT"));
				columnModel.setPrimaryKey(justicPrimaryKey(columnModel.getColumnName(), primaryKeys));
				columnModel.setNullable(columnSet.getInt("NULLABLE"));
				columnModel.setMaxLength(columnSet.getInt("CHAR_OCTET_LENGTH"));
				//String columnClassName = ColumnTypeEnum.getColumnTypeEnumByDBType(columnModel.getTypeName());
				String columnClassName = sqlType2JavaType(columnModel.getTypeName());
				String imp = getImportByJavaType(columnClassName);
				if(StringUtils.isNotEmpty(imp))
					imports.add(imp);
				String fieldName = getFieldName(columnModel.getColumnName());
				String fieldType = null;
				try{
					if(StringUtils.isNotEmpty(columnClassName))
						fieldType = Class.forName(columnClassName).getSimpleName();
					else
						throw new RuntimeException();
				}
				catch(ClassNotFoundException e){
					fieldType = columnClassName;
				}
				columnModel.setFieldName(fieldName);
				columnModel.setColumnClassName(columnClassName);
				columnModel.setFieldType(fieldType);
    			columnModelList.add(columnModel);
    			if(columnModel.isPrimaryKey())
    				primaryKeyColumn=columnModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TableModel table = new TableModel();
		table.setColumns(columnModelList);
		table.setPrimaryKeyColumn(primaryKeyColumn);
		table.setImports(imports);
		table.setTableName(tableName);
		return table;
	}
	/**
	 * 将数据库字段转换成bean属性
	 * @param columnName
	 * @return
	 */
	private static String getFieldName(String columnName) {
		char[]  columnCharArr = columnName.toCharArray();
		StringBuffer sb = new StringBuffer();
		int ad = -1;
		for (int i = 0; i < columnCharArr.length; i++) {
			  char cur = columnCharArr[i];
			  if(cur=='_'){
				  ad = i;
			  }else{
				  if((ad+1)==i&&ad!=-1){
					  sb.append(Character.toUpperCase(cur));
				  }else{
					  sb.append(cur);
				  }
				  ad=-1;
			  }
		}
		return sb.toString();
	}

	public static String getEntityName(String tableName){
		char[]  charArr = tableName.toCharArray();
		StringBuffer sb = new StringBuffer();
		int ad = -1;
		for (int i = 0; i < charArr.length; i++) {
			char cur = charArr[i];
			if(i==0){
				sb.append(Character.toUpperCase(cur));
			}
			else if(cur=='_'){
				ad = i;
			}else{
				if((ad+1)==i&&ad!=-1){
					sb.append(Character.toUpperCase(cur));
				}else{
					sb.append(cur);
				}
				ad=-1;
			}
		}
		return sb.toString();
	}

	/**
	 * 获取表主键
	 * @throws SQLException 
	 * */
	private static List<String> getPrimaryKeys(DatabaseMetaData dbMeta, String tableName) throws SQLException{
		ResultSet pkRSet = dbMeta.getPrimaryKeys(null, null, tableName);
		List<String> primaryKyes = new ArrayList<String>();
		while(pkRSet.next()){
			primaryKyes.add(pkRSet.getObject("COLUMN_NAME").toString());
		}
		return primaryKyes;
	}
	/**
	 * 判断列是否为主键列
	 * */
	private static boolean justicPrimaryKey(String columnName, List<String> primaryKyes){
		for(String key : primaryKyes)
			if(key.equals(columnName))
				return true;
		return false;
	}
	 /**
	  * 功能：获得列的数据类型
	  * @param sqlType
	  * @return
	  */
	private static String sqlType2JavaType(String sqlType) {
		if(sqlType.equalsIgnoreCase("bit")){
			return "Boolean";
		}else if(sqlType.equalsIgnoreCase("tinyint")){
			return "Byte";
		}else if(sqlType.equalsIgnoreCase("smallint")){
			return "Short";
		}else if(sqlType.equalsIgnoreCase("int")){
			return "Integer";
		}else if(sqlType.equalsIgnoreCase("bigint")){
			return "Long";
		}else if(sqlType.equalsIgnoreCase("float")){
			return "Float";
		}else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") 
		|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money") 
		|| sqlType.equalsIgnoreCase("smallmoney")){
			return "Double";
		}else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") 
		|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar") 
		|| sqlType.equalsIgnoreCase("text")){
			return "String";
		}else if(sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")){
			return "Date";
		}else if(sqlType.equalsIgnoreCase("image")){
			return "Blod";
		}else if(sqlType.equalsIgnoreCase("timestamp")){
			return "Timestamp";
		}else if(sqlType.indexOf("double precision")>=0)
			return "Double";
		return "String";
	}
	/**
	 * 根据数据类型获取需要引入的类
	 * */
	private static String getImportByJavaType(String javaType){
		switch(javaType){
		case "Date": return "java.util.Date";
		case "Timestamp": return "java.sql.Timestamp";
		case "Blod": return "java.sql.Blod";
		}
		return null;
	}


	/**
	 * 获取查询实体类的字段类型
	 * */
	public static String getQueryModelFieldType(String javaType){
		switch(javaType){
			case "byte": return "Byte";
			case "short": return "Short";
			case "int": return "Integer";
			case "float": return "Float";
			case "double": return "Double";
			case "long": return "Long";
			case "Byte": return "Byte";
			case "Short": return "Short";
			case "Int": return "Integer";
			case "Float": return "Float";
			case "Double": return "Double";
			case "Long": return "Long";
		}

		return "String";
	}
	/**
	 * 获取查询字段
	 * */
	public static List<ColumnModel> getQueryFields(TableModel table){
		return table.getColumns();
	}

	public static List<TableModel> getTables(Connection con){
		List<TableModel> list = new ArrayList<TableModel>();
		try {
			DatabaseMetaData dbmd=con.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				TableModel tm = new TableModel();
				tm.setTableName(resultSet.getString("TABLE_NAME"));
				tm.setRemarks(resultSet.getString("REMARKS"));
				list.add(tm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 将首字母变大写
	 * @param str
	 * @return
	 */
	public static String toFirstCharUpCase(String str){
		char[]  columnCharArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columnCharArr.length; i++) {
			char cur = columnCharArr[i];
			if(i==0){
				sb.append(Character.toUpperCase(cur));
			}else{
				sb.append(cur);
			}
		}
		return sb.toString();
	}
	/**
	 * 将首字母变小写
	 * @param str
	 * @return
	 */
	public static String toFirstCharLowerCase(String str){
		char[]  columnCharArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columnCharArr.length; i++) {
			char cur = columnCharArr[i];
			if(i==0){
				sb.append(Character.toLowerCase(cur));
			}else{
				sb.append(cur);
			}
		}
		return sb.toString();
	}
}
