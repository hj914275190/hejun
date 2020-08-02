package com.hejun.eduService;

import java.util.ArrayList;
import java.util.Scanner;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 代码自动生成
 * @author HeJun
 *
 */
public class HejunCode {
	
	 /**
	  * 读取控制台内容
	  * @param tip
	  * @return
	  */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (ipt != null) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
	public static void main(String[] args) {
		//0、新建AutoGenerator（代码自动生成器对象）
		AutoGenerator mpg = new AutoGenerator();
		//1、全局配置
			//1.1新建全局配置对象
		GlobalConfig gc = new GlobalConfig();
			//1.2获取当前项目路径（src所在的目录）
		String projectPath = System.getProperty("user.dir");
			//1.3设置代码输出路径
		gc.setOutputDir(projectPath+"/src/main/java");
			//1.4设置作者名字
		gc.setAuthor("hejun");
			//1.5是否打开资源管理器
		gc.setOpen(false);
			//1.6是否覆盖原来生成的代码
		gc.setFileOverride(true);
			//1.7设置service接口的命名方式（去掉I）
		gc.setServiceName("%sService");
			//1.8设置主键类型为雪花算法
		gc.setIdType(IdType.ASSIGN_ID);
			//1.9设置日期的类型，对应java的LocalDateTime类
		gc.setDateType(DateType.TIME_PACK);
			//1.20自动配置swagger文档
		gc.setSwagger2(true);
			//1.21将全局配置注入代码自动生成器对象
		mpg.setGlobalConfig(gc);
		
		
		//2、数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setDbType(DbType.MYSQL);
		dsc.setUrl("jdbc:mysql://localhost:3306/edu?useUnicode=true&useSSl=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
		dsc.setUsername("hejun");
		dsc.setPassword("hj83764872");
		
		mpg.setDataSource(dsc);
		
		//3、包配置
		PackageConfig pc = new PackageConfig();
		pc.setController("controller");
		pc.setEntity("entity");
		pc.setMapper("mapper");
		pc.setModuleName(scanner("模块名"));
		pc.setService("service");
		pc.setParent("com.hejun");
		
		mpg.setPackageInfo(pc);
		
		
		//4、策略配置
		StrategyConfig sc = new StrategyConfig();
			//需映射的表
		sc.setInclude(scanner("表名，多个表名用逗号分隔").split(","));
			//数据库表映射到实体的命名策略，下划线转驼峰
		sc.setNaming(NamingStrategy.underline_to_camel);
			//生成实体时去掉表前缀
		sc.setTablePrefix(scanner("表前缀") + "_"); 
			//数据库表字段映射到实体的命名策略，下划线转驼峰
		sc.setColumnNaming(NamingStrategy.underline_to_camel);
			//是否为Lombok模型
		sc.setEntityLombokModel(true);
			//设置逻辑删除字段
		sc.setLogicDeleteFieldName("deleted");
			//自动填充字段
		TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
		
		TableFill gmtModified = new TableFill("gmt_modified", FieldFill.UPDATE);
		ArrayList<TableFill> list = new ArrayList<>();
		list.add(gmtCreate);
		list.add(gmtModified);
		sc.setTableFillList(list);
			//乐观锁
		sc.setVersionFieldName("Version");
			//是否为restful api风格控制器，为Controller加上@RestConntroller注解，直接返回Json
		sc.setRestControllerStyle(true);
			//mapping中驼峰转下划线
		sc.setControllerMappingHyphenStyle(false);
		
		mpg.setStrategy(sc);
		//5.执行代码生成
		mpg.execute();
		
		
		
		
	}
	
}
