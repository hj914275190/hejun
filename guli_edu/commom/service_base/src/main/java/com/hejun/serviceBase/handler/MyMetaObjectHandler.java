package com.hejun.serviceBase.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
										//实体类字段名
		this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime.class, LocalDateTime.now());
		this.strictUpdateFill(metaObject, "gmtModified", LocalDateTime.class, LocalDateTime.now());
		
		
	}

	@Override
	public void updateFill(MetaObject metaObject) {
										//实体类字段名
		this.strictUpdateFill(metaObject, "gmtModified", LocalDateTime.class, LocalDateTime.now());

	}

}
