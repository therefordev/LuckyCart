package smarso.inn.shop.luckycart.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import smarso.inn.shop.luckycart.definitions.shopping.Product;

/**
 * Product API Interface
 *
 */
public interface ProductAPI {

    /**
     *
     * @param productId
     * @param attrs
     * @return
     */
    @GET("/product/{product_id}")
    Call<Product> getProduct(@Path("product_id") int productId,
                             @Query("attributes[]")String ... attrs);
}
