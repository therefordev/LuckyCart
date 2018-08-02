package smarso.inn.shop.luckycart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import smarso.inn.shop.luckycart.R;
import smarso.inn.shop.luckycart.definitions.shopping.Product;
import smarso.inn.shop.luckycart.fragments.ProductDetailFragment;

/**
 * Product Activity Class
 *
 */
public class ProductActivity extends LuckyCartActivity {

    /** */
    protected Product product;

    /** */
    protected int produtId;

    /** */
    protected ProductDetailFragment fragment;

    /**
     *
     */
    public ProductActivity() {
        super();
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a__product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(ProductDetailFragment.PRODUCT_ID,
                    getIntent().getIntExtra(ProductDetailFragment.PRODUCT_ID, 0));

            fragment = new ProductDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();

            setActionBarTitle(R.string.loading);
        }
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ShopFrontActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
