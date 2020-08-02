package com.hejun.eduService.service.impl;

import com.hejun.eduService.mapper.TeacherMapper;
import com.hejun.eduService.pojo.entity.Teacher;
import com.hejun.eduService.pojo.param.TeacherParam;
import com.hejun.eduService.service.TeacherService;
import lombok.AllArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.ibatis.session.SqlSession;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hejun
 * @since 2020-07-10
 */
@Service
@AllArgsConstructor
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

	TeacherMapper teacherMapper;

	@Override
	@Cacheable(cacheNames = "teacherByPage")
	public IPage<Teacher> findTeacherByPage(long current, long size, TeacherParam param) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		IPage<Teacher> page = new Page<>();	
		//设置分页的当前页和每页行数
		page.setCurrent(current);
		page.setSize(size);
		
		String name = param.getName();
		String beginTime = param.getBeginTime();
		String endTime = param.getEndTime();
		Integer level = param.getLevel();
				
		if(StringUtils.isNotBlank(name)) {
			queryWrapper.like("name", name);
		}
		if (level!=null) {
			queryWrapper.eq("level", level);
		}
		if (StringUtils.isNotBlank(beginTime)) {
			queryWrapper.ge("gmt_create", beginTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			queryWrapper.le("gmt_modified", endTime);
		}
		return teacherMapper.selectPage(page, queryWrapper);
			
	}
	
	@Override
	@CacheEvict(cacheNames = "teacher",key = "teacherList")
	public void removeTeacherListCache() {
		
	}

	@Override
	@CacheEvict(cacheNames = "teacher",key ="#id" )
	public void removeTeacherCacheById(Long id) {
		
	}

	@Override
	@Cacheable(cacheNames = "teacher",key = "#id")
	public Teacher getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	@Cacheable(cacheNames = "teacher",key = "teacherList")
	public List<Teacher> list() {
		return super.list();
	}
	
}
