package smarso.inn.shop.luckycart.definitions.shopping;

import java.util.List;
import java.util.Map;

import smarso.inn.shop.luckycart.definitions.Definition;

/**
 * Product Definition Class
 *
 */
public class Product extends Definition {
    public String description;
    public String price;
    public String discount;
    public String image;

    public List<ProductImage> images;
    public Map<String,Map<String,String>> dimensions;

    /**
     *
     * @return
     */
    public String getImage() {

        if (null != image) {
            return image;
        }

        if (null != images && 0 < images.size()) {
            return images.get(0).url;
        }

        return null;
    }
}
