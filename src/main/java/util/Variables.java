package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrey on 05.11.2017.
 */
public class Variables {
    private static Variables instance;
    private static Map<String,String> variables;

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

    public Map<String,String> getVariables() {
        return variables;
    }

    public static void put(String key,String value) {
        variables.put(key, value);
    }

    public static String get(String key) {
        return variables.get(key);
    }
}
