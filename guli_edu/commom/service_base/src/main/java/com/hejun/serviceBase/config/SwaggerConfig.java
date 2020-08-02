package com.hejun.serviceBase.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import com.google.common.base.Predicates;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * 配置Swagger的Docket的Bean实例
	 * @return
	 */
	@Bean
	//@Profile({"prd","test"}) 这个方式不行
	public Docket docket(Environment environment) {
		//设置要显示swagger的环境
		Profiles profiles = Profiles.of("dev","test");
		//通过environment.acceptsProfiles判断是否处在设定的环境中
		boolean flag = environment.acceptsProfiles(profiles);
		
		Docket docket = new Docket(DocumentationType.SWAGGER_12);
		ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();  
    	Parameter token = tokenPar
    						.name("token").description("用户令牌")
    						.modelRef(new ModelRef("string"))
    						.parameterType("header") 
    						.required(false).build(); //header中的token参数非必填，传空也可以
    	
    	pars.add(token);

		docket.apiInfo(apiInfo())
			.groupName("贺骏")
			//enable配置是否启动Swagger
			.enable(flag)
			//扫描接口
			.select()
			//RequestHandlerSelectors,配置扫描接口的方式
			//basePackage:扫描指定的包
			//any:扫描全部
			//none:不扫描
			//withClassAnnotation:按类上的注解扫描，参数是一个注解的字节码对象
			//whitMethodAnnotation:按方法上的注解扫描，参数是一个注解的字节码对象
			//.apis(RequestHandlerSelectors.basePackage("com.hejun.eduService.controller"))
			//.apis(RequestHandlerSelectors.any())
			//过滤什么路径，any表示不过滤
			.paths(Predicates.not(PathSelectors.regex("/error.*")))//过滤掉自带的error接口
			//.paths(Predicates.not(PathSelectors.regex("/admin/.*")))//过滤掉admin下的接口
			.build()
			.globalOperationParameters(pars);//添加全局请求参数
			
		return docket;
		
	}
	/**
	 * 配置Swagger ui页面信息-->ApiInfo
	 * @return
	 */
	private ApiInfo apiInfo() {
		//作者信息
		Contact contact = new Contact("贺骏", "https://user.qzone.qq.com/914275190", "914275190@qq.com");
		return new ApiInfoBuilder()
					.title("贺骏的SwaggerAPI文档")
					.description("认真学习，认真生活")
					.version("v1.0")
					.termsOfServiceUrl("https://user.qzone.qq.com/914275190")//服务条款
					.contact(contact)
					.license("Apache 2.0")
					.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
					.build();

	}
//	@Bean
//	public Docket docket2() {
//		Docket docket = new Docket(DocumentationType.SWAGGER_2);
//		docket.apiInfo(apiInfo()).groupName("贺骏2");
//		return docket;
//	}
}
