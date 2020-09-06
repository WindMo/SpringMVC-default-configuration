package mo.springmvc.defaultconfiguration.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mo.springmvc.defaultconfiguration.test.TestBean;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 */

public abstract class BaseBeanUtil extends BeanUtils {

    protected static final String CLASS = "class";

    /**
     * bean转map，不包含值为null的属性
     * @param obj bean对象
     * @return map键值对
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> transBeanToMapExcludeNullField(Object obj) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        return transBeanToMap(obj, false);
    }

    /**
     * bean转map，包含值为null的属性
     * @param obj bean对象
     * @return map键值对
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> transBeanToMapIncludeNullField(Object obj) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        return transBeanToMap(obj, true);
    }

    /**
     * bean转map
     * @param obj bean对象，bean必须有getter方法，没有getter方法的属性将不会被添加到map
     * @param includeNullFields 是否包含为null的属性
     * @return map键值对
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static Map<String, Object> transBeanToMap(Object obj, boolean includeNullFields) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        // 解析bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        // 获取属性信息
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<String, Object> map = new HashMap<>(propertyDescriptors.length * 4 / 3 + 1);
        for(PropertyDescriptor property : propertyDescriptors) {

            // 获取属性信息名称
            String key = property.getName();
            // 跳过class信息
            if (!CLASS.equals(key)) {

                // 获取get方法
                Method getter = property.getReadMethod();
                if (getter != null) {

                    // 执行get方法取出返回值
                    Object value = getter.invoke(obj);
                    // 根据是否包含null的属性标志添加到map
                    if (value != null || includeNullFields) {
                        map.put(key, value);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 通过给定的class信息与属性map实例化出一个bean，该bean必须拥有无参构造方法，否则将抛出异常
     * @param clazz
     * @param map
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T transMapToBean(Class<T> clazz, Map<String, Object> map) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        T result = clazz.newInstance();
        transMapToBean2(map,result);
        return result;
    }


    /**
     * map值注入到bean属性中，bean必须有setter方法，没有setter方法的属性使用默认值
     * @param propertyMap map键值映射
     * @param target 注入目标对象
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void transMapToBean2(Map<String, Object> propertyMap, Object target) throws InvocationTargetException, IllegalAccessException {

        BeanUtils.populate(target, propertyMap);
    }


    /**
     * 返回bean属性为null的属性名称
     * @param bean
     * @return
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static List<String> checkBeanNullFields(Object bean) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<String> list = new LinkedList();
        Map<String, Object> fieldMap = transBeanToMap(bean, true);
        fieldMap.forEach((k, v) -> {
            if (v == null) {
                list.add(k);
            }

        });
        return list;
    }

    /**
     * 返回值为null的对象下标
     * @param objects
     * @return
     */
    public static List<Integer> checkNullObjects(Object... objects) {
        List<Integer> list = new LinkedList();

        for(int i = 0; i < objects.length; ++i) {
            if (objects[i] == null) {
                list.add(i);
            }
        }

        return list;
    }

    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
//
//        TestBean bean = new TestBean(null,18);
//        bean.setAge(18);
////        bean.setName("myName");
//        Map<String,Object> map = transBeanToMap(bean,true);
////        map.forEach((k,v) -> System.out.println(k + " = " + v));
//
        TestBean bean2 = new TestBean(null,20);
//        bean2.setAge(20);
//        bean2.setName("+++");

        Map<String, Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name","myName");
        transMapToBean2(map,bean2);
        System.out.println(bean2);

    }


}