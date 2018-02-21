package ${serviceClass.packagePath};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<#if serviceClass.findByPage>
import ${pageResponseClassPath};
</#if>
<#if serviceClass.query>
import java.util.List;
</#if>
<#if (serviceClass.baseService)??>
import ${serviceClass.baseService};
import ${daoClass.baseDao};
import ${daoClass.packagePath}.${daoClass.className};
</#if>
import ${entityClassPath};
import ${queryClassPath};
@Service
public class ${serviceClass.className} <#if (serviceClass.baseServiceName)??>extends ${serviceClass.baseServiceName}<${entityClass.className}, ${queryClass.className}></#if> {
    @Autowired
    private ${daoClass.className} ${daoClass.className ? uncap_first};

    <#if (serviceClass.baseServiceName)??>
    protected ${daoClass.baseDaoName}<${entityClass.className}, ${queryClass.className}> getDao(){
            return ${daoClass.className ? uncap_first};
    }
    </#if>
    <#if serviceClass.get>

    public ${entityClass.className} get(${entityClass.className} entity){
        return ${daoClass.className ? uncap_first}.get(entity);
    }
    </#if>
    <#if serviceClass.insert>

    @Transactional
    public int insert(${entityClass.className} entity){
        return ${daoClass.className ? uncap_first}.insert(entity)>0;
    }
    </#if>
    <#if serviceClass.update>

    @Transactional
    public int update(${entityClass.className} entity){
        return ${daoClass.className ? uncap_first}.update(entity)>0;
    }
    </#if>
    <#if serviceClass.sensitiveUpdate>

    @Transactional
    public int sensitiveUpdate(${entityClass.className} entity){
        return ${daoClass.className ? uncap_first}.sensitiveUpdate(entity)>0;
    }
    </#if>
    <#if serviceClass.delete>

    @Transactional
    public int delete(${entityClass.className} entity){
        return ${daoClass.className ? uncap_first}.delete(entity)>0;
    }
    </#if>
    <#if serviceClass.exists>

    public boolean exists(${entityClass.className} entity){
        return ${daoClass.className ? uncap_first}.exists(entity)>0;
    }
    </#if>
    <#if serviceClass.findByPage>

    public Page findByPage(${queryClass.className} query){
        return new Page(${daoClass.className ? uncap_first}.findByPage(query), ${daoClass.className ? uncap_first}.count(query));
    }
    </#if>
    <#if serviceClass.count>

    public int count(${queryClass.className} query){
        return ${daoClass.className ? uncap_first}.count(query);
    }
    </#if>
    <#if serviceClass.query>

    public List<${entityClass.className}> query(${queryClass.className} query){
        return ${daoClass.className ? uncap_first}.query(query);
    }
    </#if>
}