package smarso.inn.shop.luckycart.definitions.product;

import java.util.List;

import smarso.inn.shop.luckycart.definitions.Definition;
import smarso.inn.shop.luckycart.definitions.shopping.Product;

/**
 * Category Definition Class
 *
 */
public class Category extends Definition {

    /** */
    public List<Product> products;

    /** */
    public int productCount;

    /**
     *
     * @param category
     */
    public void append(Category category) {
        for (Product product:category.products) {
            products.add(product);
        }
    }
}
