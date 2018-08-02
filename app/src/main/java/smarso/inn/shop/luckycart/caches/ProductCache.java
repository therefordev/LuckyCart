package smarso.inn.shop.luckycart.caches;

import smarso.inn.shop.luckycart.definitions.shopping.Product;

/**
 * Product Cache Class
 *
 */
public class ProductCache extends MultiCache<Product> {

    /** */
    private static final ProductCache instance = new ProductCache();

    /**
     *
     */
    private ProductCache() {}

    /**
     *
     * @return
     */
    public static ProductCache instance() {
        return instance;
    }
}
