package com.hejun.eduService.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hejun.eduService.pojo.entity.Teacher;
import com.hejun.eduService.mapper.TeacherMapper;

@SpringBootTest
public class TestMapper {
	@Autowired
	TeacherMapper mapper;
	@Test
	void test() {
		Teacher teacher = mapper.selectById(1);
		System.out.println(teacher);
	}
}
