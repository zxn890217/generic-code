package ${entityClass.packagePath};
<#if entityClass.imports ??>
<#list entityClass.imports as path>
import ${path};
</#list>
</#if>
public class ${entityClass.className} {
    <#list entityClass.columns as column>
    //${column.remarks}
    private ${column.fieldType} ${column.fieldName};
    </#list>
    <#list entityClass.columns as column>

    public ${column.fieldType} get${column.fieldName ? cap_first}(){
        return ${column.fieldName};
    }

    public void set${column.fieldName ? cap_first}(${column.fieldType} ${column.fieldName}){
        this.${column.fieldName} = ${column.fieldName};
    }
    </#list>
}