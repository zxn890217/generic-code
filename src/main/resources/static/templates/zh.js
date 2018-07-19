${entityClass.className ? uncap_first}:{
  <#list entityClass.columns as column>
  <#if !column.primaryKey>
  ${column.fieldName}: '${column.remarks}'<#if column_has_next>,</#if>
  </#if>
  </#list>
}