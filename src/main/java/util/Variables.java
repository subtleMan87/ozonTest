package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrey on 05.11.2017.
 */
public class Variables {
    private static Variables instance;
    private static Map <String, Object> variables;

    public static synchronized Variables getInstance() {
        if (instance == null) {
            instance = new Variables();
        }
        return instance;
    }

    private Variables() {
        if (variables == null) {
            variables = new HashMap<>();
        }
    }

    public Map<String,Object> getVariables() {
        return variables;
    }

    public static void put(String key,String value) {
        variables.put(key, value);
    }

    public static Object get(String key) {
        return variables.get(key);
    }
}
