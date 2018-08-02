package smarso.inn.shop.luckycart.definitions.shopping;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Product List Definition Class
 *
 */
abstract public class ProductList implements Iterable<Product> {

    /** */
    public Map<Integer,Product> products = new ConcurrentHashMap<Integer,Product>();

    /**
     *
     * @param product
     * @return
     */
    public ProductList addProduct(Product product) {
        this.products.put(product.id,product);
        return this;
    }

    /**
     *
     * @param product
     * @return
     */
    public ProductList removeProduct(Product product) {
        return removeProduct(product.id);
    }

    /**
     *
     * @param product
     * @return
     */
    public ProductList removeProduct(Integer product) {
        this.products.remove(product);
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator<Product> iterator() {
        return products.values().iterator();
    }

    /**
     *
     * @return
     */
    public int size() {
        return products.size();
    }

    /**
     *
     * @param key
     * @return
     */
    public Product get(Integer key) {
        return products.get(key);
    }

    /**
     *
     * @param id
     * @return
     */
    public Product getIndex(int id) {

        Iterator<Product> products = iterator();

        int idx = 0;

        while(products.hasNext()) {
            Product product = products.next();
            if (id == idx++) {
                return product;
            }
        }

        return null;
    }

}
