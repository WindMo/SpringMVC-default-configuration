package mo.springmvc.defaultconfiguration.util;

import java.io.IOException;
import java.util.*;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 */

public abstract class BasePropertiesUtil {

    public static Map<String, String> getMapFromPropertiesFile(String resource) throws IOException {
        Properties props = new Properties();
        props.load(BasePropertiesUtil.class.getClassLoader().getResourceAsStream(resource));
        Set<String> keys = props.stringPropertyNames();
        if (keys.isEmpty()) {
            return new HashMap(0);
        } else {
            Map<String, String> map = new HashMap(keys.size() * 4 / 3 + 1);
            Iterator var4 = keys.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                String value = props.getProperty(key);
                map.put(key, value);
            }
            return map;
        }
    }
}
