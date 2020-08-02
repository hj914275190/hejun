package com.hejun.commomUtils.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * target工具类,可以实现target和source的互转，前提是两者同名字段的类型一致，通过缓存反射元素，提升了反射执行效率
 *
 * @author HeJun
 *
 */
public class HjBeanUtil {
    // 默认source构造器名字
    private static final String sourceConstruct = "sourceConstruct";
    // 默认target构造器名字
    private static final String targetConstruct = "targetConstruct";
    // 默认target类字段list名字
    private static final String targetFieldsListName = "targetFieldsList";
    // 创建反射元素缓存池，饿汉式
	private volatile static CachePool cachePool = new CachePool(); 
	
	/**
	 * 将source转换成target
	 * @param <T>
	 * @param targetClazz
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copy(Object source,Class<T> targetClazz) throws Exception{
		T target;		
		//获取source的类对象
		Class<?> sourceClazz = source.getClass();
		//如果缓存池中没有target的缓存就添加,并返回target缓存
		ClazzCache targetCache = addtargetToPool(targetClazz);
		//如果缓存池中没有source的缓存就添加，并返回source缓存
		ClazzCache sourceCache = addsourceToPool(sourceClazz);				
		//从targetCache中获得target的构造器
		Constructor<T> constructor = (Constructor<T>) targetCache.get(targetConstruct);
		//通过构造器创建target实例
		target = constructor.newInstance();
		//从targetCache中获得targetFieldsList
		List<Field> targetFieldsList = (List<Field>) targetCache.get(targetFieldsListName);
		//遍历targetFieldsList中所有的字段
		for (Field targetField : targetFieldsList) {
			//通过target字段的名字，从sourceCache缓存中获得source的字段（target字段需为source字段的子集）
			Field sourceField = (Field) sourceCache.get(targetField.getName());
			if(sourceField==null) {
				continue;
			}
			//暴力反射，无视private修饰，可以自由get，set
			targetField.setAccessible(true);
			sourceField.setAccessible(true);
			targetField.set(target, sourceField.get(source));
		}	
		
		return target;
		
	}
	/**
	 * 向缓存池添加target反射元素缓存
	 * @param targetClazz
	 * @throws Exception
	 */
	private static ClazzCache addtargetToPool(Class<?> targetClazz) throws Exception {
		ClazzCache targetCache = cachePool.get(targetClazz);	
		//如果缓存中没有target的缓存就添加
		if(targetCache == null) {
			targetCache = gettargetCache(targetClazz);
			//targetCache反射元素缓存放入缓存池中
			cachePool.put(targetClazz, targetCache);
		}
		return targetCache;
	}
	/**
	 * 向缓存池添加source反射元素缓存
	 * @param sourceClazz
	 * @throws Exception
	 */
	private static ClazzCache addsourceToPool(Class<?> sourceClazz) throws Exception {
		ClazzCache sourceCache = cachePool.get(sourceClazz);
		//如果缓存池中没有source的缓存，就添加
		if(sourceCache == null) {
			sourceCache = getsourceCache(sourceClazz);
			//source反射元素缓存放入缓存池中
			cachePool.put(sourceClazz, sourceCache);
		}
		return sourceCache;
	}
	
	/**
	 * 获得target缓存
	 * @param targetClazz
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static ClazzCache gettargetCache(Class<?> targetClazz) throws NoSuchMethodException, SecurityException {
		
		//获得target的字段数组
		Field[] targetFields = targetClazz.getDeclaredFields();
		List<Field> targetFieldsList = new ArrayList<>();		
		ClazzCache targetCache = new ClazzCache();			
		for (Field field : targetFields) {
			//判断是否是final修饰的字段，如果是就不添加进targetFields
			if(!Modifier.isFinal(field.getModifiers())) {
				targetFieldsList.add(field);
			}
			//将target字段list放入targetCache中
			targetCache.put(targetFieldsListName, targetFieldsList);
			//将target的构造器放入targetCache中
			targetCache.put(targetConstruct,targetClazz.getDeclaredConstructor());
		}
		
		return targetCache;	
	}
	
	/**
	 * 获得source缓存
	 * @param sourceClazz
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static ClazzCache getsourceCache(Class<?> sourceClazz) throws NoSuchMethodException, SecurityException {
		//创建一个map用来存放source的构造器和字段
		ClazzCache sourceCache = new ClazzCache();
			
		//获取source的字段数组
		Field[] sourcefields = sourceClazz.getDeclaredFields();
		//遍历source的字段数组，一一放入sourceCache中
		for (Field field : sourcefields) {
			sourceCache.put(field.getName(), field);
		}
		//将source的无参构造放入sourceCache中
		sourceCache.put(sourceConstruct, sourceClazz.getDeclaredConstructor());
		
		return sourceCache;
		
	}
	
		
	static class ClazzCache extends ConcurrentHashMap<String, Object>{

		private static final long serialVersionUID = 1L;
		
	}
	
	static class CachePool extends ConcurrentHashMap<Class<?>, ClazzCache>{

		private static final long serialVersionUID = 1L;
		
	}
}
