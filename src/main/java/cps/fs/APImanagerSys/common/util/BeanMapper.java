package cps.fs.APImanagerSys.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 值的重新组合
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class BeanMapper {
	
	/**
	 * 值拷贝
	 * @param source
	 * @param target
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	public static <E,T> T map(E source, T target) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		if(null == source || null == target) {
			return null;
		}
		Class<?> sourceClass = source.getClass();
		Class<?> targetClass = target.getClass();
		
		Field[] declaredFields = sourceClass.getDeclaredFields();
		
		for(Field field : declaredFields) {
			field.setAccessible(true);
			String method = "set" + field.getName().substring(0, 1)
					.toUpperCase() + field.getName().substring(1);
			Method declaredMethod = targetClass.getDeclaredMethod(method, field.getType());
			if(field != null) {
				Object o = field.get(source);
				if(null != o) {
					declaredMethod.invoke(target, o);
				}
			}
		}
		return target;
	}
	
	/**
     *根据指定的字段，从传入集合泛型类型中抽出该字段值，并重新组合以该字段类型为泛型的集合（允许重复的值）
     * @param list 传入的集合
     * @param field 需要抽取的字段
     * @param fieldType 该字段类型
     * @param <E> 字段泛型
     * @param <T> 传入集合泛型
     * @return 结果集合
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
	public static <E,T> List<E> toFieldList(String field, Class<E> fieldType,
			List<T> list) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		if (null == field || null == fieldType || null == list || list.size() <= 0){
	            return null;
        }
		Field declaredField=null;
        List targetList = new ArrayList<>();
        for (T t : list) {
            if (declaredField == null){
                declaredField = t.getClass().getDeclaredField(field);
                declaredField.setAccessible(true);
            }
            Object o = declaredField.get(t);
            if (o == null){
                continue;
            }
            targetList.add((E) o);
        }
        return targetList;
	}
	
	
	public static <F, T> Map<F, T> listToMap(Iterable<T> list, String field, Class<F> fieldType) {
        if (list == null || field == null || fieldType == null) {
            {
                return null;
            }
        }
        Field classField = null;
        Map<F, T> map = new LinkedHashMap<>();
        try {
            for (T t : list) {
                if (classField == null) {
                    classField = checkField(t.getClass(), field);
                }
                Object ko = classField.get(t);
                if (ko == null) {
                    {
                        continue;
                    }
                }

                map.put((F) ko, t);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 根据指定的字段，从传入集合泛型类型中抽出该字段值，并重新组合以该字段类型为泛型的集合（允许重复的值）
     * @param list 传入的集合
     * @param field 需要抽取的字段
     * @param fieldType 该字段类型
     * @param <E> 字段泛型
     * @param <T> 传入集合泛型
     * @return 结果集合
     */
    public static <E, T> List<E> getFieldList(Iterable<T> list, String field, Class<E> fieldType){
        return (List<E>) getFieldCollection(list, field, fieldType, new ArrayList<E>());
    }

    /**
     * 根据指定的字段，从传入集合泛型类型中抽出该字段值，并重新组合以该字段类型为泛型的集合
     * @param list 传入的集合
     * @param field 需要抽取的字段
     * @param fieldType 该字段类型
     * @param <E> 字段泛型
     * @param <T> 传入集合泛型
     * @return 结果集合
     */
    public static <E, T> Set<E> getFieldSet(Iterable<T> list, String field, Class<E> fieldType){
        return (Set<E>) getFieldCollection(list, field, fieldType, new HashSet<E>());
    }

    @SuppressWarnings("unchecked")
    public static <E, T> Collection<E> getFieldCollection(Iterable<T> list, String field, Class<E> fieldType, Collection<E> collection) {
        if (list == null || field == null || fieldType == null) {
            return null;
        }
        Field classField = null;
        try {
            for (T t : list) {
                if (classField == null) {
                    classField = checkField(t.getClass(), field);
                }
                Object o = classField.get(t);
                if (o == null) {
                    continue;
                }
                collection.add((E) o);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    /**
     *  根据传入集合，提取指定字段值做为返回Map的Key，此方法与 list2Map 不同之处在于 后者的key应当是集合中的主键概念，即集合中所有
     *  对象的该字段值都不同，所以后者的应用受到该约束，如果有两个或以上的该字段相同的key，那么后来者会替换掉之前的value。
     *
     *  本方法使用场景是提取集合中的某字段，同时集合中可能有多个值在此字段一致，调用此方法后，该字段做返回 Map 的Key，Map的value为
     *  原list在此字段聚合的结果集
     *
     * @param list 需要聚合的数据
     * @param field 指定聚合字段
     * @param fieldType 该字段类型
     * @param <E> K泛型
     * @param <T> 元数据范型
     * @return 聚合结果，结构为 Map<E, List<T>>
     */
    @SuppressWarnings("all")
    public static <E, T> Map<E, List<T>> groupBy(Iterable<T> list, String field, Class<E> fieldType) {
        if (list == null || field == null || fieldType == null) {
            return null;
        }
        Field classField = null;
        Set<E> fieldSet = getFieldSet(list, field, fieldType);
        Map<E, List<T>> group = new HashMap<>();
        for (E e : fieldSet) {
            group.put(e, new ArrayList<T>());
        }
        try {
            for (T t : list) {
                if (classField == null) {
                    classField = checkField(t.getClass(), field);
                }
                group.get((classField.get(t))).add(t);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return group;
    }

    private static Field checkField(Class<?> tClass, String field) throws NoSuchFieldException {
        Field classField = null;
        while (classField == null) {
            if (tClass == Object.class) {
                throw new NoSuchFieldException(field);
            }
            try {
                classField = tClass.getDeclaredField(field);
                classField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                tClass = tClass.getSuperclass();
            }
        }
        return classField;
    }

}
