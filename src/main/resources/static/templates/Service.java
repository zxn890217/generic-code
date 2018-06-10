package ${serviceClass.packagePath};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${serviceClass.baseService};
import ${daoClass.baseDao};
import ${daoClass.packagePath}.${daoClass.className};

import ${entityClassPath};
@Service
public class ${serviceClass.className} extends ${serviceClass.baseServiceName}<${entityClass.className}, Long> {
    @Autowired
    private ${daoClass.className} ${daoClass.className ? uncap_first};

    protected ${daoClass.baseDaoName}<${entityClass.className}, Long> getDao(){
            return ${daoClass.className ? uncap_first};
    }
}