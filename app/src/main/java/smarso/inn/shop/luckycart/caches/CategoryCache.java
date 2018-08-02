package smarso.inn.shop.luckycart.caches;

import smarso.inn.shop.luckycart.definitions.product.Category;

/**
 * Category Cache Class
 *
 */
public class CategoryCache extends MultiCache<Category> {

    /** */
    private static final CategoryCache instance = new CategoryCache();

    /**
     *
     */
    private CategoryCache() {}

    /**
     *
     * @return
     */
    public static CategoryCache instance() {
        return instance;
    }
}
