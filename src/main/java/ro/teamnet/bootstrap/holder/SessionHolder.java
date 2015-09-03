package ro.teamnet.bootstrap.holder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Un storage cu scope session
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class SessionHolder {

    public Map<String,Object> content=new ConcurrentHashMap<>();

    public Object get(Object key) {
        return content.get(key);
    }

    public Object put(String key, Object value) {
        return content.put(key, value);
    }

    public boolean containsKey(Object key) {
        return content.containsKey(key);
    }

    public Object remove(Object key) {
        return content.remove(key);
    }
}
