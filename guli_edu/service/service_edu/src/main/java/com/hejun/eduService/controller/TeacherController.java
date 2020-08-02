package com.hejun.eduService.controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hejun.commomUtils.utils.DtoUtil;
import com.hejun.eduService.pojo.dto.TeacherDto;
import com.hejun.eduService.pojo.entity.Teacher;
import com.hejun.eduService.pojo.param.TeacherParam;
import com.hejun.eduService.service.TeacherService;
import com.hejun.serviceBase.constant.MyExceptionConst;
import com.hejun.serviceBase.exception.InnerException;
import com.hejun.serviceBase.responseModel.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  教师管理接口
 * </p>
 *
 * @author hejun
 * @since 2020-07-10
 */
@RestController
@RequestMapping("admin/eduService/teacher")
@CrossOrigin
@Api(tags = "教师管理接口")
public class TeacherController {
	
	@Autowired
	TeacherService teacherService;
	
	@GetMapping("getAll")
	@ApiOperation("查询所有的教师")
	public R findAllTeachers() {
		List<Teacher> list = teacherService.list();		
		//throw new InnerException();
		//int x = 1/0;
		return R.ok().data("items", list);
	}
	
	@DeleteMapping("deleteById/{id}")
	@ApiOperation("通过ID删除教师")
	public R deleteTeacherById(@PathVariable Long id) {
		boolean removeById = teacherService.removeById(id);
		if(removeById) {
			teacherService.removeTeacherListCache();
			teacherService.removeTeacherCacheById(id);
			return R.ok();
		}else {
			return R.error("该ID不存在");
		}
	}
	
	@PostMapping("page/{current}/{size}")
	@ApiOperation("分页查询教师")
	public R findTeacherByPage(@PathVariable long current,@PathVariable long size, @RequestBody TeacherParam teacherParam) {
		IPage<Teacher> iPage = teacherService.findTeacherByPage(current, size, teacherParam);
		return R.ok().data("page", iPage);
	}
	
	@PostMapping("add")
	@ApiOperation("注册教师")
	public R addTeacher(@RequestBody TeacherDto teacherDto) throws Exception {
		Teacher teacher = DtoUtil.DtoToEntity(teacherDto,Teacher.class);
		boolean save = teacherService.save(teacher);
		if(save) {
			return R.ok();
		}else {
			return R.error();
		}
	}
	
	@GetMapping("getById/{id}")
	@ApiOperation("通过ID查询一个教师")
	public R findTeacherById(@PathVariable String id) {
		Teacher teacher = teacherService.getById(id);
		return R.ok().data("teacher",teacher);
	}
	
	@PutMapping("update")
	@ApiOperation("更新一个教师")
	public R updateOneTeacher(@RequestBody Teacher teacher ) {		
		boolean updateById = teacherService.updateById(teacher);
		if(updateById) {
			teacherService.removeTeacherListCache();
			teacherService.removeTeacherCacheById(teacher.getId());
			return R.ok();
		}else {
			return R.error();
		}
	
	}
	
	@GetMapping("getInfo/{id}")
	@ApiOperation("通过ID获得教师基本信息")
	public R findTeacherInfo(@PathVariable String id) throws Exception {
		Teacher teacher = teacherService.getById(id);
		if(teacher==null) {
			return R.error("Id不存在");
		}else {
			TeacherDto teacherDto = DtoUtil.entityToDto(teacher,TeacherDto.class);
			return R.ok().data("teacherInfo",teacherDto);
		}
	}
	
}

