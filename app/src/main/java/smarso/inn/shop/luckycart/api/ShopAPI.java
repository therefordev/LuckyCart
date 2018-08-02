package smarso.inn.shop.luckycart.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import smarso.inn.shop.luckycart.definitions.product.Shop;

/**
 * Shop API Interface
 *
 */
public interface ShopAPI {

    /**
     *
     * @param shopId
     * @return
     */
    @GET("/shop/{shop_id}")
    Call<Shop> getShop(@Path("shop_id") int shopId);
}
