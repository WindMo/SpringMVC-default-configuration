package mo.springmvc.defaultconfiguration.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 * 文本文件操作
 */

public abstract class TextFileUtil {

    public static String readStringFromTextFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(TextFileUtil.class.getClassLoader().getResourceAsStream(filePath)));
        StringBuilder sb = new StringBuilder();

        while(true) {
            String str = reader.readLine();
            if (str == null) {
                return sb.toString();
            }

            sb.append(str);
        }
    }

    public static JSONObject readJSONObjectFromTextFile(String filePath) throws IOException {
        String str = readStringFromTextFile(filePath);
        JSONObject object = JSON.parseObject(str);
        return object;
    }

    public static JSONArray readJSONArrayFromTextFile(String filePath) throws IOException {
        String str = readStringFromTextFile(filePath);
        JSONArray array = JSON.parseArray(str);
        return array;
    }

    public static <T> T readObjectFromTextFile(Class<T> clazz, String filePath) throws IOException {
        JSONObject object = readJSONObjectFromTextFile(filePath);
        T t = JSON.toJavaObject(object, clazz);
        return t;
    }

    public static <T> List<T> readObjectListFromTextFile(Class<T> clazz, String filePath) throws IOException {
        JSONArray array = readJSONArrayFromTextFile(filePath);
        List<T> list = (List)array.stream().map((e) -> {
            return ((JSONObject)e).toJavaObject(clazz);
        }).collect(Collectors.toList());
        return list;
    }
}
