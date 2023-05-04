package pl.mimuw.transactions;

import java.util.Collections;
import java.util.Map;

public class Utility {
    public static final String MESSAGE = "message";

    public static Map<?, ?> toResponse(String key, Object value) {
        return Collections.singletonMap(key, value);
    }
}
