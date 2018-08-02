package smarso.inn.shop.luckycart.caches;

import smarso.inn.shop.luckycart.definitions.shopping.WishList;

/**
 * Wish List Cache Class
 *
 */
public class WishListCache extends Cache<WishList> {

    /** */
    private static final WishListCache instance = new WishListCache();

    /**
     *
     */
    private WishListCache() {}

    /**
     *
     * @return
     */
    public static WishListCache instance() {
        return instance;
    }

    /**
     *
     */
    protected void init() {
        if (null == data) {
            data = new WishList();
        }
    }
}
