package pl.mimuw.users;

import java.util.Collections;
import java.util.Map;

public class Utility {
    public static final String MESSAGE = "message";
    public static final String BALANCE = "balance";
    public static final String TOKEN = "token";

    public static Map<?, ?> toResponse(String key, Object value) {
        return Collections.singletonMap(key, value);
    }
}
