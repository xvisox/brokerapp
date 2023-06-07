package pl.mimuw.users;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {
    public static final String MESSAGE = "message";
    public static final String BALANCE = "balance";
    public static final String TOKEN = "token";
    public static final String USERNAME = "username";

    public static Map<?, ?> toResponse(String key, Object value) {
        return Collections.singletonMap(key, value);
    }

    public static Map<?, ?> toResponse(List<String> keys, List<Object> values) {
        Map<String, Object> response = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            response.put(keys.get(i), values.get(i));
        }
        return response;
    }
}
