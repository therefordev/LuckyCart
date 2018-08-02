package smarso.inn.shop.luckycart.caches;

import smarso.inn.shop.luckycart.definitions.product.Shop;

/**
 * Shop Cache Class
 *
 */
public class ShopCache extends Cache<Shop> {

    /** */
    private static final ShopCache instance = new ShopCache();

    /**
     *
     */
    private ShopCache() {}

    /**
     *
     * @return
     */
    public static ShopCache instance() {
        return instance;
    }

    /**
     *
     */
    protected void init() {
        if (null == data) {
            data = new Shop();
        }
    }
}
