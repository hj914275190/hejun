package com.hejun.commomUtils.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;


/**
 * DTO工具类,可以实现Dto和Entity的互转，前提是Dto字段为Entity字段子集，通过缓存反射元素，提升了反射执行效率
 *
 * @author HeJun
 *
 */
public class DtoUtil2 {
    // 创建反射元素缓存池
	private static BeanCopierCachePool cachePool = new BeanCopierCachePool(); 
	
	/**
	 * 将DTO转换成Entity，前提DTO的所有字段必须是Entity字段的子集
	 * @param <T>
	 * @param <T>
	 * @param entityClazz
	 * @param dto
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void copy(Object source,Object target){
		
		Class<?> sourceClass = source.getClass();
		Class<?> targetClass = target.getClass();
		BeanCopier beanCopier = addBeanCopierToPool(sourceClass, targetClass);
		beanCopier.copy(source, target, null);		
	}
	

	
	/**
	 * 向缓存池添加entity反射元素缓存
	 * @param entityClazz
	 * @throws Exception
	 */
	private static BeanCopier addBeanCopierToPool(Class<?> sourceClass,Class<?> targetClass){
		
		BeanCopierCache beanCopierCache = cachePool.get(sourceClass);
		BeanCopier beanCopier = null;
		if(beanCopierCache!=null) {
			beanCopierCache.get(targetClass);
		}
		//如果缓存池中没有entity的缓存，就添加
		if(beanCopier == null) {
			beanCopier = createBeanCopier(sourceClass, targetClass);			
			//entity反射元素缓存放入缓存池中
			cachePool.put(sourceClass, new BeanCopierCache(targetClass,beanCopier));
		}
		return beanCopier;
	}
	

	
	private static BeanCopier createBeanCopier(Class<?> sourceClass,Class<?> targetClass) {
		return BeanCopier.create(sourceClass, targetClass, false);
	}
		

	static class BeanCopierCache extends ConcurrentHashMap<Class<?>, BeanCopier>{
		
		private static final long serialVersionUID = 1L;
		
		BeanCopierCache(Class<?> targetClass,BeanCopier beanCopier){
			this.put(targetClass, beanCopier);
		}
		 
		
	}
	
	static class BeanCopierCachePool extends ConcurrentHashMap<Class<?>, BeanCopierCache>{

		private static final long serialVersionUID = 1L;
		
	}
}
