package mo.springmvc.defaultconfiguration.test.annotation;

import mo.springmvc.defaultconfiguration.BaseSpringMvcDefaultConfig;

import java.lang.annotation.*;

/**
 * @author WindShadow
 * @verion 2020/8/18.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BaseConfig {

    Class<? extends BaseSpringMvcDefaultConfig> baseConfigClass() default BaseSpringMvcDefaultConfig.class;
}
