package com.hejun.eduService.pojo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hejun.eduService.pojo.entity.Teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("TeacherDto教师数据传输对象")
@Data
@Component
public class TeacherDto  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	    @ApiModelProperty(value = "讲师姓名")
	    private String name;

	    @ApiModelProperty(value = "讲师简介")
	    private String intro;

	    @ApiModelProperty(value = "讲师资历，一句话说明讲师")
	    private String career;

	    @ApiModelProperty(value = "头衔，1高级讲师，2首席讲师")
	    private Integer level;

	    @ApiModelProperty(value = "讲师头像")
	    private String avatar;
	    
}
