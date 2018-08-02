package smarso.inn.shop.luckycart.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import smarso.inn.shop.luckycart.definitions.product.Category;


/**
 * Category API Interface
 *
 */
public interface CategoryAPI {

    /**
     *
     * @param categoryId
     * @param offset
     * @param limit
     * @param attrs
     * @return
     */
    @GET("/category/{category_id}")
    Call<Category> getCategory(@Path("category_id") int categoryId, @Query("offset") int offset,
                               @Query("limit") int limit, @Query("attributes[]")String ... attrs);
}
