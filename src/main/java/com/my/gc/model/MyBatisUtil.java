package com.my.gc.model;

import java.util.ArrayList;
import java.util.List;

import com.my.gc.config.DataBaseConfig;
import com.my.gc.utils.JdbcUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * mybatis配置文件生成功能
 * */
@Component
public class MyBatisUtil {
	private static DataBaseConfig config;

	@Autowired
	public void setConfig(DataBaseConfig config) {
		MyBatisUtil.config = config;
	}
	/**
	 * 从表结构中去生成mybatis配置
	 * @param table
	 * @param namespace
	 * @param beanName
	 * @param queryModelName
	 * @return
	 */
	public static String genMapperConfig(TableModel table,String namespace, String beanName, String queryModelName){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\""+namespace+"\">\n");
		//生成resultMap
		String resultMap = beanName+"Map";
		sb.append(genResultMap(beanName, resultMap, table));
		//生成查询字段
		sb.append(genSelectFieldsSQL(table));
		//生成Get SQL
		sb.append(genGetSQL(beanName, resultMap, table));
		//生成插入SQL
		String dbType = "oracle";
		String driver = config.getDriver();
		if(driver.toLowerCase().indexOf("mysql")>0){
			dbType = "";
		}
		if(dbType.equalsIgnoreCase("oracle")){
			sb.append(genSaveSQLOfORCL(beanName, table));
		}
		else{
			sb.append(genSaveSQL(beanName, table));
		}
		//生成修改SQL
		sb.append(genUpdateSQL(beanName, table));
		//生成敏感修改SQL
		sb.append(genSensitiveUpdateSQL(beanName, table));
		//根据查询条件修改
		sb.append(genUpdateByConditions(table));
		//生成删除SQL
		sb.append(genDeleteSQL(beanName, table));
		//生成查询条件SQL
		sb.append(genSelectConditionsSQL(table));
		if(StringUtils.isNotEmpty(queryModelName)){
			//生成分页查询
			if(dbType.equalsIgnoreCase("oracle")){
				sb.append(genFindByPageSQLOfORCL(queryModelName, resultMap, table));
			}
			else{
				sb.append(genFindByPageSQL(queryModelName, resultMap, table));
			}
			//统计
			sb.append(genCountSQL(queryModelName, table));
			//查询
			sb.append(genQuerySQL(queryModelName, resultMap, table));
		}
		sb.append("</mapper>");
		return sb.toString();
	}
	
	private static String genResultMap(String beanName, String resultMap, TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<resultMap type=\""+beanName+"\" id=\""+resultMap+"\">\n");
		if(primaryKeys.size()==1){
			ColumnModel primaryKey = primaryKeys.get(0);
			sb.append("\t\t<id property=\""+primaryKey.getFieldName()+"\" column=\""+primaryKey.getColumnName()+"\"/>\n");
			for(ColumnModel cm : columnModelList){
				if(!cm.isPrimaryKey())
					sb.append("\t\t<result property=\""+cm.getFieldName()+"\" column=\""+cm.getColumnName()+"\"/>\n");
			}
		}
		else
			for(ColumnModel cm : columnModelList){
				sb.append("\t\t<result property=\""+cm.getFieldName()+"\" column=\""+cm.getColumnName()+"\"/>\n");
			}
		sb.append("\t</resultMap>\n\n");
		return sb.toString();
	}
	
	private static String genGetSQL(String beanName, String resultMap, TableModel table){
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--根据主键获取对象-->\n");
		sb.append("\t<select id=\"get\" parameterType=\""+beanName+"\" resultMap=\""+resultMap+"\">\n\t\tSELECT <include refid=\"select-fields\"/>");
		sb.append(" FROM "+table.getTableName() +" \n\t\tWHERE ");
		for(int i=0; i<primaryKeys.size(); i++){
			ColumnModel pk = primaryKeys.get(i);
			sb.append(pk.getColumnName()+"=#{"+pk.getFieldName()+"}");
			if(i<primaryKeys.size()-1){
				sb.append(" and ");
			}
		}
		sb.append("\n\t</select>\n\n");
		return sb.toString();
	}
	
	private static String genSaveSQL(String beanName, TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--保存-->\n");
		if(primaryKeys.size()==1 && primaryKeys.get(0).isAutoIncrement()){
			//自增主键，并返回主键值
			sb.append("\t<insert id=\"save\" parameterType=\""+beanName+"\" useGeneratedKeys=\"true\" keyProperty=\""+primaryKeys.get(0).getFieldName()+"\">\n");
			sb.append("\t\tINSERT INTO "+table.getTableName()+"(");
			int count =0;
			for(ColumnModel cm : columnModelList){
				if(!cm.isPrimaryKey()|| !cm.isAutoIncrement()){
					sb.append(cm.getColumnName());
					sb.append(",");
					count++;
					if(count>6){
						sb.append("\n\t\t");
						count=0;
					}
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")\n\t\tVALUES(");
			count =0;
			for(ColumnModel cm : columnModelList){
				if(!cm.isPrimaryKey()|| !cm.isAutoIncrement()){
					sb.append("#{"+cm.getFieldName()+"}");
					sb.append(",");
					count++;
					if(count>6){
						sb.append("\n\t\t");
						count=0;
					}
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")\n");
			sb.append("\t</insert>\n\n");
		}
		else{
			sb.append("\t<insert id=\"save\" parameterType=\""+beanName+"\">\n");
			sb.append("\t\tINSERT INTO "+table.getTableName()+"(");
			int count =0;
			for(ColumnModel cm : columnModelList){
				if(!cm.isAutoIncrement()){
					sb.append(cm.getColumnName());
					sb.append(",");
					count++;
					if(count>6){
						sb.append("\n\t\t");
						count=0;
					}
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")\n\t\tVALUES(");
			count=0;
			for(ColumnModel cm : columnModelList){
				if(!cm.isAutoIncrement()){
					sb.append("#{"+cm.getFieldName()+"}");
					sb.append(",");
					count++;
					if(count>6){
						sb.append("\n\t\t");
						count=0;
					}
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")\n");
			sb.append("\t</insert>\n\n");
		}
		return sb.toString();
	}
	
	private static String genSaveSQLOfORCL(String beanName, TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--保存-->\n");
		sb.append("\t<insert id=\"save\" parameterType=\""+beanName+"\">\n");
		if(primaryKeys.size()==1 && primaryKeys.get(0).isAutoIncrement()){
			String sequence = null;
			if(table.getTableName().toLowerCase().indexOf("t_")==0){
				sequence = table.getTableName().toLowerCase().replace("t_", "s_");
			}
			else{
				sequence = "s_"+table.getTableName().toLowerCase();
			}
			//自增主键，并返回主键值
			sb.append("\t\t<selectKey keyProperty=\""+primaryKeys.get(0).getFieldName()+"\" resultType=\"int\" order=\"BEFORE\">select "+sequence+".nextval from dual</selectKey>\n");
		}
		sb.append("\t\tINSERT INTO "+table.getTableName()+"(");
		int count = 0;
		for(ColumnModel cm : columnModelList){
			sb.append(cm.getColumnName());
			sb.append(",");
			count++;
			if(count>6){
				sb.append("\n\t\t");
				count=0;
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")\n\t\tVALUES(");
		count = 0;
		for(ColumnModel cm : columnModelList){
			sb.append("#{"+cm.getFieldName()+"}");
			sb.append(",");
			count++;
			if(count>6){
				sb.append("\n\t\t");
				count=0;
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")\n");
		sb.append("\t</insert>\n\n");
		return sb.toString();
	}
	
	private static String genUpdateSQL(String beanName, TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--修改-->\n");
		sb.append("\t<update id=\"update\" parameterType=\""+beanName+"\">\n");
		sb.append("\t\tUPDATE "+table.getTableName()+" SET ");
		int count = 0;
		for(ColumnModel cm : columnModelList){
			if(!cm.isPrimaryKey()){
				sb.append(cm.getColumnName()+"=#{"+cm.getFieldName()+"}");
				sb.append(",");
				count++;
				if(count>4){
					sb.append("\n\t\t");
					count=0;
				}
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n\t\tWHERE ");
		for(int i=0; i<primaryKeys.size(); i++){
			ColumnModel pk = primaryKeys.get(i);
			sb.append(pk.getColumnName()+"=#{"+pk.getFieldName()+"}");
			if(i<primaryKeys.size()-1){
				sb.append(" and ");
			}
		}
		sb.append("\n\t</update>\n\n");
		return sb.toString();
	}
	
	private static String genSensitiveUpdateSQL(String beanName, TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--非空修改-->\n");
		sb.append("\t<update id=\"sensitiveUpdate\" parameterType=\""+beanName+"\">\n");
		sb.append("\t\tUPDATE "+table.getTableName()+" ");
		sb.append("\n\t\t<set>");
		for(ColumnModel cm : columnModelList){
			if(!cm.isPrimaryKey()){
				sb.append("\n\t\t\t<if test=\""+cm.getFieldName()+"!=null\">");
				sb.append(cm.getColumnName()+"=#{"+cm.getFieldName()+"}");
				sb.append(",</if>");
			}
		}
		sb.append("\n\t\t</set>");
		sb.append("\n\t\tWHERE ");
		for(int i=0; i<primaryKeys.size(); i++){
			ColumnModel pk = primaryKeys.get(i);
			sb.append(pk.getColumnName()+"=#{"+pk.getFieldName()+"}");
			if(i<primaryKeys.size()-1){
				sb.append(" and ");
			}
		}
		sb.append("\n\t</update>\n\n");
		return sb.toString();
	}
	
	private static String genDeleteSQL(String beanName, TableModel table){
		List<ColumnModel> primaryKeys = table.getPrimaryKeyColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--删除-->\n");
		sb.append("\t<delete id=\"delete\" parameterType=\""+beanName+"\">\n");
		sb.append("\t\t DELETE FROM "+table.getTableName()+" WHERE ");
		for(int i=0; i<primaryKeys.size(); i++){
			ColumnModel pk = primaryKeys.get(i);
			sb.append(pk.getColumnName()+"=#{"+pk.getFieldName()+"}");
			if(i<primaryKeys.size()-1){
				sb.append(" and ");
			}
		}
		sb.append("\n\t</delete>\n\n");
		return sb.toString();
	}
	
	private static String genSelectFieldsSQL(TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--查询字段-->\n");
		sb.append("\t<sql id=\"select-fields\">\n\t\t");
		for(ColumnModel cm : columnModelList){
			sb.append(cm.getColumnName());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n\t</sql>\n\n");
		return sb.toString();
	}
	
	private static String genSelectConditionsSQL(TableModel table){
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--查询条件-->\n");
		sb.append("\t<sql id=\"select-conditions\">");
		for(ColumnModel cm : JdbcUtil.getQueryFields(table)){
			sb.append("\n\t\t<if test=\""+cm.getFieldName()+"!=null\">");
			sb.append("\n\t\tAND "+cm.getColumnName()+"=#{"+cm.getFieldName()+"}");
			sb.append("\n\t\t</if>");
		}
		sb.append("\n\t</sql>\n\n");
		return sb.toString();
	}
	
	private static String genFindByPageSQL(String queryModelName, String resultMap, TableModel table){
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--分页查询-->\n");
		sb.append("\t<select id=\"findByPage\" parameterType=\""+queryModelName+"\" resultMap=\""+resultMap+"\">");
		sb.append("\n\t\tSELECT <include refid=\"select-fields\"/>");
		sb.append(" FROM "+table.getTableName());
		sb.append("\n\t\t<where>");
		sb.append("\n\t\t\t<include refid=\"select-conditions\"/>");
		sb.append("\n\t\t</where>");
		sb.append("\n\t\t<if test=\"sort!= null\">\n\t\torder by ${sort} ${order}\n\t\t</if>");
		sb.append("\n\t\t<if test=\"rows>0\">\n\t\tlimit #{offset},#{rows}\n\t\t</if>");
		sb.append("\n\t</select>\n\n");
		return sb.toString();
	}
	
	private static String genFindByPageSQLOfORCL(String queryModelName, String resultMap, TableModel table){
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--分页查询-->\n");
		sb.append("\t<select id=\"findByPage\" parameterType=\""+queryModelName+"\" resultMap=\""+resultMap+"\">");
		sb.append("\n\t\tSELECT * FROM (SELECT t.*, ROWNUM rn FROM (");
		sb.append("\n\t\tSELECT <include refid=\"select-fields\"/>");
		sb.append(" FROM "+table.getTableName());
		sb.append("\n\t\t<where>");
		sb.append("\n\t\t\t<include refid=\"select-conditions\"/>");
		sb.append("\n\t\t</where>");
		sb.append("\n\t\t<if test=\"sort!= null\">\n\t\torder by ${sort} ${order}\n\t\t</if>");
		sb.append("\n\t\t)\n\t) ");
		sb.append("\n\t<if test=\"rows>0\">WHERE rn>#{offset} AND (#{offset}+#{rows})>=rn</if>");
		sb.append("\n\t</select>\n\n");
		return sb.toString();
	}
	
	private static String genCountSQL(String queryModelName, TableModel table){
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--统计-->\n");
		sb.append("\t<select id=\"count\" parameterType=\""+queryModelName+"\" resultType=\"int\">");
		sb.append("\n\t\tSELECT count(*) FROM "+table.getTableName());
		sb.append("\n\t\t<where>");
		sb.append("\n\t\t\t<include refid=\"select-conditions\"/>");
		sb.append("\n\t\t</where>");
		sb.append("\n\t</select>\n\n");
		return sb.toString();
	}
	
	private static String genQuerySQL(String queryModelName, String resultMap, TableModel table){
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--查询-->\n");
		sb.append("\t<select id=\"query\" parameterType=\""+queryModelName+"\" resultMap=\""+resultMap+"\">\n");
		sb.append("\t\tSELECT <include refid=\"select-fields\"/> FROM "+table.getTableName());
		sb.append("\n\t\t<where>");
		sb.append("\n\t\t\t<include refid=\"select-conditions\"/>");
		sb.append("\n\t\t</where>");
		sb.append("\n\t\t<if test=\"sort!= null\">\n\t\torder by ${sort} ${order}\n\t\t</if>");
		sb.append("\n\t</select>\n");
		return sb.toString();
	}
	
	private static String genUpdateByConditions(TableModel table){
		List<ColumnModel> columnModelList = table.getColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!--根据查询条件修改-->\n");
		sb.append("\t<update id=\"updateByConditions\">\n");
		sb.append("\t\tUPDATE "+table.getTableName()+" ");
		sb.append("\n\t\t<set>");
		for(ColumnModel cm : columnModelList){
			if(!cm.isPrimaryKey()){
				sb.append("\n\t\t\t<if test=\"entity."+cm.getFieldName()+"!=null\">");
				sb.append(cm.getColumnName()+"=#{entity."+cm.getFieldName()+"}");
				sb.append(",</if>");
			}
		}
		sb.append("\n\t\t</set>");
		sb.append("\n\t\t<where> ");
		for(ColumnModel cm : JdbcUtil.getQueryFields(table)){
			sb.append("\n\t\t\t<if test=\"query."+cm.getFieldName()+"!=null\">");
			sb.append("\n\t\t\tAND "+cm.getColumnName()+"=#{query."+cm.getFieldName()+"}");
			sb.append("\n\t\t\t</if>");
		}
		sb.append("\n\t\t</where> ");
		sb.append("\n\t</update>\n\n");
		return sb.toString();
	}
	
	private static String getSelectFields(List<ColumnModel> columnModelList){
		StringBuffer sb = new StringBuffer();
		for(ColumnModel cm : columnModelList){
			sb.append(cm.getColumnName());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

}
