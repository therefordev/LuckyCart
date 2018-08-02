package smarso.inn.shop.luckycart.application;

import android.app.Application;
import android.content.res.Resources;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import smarso.inn.shop.luckycart.config.LuckyCartConfig;
import smarso.inn.shop.luckycart.R;


/**
 * Droid Cart Application Class
 *
 */
public class LuckyCart extends Application {

    /** */
    private Retrofit retrofit = null;

    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        setConfig();
    }

    /**
     *
     */
    private void setConfig() {
        Resources resources = getResources();

        // Shop Base URL
        LuckyCartConfig.set(LuckyCartConfig.BASE_URL,
                resources.getString(R.string.droidcart_shop_base));

        // Shop ID
        LuckyCartConfig.set(LuckyCartConfig.SHOP_ID,
                resources.getInteger(R.integer.droidcart_shop_id));

        // Version ID
        LuckyCartConfig.set(LuckyCartConfig.VERSION_ID,
                resources.getString(R.string.droidcart_version));
    }

    /**
     *
     * @return
     */
    public Retrofit getRetrofit() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                .baseUrl(LuckyCartConfig.getString(LuckyCartConfig.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
}
