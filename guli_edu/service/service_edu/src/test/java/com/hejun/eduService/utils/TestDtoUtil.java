package com.hejun.eduService.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;
import com.hejun.commomUtils.utils.DtoUtil;
import com.hejun.commomUtils.utils.DtoUtil2;
import com.hejun.commomUtils.utils.HjBeanUtil;
import com.hejun.eduService.pojo.dto.TeacherDto;
import com.hejun.eduService.pojo.entity.Teacher;

@SpringBootTest
public class TestDtoUtil {
	@Autowired
	Teacher teacher;
//	@Autowired
//	TeacherDto teacherDto;
	
	
	@Test
	//14200ms
	void test1() throws Exception {
		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setName("老王");
		teacherDto.setLevel(2);
		teacherDto.setAvatar("xxxxx");
		teacherDto.setCareer("vvvvv");
		teacherDto.setIntro("kkkkk");
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			teacher = DtoUtil.DtoToEntity(teacherDto,Teacher.class);
		}
		long end = System.currentTimeMillis();
		long time = end-begin;
		System.out.println("test1:"+time);
	
	}
	
	@Test
	//17523ms
	void test3() throws Exception {
		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setName("老王");
		teacherDto.setLevel(2);
		teacherDto.setAvatar("xxxxx");
		teacherDto.setCareer("vvvvv");
		teacherDto.setIntro("kkkkk");
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			teacher = HjBeanUtil.copy(teacherDto,Teacher.class);
		}
		long end = System.currentTimeMillis();
		long time = end-begin;
		System.out.println("test3:"+time);
	
	}
	
	@Test
	//14200ms
	void test12() throws Exception {
		Teacher teacher = new Teacher();
		teacher.setName("老王");
		teacher.setLevel(2);
		teacher.setAvatar("xxxxx");
		teacher.setCareer("vvvvv");
		teacher.setIntro("kkkkk");
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			 TeacherDto copy = HjBeanUtil.copy(teacher,TeacherDto.class);
		}
		long end = System.currentTimeMillis();
		long time = end-begin;
		System.out.println("test1:"+time);
	
	}
	
	
	@Test
	//7837ms
	void test20() {
		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setName("老王");
		teacherDto.setLevel(2);
		teacherDto.setAvatar("xxxxx");
		teacherDto.setCareer("vvvvv");
		teacherDto.setIntro("kkkkk");
		
		long begin = System.currentTimeMillis();
		Teacher teacher2 = null;
		for (int i = 0; i < 100000000; i++) {
			teacher2 = new Teacher();
			BeanCopier.create(TeacherDto.class, Teacher.class, false).copy(teacherDto, teacher2, null);;
		}
		long end = System.currentTimeMillis();
		long time = end-begin;
		System.out.println(teacher2);
		System.out.println("test20:"+time);
	
	}
	
	@Test
	//20000ms
	void test2(){
		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setName("老王");
		teacherDto.setLevel(2);
		teacherDto.setAvatar("xxxxx");
		teacherDto.setCareer("vvvvv");
		teacherDto.setIntro("kkkkk");
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			
			DtoUtil2.copy(teacherDto, teacher);
		}
		long end = System.currentTimeMillis();
		long time = end-begin;
		System.out.println(teacher);
		System.out.println("test20:"+time);
	
	}
	@Test
	//306
	void test21() {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String,Object>();
		map.put("aaaa", "aaaa");
		map.put("bbbb", "bbbb");
		map.put("cccc", "cccc");
		map.put("dddd", "dddd");
		map.put("eeee", "eeee");
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			Object object = map.get("cccc");
		}
		long end = System.currentTimeMillis();
		long time = end-start;
		System.out.println(time);
		
	}
	@Test
	void test22() {
		ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap<Integer,Object>();
		map.put(1111, "aaaa");
		map.put(2222, "bbbb");
		map.put(3333, "cccc");
		map.put(4444, "dddd");
		map.put(5555, "eeee");
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			Object object = map.get(4444);
		}
		long end = System.currentTimeMillis();
		long time = end-start;
		System.out.println(time);
		
	}
	
	@Test
	void test23() {
		ArrayList<Double> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(Math.random());
		}
		Double x = 2.33333;
		ArrayList<Double> list1 = new ArrayList<>(list);
		LinkedList<Double> list2 = new LinkedList<>(list);
		
		//在ArrayList索引末尾添加删除操作
		long start1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list1.remove(list1.size()-1);
			list1.add(x);
		}
		long time1 = System.currentTimeMillis()-start1;
		System.out.println("在ArrayList索引末尾添加删除操作："+time1);
		//在ArrayList索引头部添加删除操作
		long start11 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list1.remove(0);
			list1.add(0,x);
		}
		long time11 = System.currentTimeMillis()-start11;
		System.out.println("在ArrayList索引头部添加删除操作："+time11);
		//在ArrayList索引头部添加,在尾部删除操作
		long start111 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list1.add(0,x);
			list1.remove(list1.size()-1);			
		}
		long time111 = System.currentTimeMillis()-start111;
		System.out.println("在ArrayList索引头部添加,在尾部删除操作："+time111);
		//在LinkedList索引末尾添加删除操作
		long start2 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list2.removeLast();
			list2.addLast(x);
		}
		long time2 = System.currentTimeMillis()-start2;
		System.out.println("在LinkedList索引末尾添加删除操作："+time2);
		//在LinkedList索引头部添加删除操作
		long start22= System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list2.removeFirst();
			list2.addFirst(x);
		}
		long time22 = System.currentTimeMillis()-start22;
		System.out.println("在LinkedList索引头部添加删除操作："+time22);
		//在LinkedList索引头部添加，在尾部删除操作
		long start222= System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list2.removeLast();
			list2.addFirst(x);
		}
		long time222 = System.currentTimeMillis()-start222;
		System.out.println("在LinkedList索引头部添加，在尾部删除操作："+time222);
				
	}
	
	
}
