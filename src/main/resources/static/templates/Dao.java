package ${daoClass.packagePath};

<#if daoClass.findByPage || daoClass.query>
import java.util.List;
</#if>
<#if daoClass.updateByConditions>
import org.apache.ibatis.annotations.Param;
</#if>
<#if (daoClass.baseDaoName)??>
import ${daoClass.baseDao};
</#if>
import ${entityClassPath};
import ${queryClassPath};

public interface ${daoClass.className} <#if (daoClass.baseDaoName)??>extends ${daoClass.baseDaoName}<${entityClass.className}, ${queryClass.className}></#if> {
    <#if daoClass.get>
    public ${entityClass.className} get(${entityClass.className} entity);
    </#if>
    <#if daoClass.insert>
    public int insert(${entityClass.className} entity);
    </#if>
    <#if daoClass.update>
    public int update(${entityClass.className} entity);
    </#if>
    <#if daoClass.sensitiveUpdate>
    public int sensitiveUpdate(${entityClass.className} entity);
    </#if>
    <#if daoClass.updateByConditions>
    public int updateByConditions(@Param("entity")${entityClass.className} entity, @Param("query")${queryClass.className} query);
    </#if>
    <#if daoClass.delete>
    public int delete(${entityClass.className} entity);
    </#if>
    <#if daoClass.exists>
    public int exists(${entityClass.className} entity);
    </#if>
    <#if daoClass.findByPage>
    public List<${queryClass.className}> findByPage(${queryClass.className} query);
    </#if>
    <#if daoClass.count>
    public int count(${queryClass.className} query);
    </#if>
    <#if daoClass.query>
    public List<${entityClass.className}> query(${queryClass.className} query);
    </#if>
}