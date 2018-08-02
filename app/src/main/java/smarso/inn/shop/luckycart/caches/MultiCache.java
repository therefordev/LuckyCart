package smarso.inn.shop.luckycart.caches;

import android.util.SparseArray;

/**
 * Multi-Cache Abstraction Layer
 *
 */
public abstract class MultiCache <T> {

    /** */
    protected SparseArray<T> data = new SparseArray<T>();

    /**
     *
     * @param id
     * @return
     */
    public synchronized T get(int id) {
        return data.get(id);
    }

    /**
     *
     * @param id
     * @param cache
     */
    public synchronized void set(int id,T cache) {
        data.put(id,cache);
    }

    /**
     *
     * @return
     */
    public synchronized int size() {
        return data.size();
    }
}
