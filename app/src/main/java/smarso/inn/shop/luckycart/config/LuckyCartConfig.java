package smarso.inn.shop.luckycart.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Droid Cart Config Singleton
 *
 */
public class LuckyCartConfig {

    /** */
    public final static String BASE_URL = "__BASE_URL";

    /** */
    public final static String SHOP_ID = "__SHOP_ID";

    /** */
    public final static String VERSION_ID = "__VERSION_ID";

    /** */
    private final static LuckyCartConfig instance = new LuckyCartConfig();

    /** */
    private final Map<String,Object> config = new ConcurrentHashMap<String,Object>();

    /**
     *
     */
    private LuckyCartConfig(){}

    /**
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        Object obj = instance.config.get(key);
        return obj.toString();
    }

    /**
     *
     * @param key
     * @return
     */
    public static Integer getInt(String key) {
        Object obj = instance.config.get(key);
        if (null == obj) {
            return new Integer(0);
        }
        return (Integer)obj;
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        instance.config.put(key,value);
    }
}
