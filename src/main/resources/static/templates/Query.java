package ${queryClass.packagePath};
<#if queryClass.imports ??>
<#list queryClass.imports as path>
import ${path};
</#list>
</#if>
<#if (queryClass.extendsClass)??>import ${queryClass.extendsClass};</#if>

public class ${queryClass.className} <#if (queryClass.extendsClass)??>extends ${queryExtendClassName}</#if>{
    <#list queryClass.columns as column>
    //${column.remarks}
    private ${column.fieldType} ${column.fieldName};
    </#list>
    <#list queryClass.columns as column>

    public ${column.fieldType} get${column.fieldName ? cap_first}(){
        return ${column.fieldName};
    }

    public void set${column.fieldName ? cap_first}(${column.fieldType} ${column.fieldName}){
        this.${column.fieldName} = ${column.fieldName};
    }
    </#list>
}