package com.hejun.eduService.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;

@Configuration //配置类
@EnableTransactionManagement //自动管理事务
@MapperScan("com.hejun.eduService.mapper") //扫描mapper文件夹
public class MyBatisPlusConig {
	/**
	 * 乐观锁插件
	 * @return
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
	    return new OptimisticLockerInterceptor();
	}
	
	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求 默认false
		//paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		// paginationInterceptor.setLimit(500);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		return paginationInterceptor;
	}
	/**
	 * 基于p6spy的第三方执行sql分析打印插件（无需在此配置Bean）
	 */
}
