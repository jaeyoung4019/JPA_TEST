package toy.project.local_specialty.local_famous_goods.utils.error;

import java.util.HashMap;
import java.util.Map;


public class BindingResultCache {
    private static Map<BindingErrorCacheKey, String> cache = new HashMap<>();

    public BindingResultCache() {
        cache = new HashMap<>();
    }

    public static String getErrorMessageByBingResult(BindingErrorCacheKey key , String message) {
        if (cache.containsKey(key)) {
            System.out.println("cache.get(key) = " + cache.get(key));
            return cache.get(key);
        } else {
            cache.put(key, message);
            return message;
        }
    }

    public Map<BindingErrorCacheKey, String> getCache() {
        return cache;
    }
}
