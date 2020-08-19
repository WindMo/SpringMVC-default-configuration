package mo.springmvc.defaultconfiguration.test;

import mo.springmvc.defaultconfiguration.BaseSpringMvcDefaultConfig;
import mo.springmvc.defaultconfiguration.test.annotation.BaseConfig;
import mo.springmvc.defaultconfiguration.util.BaseAnnotationUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

/**
 * 缺省SpringMVC配置单元测试基类
 * @author WindShadow
 * @verion 2020/8/17.
 */
@Deprecated
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseSpringMvcDefaultConfigurationTest.InnerRootConfig.class})
@BaseConfig
public abstract class BaseSpringMvcDefaultConfigurationTest<T extends BaseSpringMvcDefaultConfig> {

//    @PostConstruct
    public void initMethod() {

        // 获取基本配置类所在包名
        BaseConfig baseConfig = BaseAnnotationUtils.getAnnotation(this.getClass(),BaseConfig.class);
        Class<? extends BaseSpringMvcDefaultConfig> clazz = baseConfig.baseConfigClass();
        String packageName = clazz.getPackage().getName();
        System.out.println("package = " + packageName);
        // “暗箱操作”
        try {

            String basePackage = "basePackages";
//            String classes = "classes";
            BaseAnnotationUtils.changeAnnotaionField(InnerRootConfig.class,ComponentScan.class,basePackage,new String[]{packageName});
//            BaseAnnotationUtils.changeAnnotaionField(this.getClass(),ContextConfiguration.class,classes,new Class<?>[]{InnerRootConfig.class});
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    @BeforeTestMethod
//    public  void init() {
//
//        // 获取基本配置类所在包名
//        BaseConfig baseConfig = BaseAnnotationUtils.getAnnotation(this.getClass(),BaseConfig.class);
//        Class<? extends BaseSpringMvcDefaultConfig> clazz = baseConfig.baseConfigClass();
//        String packageName = clazz.getPackage().getName();
//        System.out.println("package = " + packageName);
//        // “暗箱操作”
//        try {
//
//            String basePackage = "basePackages";
////            String classes = "classes";
//            BaseAnnotationUtils.changeAnnotaionField(InnerRootConfig.class,ComponentScan.class,basePackage,new String[]{packageName});
////            BaseAnnotationUtils.changeAnnotaionField(this.getClass(),ContextConfiguration.class,classes,new Class<?>[]{InnerRootConfig.class});
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    @ComponentScan(excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BaseSpringMvcDefaultConfigurationTest.class),
            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
    })
    @Configuration
    protected static class InnerRootConfig {}
}
