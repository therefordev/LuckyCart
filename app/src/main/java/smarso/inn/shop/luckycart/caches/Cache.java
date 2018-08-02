package smarso.inn.shop.luckycart.caches;

/**
 * Cache Abstraction Layer
 *
 */
public abstract class Cache <T> {

    /** */
    protected T data;

    protected abstract void init();

    /**
     *
     * @return
     */
    public synchronized T get() {
        if (null == data) {
            init();
        }

        return data;
    }

    /**
     *
     * @param cache
     */
    public synchronized void set(T cache) {
        data = cache;
    }
}
