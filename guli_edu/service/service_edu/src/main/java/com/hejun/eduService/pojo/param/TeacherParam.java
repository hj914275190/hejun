package com.hejun.eduService.pojo.param;


import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Component
@ApiModel(value = "TeacherParam教师查询参数类")
public class TeacherParam {
	@ApiModelProperty("教师姓名，用于模糊查询")
	@NotBlank
	private String name;
	@ApiModelProperty("教师头衔，1高级讲师，2首席讲师")
	private Integer level;
	@ApiModelProperty(value = "教师注册时间开始日期",example = "2019-01-01")
	private String beginTime;
	@ApiModelProperty(value = "教师注册时间截止日期",example = "2019-12-01")
	private String endTime;
}
