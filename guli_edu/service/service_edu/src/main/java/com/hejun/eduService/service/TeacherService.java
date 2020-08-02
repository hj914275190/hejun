package com.hejun.eduService.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hejun.eduService.pojo.entity.Teacher;
import com.hejun.eduService.pojo.param.TeacherParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hejun
 * @since 2020-07-10
 */
public interface TeacherService extends IService<Teacher> {

	 IPage<Teacher> findTeacherByPage(long current,long size,  TeacherParam param);
	 
	 void removeTeacherListCache();
	 
	 void removeTeacherCacheById(Long id);
	 
}
