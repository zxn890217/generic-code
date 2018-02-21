package ${controllerClass.packagePath};


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import ${responseResultClassPath};
<#if controllerClass.list>
import ${pageResponseClassPath};
</#if>
<#if controllerClass.query>
import java.util.List;
</#if>
<#if (controllerClass.baseController)??>
import ${controllerClass.baseController};
import ${serviceClass.baseService};
import ${serviceClass.packagePath}.${serviceClass.className};
</#if>
import ${entityClassPath};
import ${queryClassPath};

@RestController
@RequestMapping(value="${pageClass.baseUrl}")
public class ${controllerClass.className} <#if (controllerClass.baseControllerName)??>extends ${controllerClass.baseControllerName}<${entityClass.className}, ${queryClass.className}></#if> {
    @Autowired
    private ${serviceClass.className} ${serviceClass.className ? uncap_first};

    <#if (controllerClass.baseControllerName)??>
    protected ${serviceClass.baseServiceName}<${entityClass.className}, ${queryClass.className}> getService(){
            return ${serviceClass.className ? uncap_first};
    }
    </#if>
    <#if controllerClass.detail>

    @RequestMapping(value="/detail")
    public ${entityClass.className} detail(${entityClass.className} entity){
        return ${serviceClass.className ? uncap_first}.get(entity);
    }

    </#if>
    <#if controllerClass.add>

    @RequestMapping(value="/add")
    public ResponseResult add(${entityClass.className} entity){
        if(${serviceClass.className ? uncap_first}.insert(entity)){
            return new ResponseResult(true, "保存成功");
        }
        return new ResponseResult(false, "保存失败");
    }
    </#if>
    <#if controllerClass.update>

    @RequestMapping(value="/update")
    public ResponseResult update(${entityClass.className} entity){
        if(${serviceClass.className ? uncap_first}.update(entity)){
            return new ResponseResult(true, "修改成功");
        }
        return new ResponseResult(false, "修改失败");
    }
    </#if>
    <#if controllerClass.delete>

    @RequestMapping(value="/delete")
    public ResponseResult delete(${entityClass.className} entity){
        if(${serviceClass.className ? uncap_first}.delete(entity)){
            return new ResponseResult(true, "删除成功");
        }
        return new ResponseResult(false, "删除失败");
    }
    </#if>
    <#if controllerClass.exists>

    @RequestMapping(value="/exists")
    public ResponseResult exists(${entityClass.className} entity){
        if(${serviceClass.className ? uncap_first}.exists(entity)){
            return new ResponseResult(true, "已存在");
        }
        return new ResponseResult(false, "不存在");
    }
    </#if>
    <#if controllerClass.list>

    @RequestMapping(value="/list")
    public Page list(${queryClass.className} query){
        return ${serviceClass.className ? uncap_first}.findByPage(query);
    }
    </#if>
    <#if controllerClass.query>

    @RequestMapping(value="/query")
    public List<${queryClass.className}> query(${queryClass.className} query){
        return ${serviceClass.className ? uncap_first}.query(query);
    }
    </#if>
}