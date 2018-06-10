package ${controllerClass.packagePath};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import ${controllerClass.baseController};
import ${serviceClass.baseService};
import ${serviceClass.packagePath}.${serviceClass.className};
import ${entityClassPath};

@RestController
@RequestMapping(value="${pageClass.baseUrl}")
public class ${controllerClass.className} extends ${controllerClass.baseControllerName}<${entityClass.className}, Long> {
    @Autowired
    private ${serviceClass.className} ${serviceClass.className ? uncap_first};

    protected ${serviceClass.baseServiceName}<${entityClass.className}, Long> getService(){
            return ${serviceClass.className ? uncap_first};
    }
}