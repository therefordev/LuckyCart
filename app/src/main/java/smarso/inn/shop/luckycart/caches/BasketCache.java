package smarso.inn.shop.luckycart.caches;

import smarso.inn.shop.luckycart.definitions.shopping.Basket;

/**
 * Basket Cache Class
 *
 */
public class BasketCache extends Cache<Basket> {

    /** */
    private static final BasketCache instance = new BasketCache();

    /**
     *
     */
    private BasketCache() {}

    /**
     *
     * @return
     */
    public static BasketCache instance() {
        return instance;
    }

    /**
     *
     */
    protected void init() {
        if (null == data) {
            data = new Basket();
        }
    }
}
