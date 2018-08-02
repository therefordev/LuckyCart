package smarso.inn.shop.luckycart.activities;

import android.content.Intent;
import android.os.Bundle;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import smarso.inn.shop.luckycart.R;
import smarso.inn.shop.luckycart.api.ShopAPI;
import smarso.inn.shop.luckycart.caches.ShopCache;
import smarso.inn.shop.luckycart.config.LuckyCartConfig;
import smarso.inn.shop.luckycart.definitions.product.Shop;

/**
 * Splash Activity Class
 *
 */
public class SplashActivity extends LuckyCartActivity implements Callback<Shop> {

    /** */
    protected final int shopId;

    /**
     *
     */
    public SplashActivity() {
        super();
        shopId = LuckyCartConfig.getInt(LuckyCartConfig.SHOP_ID);
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a__splash);

        // Fudge to prevent the black screen between transitions
        overridePendingTransition(0,R.anim.hold);

        // Go get the Shop front details
        this.load();
    }

    /**
     *
     */
    protected void load() {
        ShopAPI service = getRetrofit()
                .create(ShopAPI.class);
        Call<Shop> call = service.getShop(shopId);
        call.enqueue(this);
    }

    /**
     *
     */
    protected void loaded() {
        Intent intent = new Intent(this, ShopFrontActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<Shop> response, Retrofit retrofit) {
        ShopCache.instance().set(response.body());
        loaded();
    }

    /**
     *
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
    }
}
