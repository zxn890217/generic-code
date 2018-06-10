package com.my.gc;

import com.my.gc.config.TemplateConfig;
import com.my.gc.model.*;
import com.my.gc.utils.FreemarkerUtil;
import com.my.gc.utils.JdbcUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenericCodeApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private TemplateConfig config;

	@Test
	public void test(){
		Template t = new Template("user", config);
		//FreemarkerUtil.genTemplate("mybatis.xml", t, System.out);
		//FreemarkerUtil.genTemplate("Dao.java", t, System.out);
		//FreemarkerUtil.genTemplate("Service.java", t, System.out);
		FreemarkerUtil.genTemplate("Controller.java", t, System.out);
		//FreemarkerUtil.genTemplate("Entity.java", t, System.out);
	}
}
