package com.chat.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工具类，将对象转化为map格式
 * @author wangwb
 *
 */
public class Utils {

	public static HashMap<String, String> objToHash(Object obj) throws IllegalArgumentException,IllegalAccessException { 

		HashMap<String, String> hashMap = new HashMap<String, String>(); 
		Class clazz = obj.getClass(); 
		List<Class> clazzs = new ArrayList<Class>(); 

		do { 
			clazzs.add(clazz); 
			clazz = clazz.getSuperclass(); 
		} while (!clazz.equals(Object.class)); 

		for (Class iClazz : clazzs) { 
			Field[] fields = iClazz.getDeclaredFields(); 
			for (Field field : fields) { 
				Object objVal = null; 
				field.setAccessible(true); 
				objVal = field.get(obj); 
				hashMap.put(field.getName(), objVal.toString()); 
			} 
		} 
		return hashMap; 
	}

	/** 
	 * 使用Introspector进行转换 
	 */    
	public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {    
		if (map == null)   
			return null;    

		Object obj = beanClass.newInstance();  
		String fieldType = "";
		Object value = null;
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
		for (PropertyDescriptor property : propertyDescriptors) {  
			Method setter = property.getWriteMethod();    
			if (setter != null) {
				try {
					System.out.println(property.getPropertyType());
					fieldType = property.getPropertyType().toString();
					if(fieldType.endsWith("int")||fieldType.endsWith("Integer")){
						value = Integer.parseInt((String) map.get(property.getName()));
					}else if(fieldType.endsWith("String")){
						value =(String) map.get(property.getName());
					}
					setter.invoke(obj, value);
				} catch (Exception e) {
					e.printStackTrace();
				}   
			}  
		}  

		return obj;  
	}    

	public static Map<String, String> objectToMap(Object obj) throws Exception {    
		if(obj == null)  
			return null;      

		Map<String, String> map = new HashMap<String, String>();   

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
		for (PropertyDescriptor property : propertyDescriptors) {    
			String key = property.getName();    
			if (key.compareToIgnoreCase("class") == 0) {   
				continue;  
			}  
			Method getter = property.getReadMethod();  
			Object value = getter!=null ? getter.invoke(obj) : null;  
			map.put(key, value.toString());  
		}    

		return map;  
	}    
	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
