package smarso.inn.shop.luckycart.activities;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smarso.inn.shop.luckycart.R;
import smarso.inn.shop.luckycart.caches.BasketCache;
import smarso.inn.shop.luckycart.definitions.shopping.Basket;
import smarso.inn.shop.luckycart.definitions.shopping.Product;
import smarso.inn.shop.luckycart.fragments.ProductDetailFragment;

/**
 * Cart Activity Class
 *
 */
public class BasketActivity extends LuckyCartActivity {

    /** */
    protected GridAdapter adapter = new GridAdapter();

    public Context getActivity() {
        return this;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a__basket);

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

            setActionBarTitle(R.string.basket);
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.basket_contents);
        recyclerView.setHasFixedSize(true);

        // The number of Columns
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     *
     */
    public class GridAdapter
            extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

        /**
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.r__basket, parent, false);
            return new ViewHolder(view);
        }

        /**
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Basket basket = BasketCache.instance().get();

            if (null == basket) {
                return;
            }

            holder.product = basket.getIndex(position);

            holder.mNameView.setText(holder.product.name);
            holder.mPriceView.setText(holder.product.price);

            holder.mSaleView.setText(holder.product.discount);
            holder.mSaleView.setPaintFlags(holder.mSaleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Log.e("Image", "" + holder.product.getImage());

            Picasso.with(getActivity()).load(holder.product.getImage()).resize(0,512).into(holder.mImageView);
        }

        /**
         *
         * @return
         */
        @Override
        public int getItemCount() {
            Basket basket = BasketCache.instance().get();
            if (null == basket) {
                return 0;
            }

            return basket.products.size();
        }

        /**
         *
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView mNameView;
            public final TextView mPriceView;
            public final TextView mSaleView;
            public final ImageView mImageView;

            public Product product;

            /**
             *
             * @param view
             */
            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNameView = (TextView) view.findViewById(R.id.name);
                mPriceView = (TextView) view.findViewById(R.id.price);
                mSaleView = (TextView) view.findViewById(R.id.sale);
                mImageView = (ImageView) view.findViewById(R.id.image_view);
            }

            /**
             *
             * @return
             */
            @Override
            public String toString() {
                return super.toString() + " '" + mNameView.getText() + "'";
            }
        }
    }
}
