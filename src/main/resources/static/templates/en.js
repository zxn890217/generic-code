${entityClass.className ? uncap_first}:{
  <#list entityClass.columns as column>
  <#if !column.primaryKey>
  ${column.fieldName}: '${column.fieldName ? cap_first}'<#if column_has_next>,</#if>
  </#if>
  </#list>
}