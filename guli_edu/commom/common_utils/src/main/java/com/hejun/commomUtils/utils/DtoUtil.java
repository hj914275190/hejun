package com.hejun.commomUtils.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * DTO工具类,可以实现Dto和Entity的互转，前提是Dto字段为Entity字段子集，通过缓存反射元素，提升了反射执行效率
 *
 * @author HeJun
 *
 */
public class DtoUtil {
    // 默认entity构造器名字
    private static final String entityConstruct = "entityConstructNameName";
    // 默认dto构造器名字
    private static final String dtoConstruct = "dtoConstruct";
    // 默认dto类字段list名字
    private static final String dtoFieldsListName = "dtoFieldsList";
    // 创建反射元素缓存池
	private static CachePool cachePool = new CachePool(); 
	
	/**
	 * 将DTO转换成Entity，前提DTO的所有字段必须是Entity字段的子集
	 * @param <T>
	 * @param entityClazz
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T DtoToEntity(Object dto,Class<T> entityClazz) throws Exception{
		T entity;						
		//获取dto的类对象
		Class<?> dtoClazz = dto.getClass();
		//如果缓存池中没有dto的缓存就添加,并返回dto缓存
		ClazzCache dtoCache = addDtoToPool(dtoClazz);
		//如果缓存池中没有entity的缓存就添加，并返回entity缓存
		ClazzCache entityCache = addEntityToPool(entityClazz);	
		//从entityCache中获得entity的构造器，并获得实例
		Constructor<T> constructor = (Constructor<T>) entityCache.get(entityConstruct);
		//通过构造器创建entity实例
		entity = constructor.newInstance();
		//从dtoCache中获得dtoFieldsList
		List<Field> dtoFieldsList = (List<Field>) dtoCache.get(dtoFieldsListName);
		//遍历dtoFieldsList中所有的字段
		for (Field dtoField : dtoFieldsList) {
			//通过dto字段的名字，从entityCache缓存中获得entity的字段（dto字段需为entity字段的子集）
			Field entityField = (Field) entityCache.get(dtoField.getName());
			//暴力反射，无视private修饰，可以自由get，set
			dtoField.setAccessible(true);
			entityField.setAccessible(true);
			entityField.set(entity, dtoField.get(dto));
		}
	
		return entity;
		
	}
	
	/**
	 * 将Entity转换成Dto，前提DTO的所有字段必须是Entity字段的子集
	 * @param <T>
	 * @param dtoClazz
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T entityToDto(Object entity,Class<T> dtoClazz) throws Exception{
		T dto;		
		//获取entity的类对象
		Class<?> entityClazz = entity.getClass();
		//如果缓存池中没有dto的缓存就添加,并返回dto缓存
		ClazzCache dtoCache = addDtoToPool(dtoClazz);
		//如果缓存池中没有entity的缓存就添加，并返回entity缓存
		ClazzCache entityCache = addEntityToPool(entityClazz);				
		//从dtoCache中获得dto的构造器
		Constructor<T> constructor = (Constructor<T>) dtoCache.get(dtoConstruct);
		//通过构造器创建dto实例
		dto = constructor.newInstance();
		//从dtoCache中获得dtoFieldsList
		List<Field> dtoFieldsList = (List<Field>) dtoCache.get(dtoFieldsListName);
		//遍历dtoFieldsList中所有的字段
		for (Field dtoField : dtoFieldsList) {
			//通过dto字段的名字，从entityCache缓存中获得entity的字段（dto字段需为entity字段的子集）
			Field entityField = (Field) entityCache.get(dtoField.getName());
			//暴力反射，无视private修饰，可以自由get，set
			dtoField.setAccessible(true);
			entityField.setAccessible(true);
			dtoField.set(dto, entityField.get(entity));
		}	
		
		return dto;
		
	}
	/**
	 * 向缓存池添加dto反射元素缓存
	 * @param dtoClazz
	 * @throws Exception
	 */
	private static ClazzCache addDtoToPool(Class<?> dtoClazz) throws Exception {
		ClazzCache dtoCache = cachePool.get(dtoClazz);	
		//如果缓存中没有dto的缓存就添加
		if(dtoCache == null) {
			dtoCache = getDtoCache(dtoClazz);
			//dtoCache反射元素缓存放入缓存池中
			cachePool.put(dtoClazz, dtoCache);
		}
		return dtoCache;
	}
	/**
	 * 向缓存池添加entity反射元素缓存
	 * @param entityClazz
	 * @throws Exception
	 */
	private static ClazzCache addEntityToPool(Class<?> entityClazz) throws Exception {
		ClazzCache entityCache = cachePool.get(entityClazz);
		//如果缓存池中没有entity的缓存，就添加
		if(entityCache == null) {
			entityCache = getEntityCache(entityClazz);
			//entity反射元素缓存放入缓存池中
			cachePool.put(entityClazz, entityCache);
		}
		return entityCache;
	}
	
	/**
	 * 获得dto缓存
	 * @param dtoClazz
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static ClazzCache getDtoCache(Class<?> dtoClazz) throws NoSuchMethodException, SecurityException {
		
		//获得dto的字段数组
		Field[] dtoFields = dtoClazz.getDeclaredFields();
		List<Field> dtoFieldsList = new ArrayList<>();		
		ClazzCache dtoCache = new ClazzCache();			
		for (Field field : dtoFields) {
			//判断是否是final修饰的字段，如果是就不添加进dtoFields
			if(!Modifier.isFinal(field.getModifiers())) {
				dtoFieldsList.add(field);
			}
			//将dto字段list放入dtoCache中
			dtoCache.put(dtoFieldsListName, dtoFieldsList);
			//将dto的构造器放入dtoCache中
			dtoCache.put(dtoConstruct,dtoClazz.getDeclaredConstructor());
		}
		
		return dtoCache;	
	}
	
	/**
	 * 获得entity缓存
	 * @param entityClazz
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static ClazzCache getEntityCache(Class<?> entityClazz) throws NoSuchMethodException, SecurityException {
		//创建一个map用来存放entity的构造器和字段
		ClazzCache entityCache = new ClazzCache();
			
		//获取entity的字段数组
		Field[] entityfields = entityClazz.getDeclaredFields();
		//遍历entity的字段数组，一一放入entityCache中
		for (Field field : entityfields) {
			entityCache.put(field.getName(), field);
		}
		//将entity的无参构造放入entityCache中
		entityCache.put(entityConstruct, entityClazz.getDeclaredConstructor());
		
		return entityCache;
		
	}
	
		
	static class ClazzCache extends ConcurrentHashMap<String, Object>{

		private static final long serialVersionUID = 1L;
		
	}
	
	static class CachePool extends ConcurrentHashMap<Class<?>, ClazzCache>{

		private static final long serialVersionUID = 1L;
		
	}
}
