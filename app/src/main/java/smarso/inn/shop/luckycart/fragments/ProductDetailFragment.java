package smarso.inn.shop.luckycart.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import smarso.inn.shop.luckycart.R;
import smarso.inn.shop.luckycart.api.ProductAPI;
import smarso.inn.shop.luckycart.caches.BasketCache;
import smarso.inn.shop.luckycart.caches.ProductCache;
import smarso.inn.shop.luckycart.caches.WishListCache;
import smarso.inn.shop.luckycart.definitions.shopping.Product;

/**
 * Product Detail Fragment Class
 *
 */
public class ProductDetailFragment extends LuckyCartFragment
        implements Callback<Product>, View.OnClickListener {

    /** */
    public static final String PRODUCT_ID = "__PRODUCT_ID";

    /** */
    protected Product product;

    /** */
    protected int productId;

    /** */
    protected String[] attributes = {
            "dimensions",
            "images"
    };

    /**
     *
     */
    public ProductDetailFragment() {
        super();
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(PRODUCT_ID)) {
            productId = getArguments().getInt(PRODUCT_ID);
            getProduct();
        }
    }

    /**
     *
     */
    public void getProduct() {
        ProductAPI service = getRetrofit()
                .create(ProductAPI.class);
        Call<Product> call = service.getProduct(productId,attributes);
        call.enqueue(this);
    }

    /**
     *
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<Product> response, Retrofit retrofit) {
        if (null == product) {
            product = response.body();
            ProductCache.instance().set(productId,product);
        }

        render();
    }

    /**
     *
     */
    public void render() {
        View rootView = getView();

        // Show the product's images
        if (product != null) {
            ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_images);
            viewPager.setAdapter(new ImageAdapter(getChildFragmentManager()));

            // Product Name
            TextView name = (TextView) rootView.findViewById(R.id.details_name);
            name.setText(product.name);

            // Product Price
            TextView price = (TextView) rootView.findViewById(R.id.details_price);
            price.setText(product.price);

            // Add to wish list button
            TextView addToWishList = (TextView) rootView.findViewById(R.id.add_to_wish_list);
            addToWishList.setOnClickListener(this);
            addToWishList.setClickable(true);

            // Add to cart button
            TextView addToCart = (TextView) rootView.findViewById(R.id.add_cart);
            addToCart.setOnClickListener(this);
            addToCart.setClickable(true);

            // Product Description Toggle
            TextView descriptionToggle = (TextView) rootView.findViewById(R.id.product_desc_toggle);
            descriptionToggle.setOnClickListener(this);
            descriptionToggle.setClickable(true);

            // Product Description
            TextView description = (TextView) rootView.findViewById(R.id.product_desc_content);
            description.setText(product.description);

            // Product Returns Toggle
            TextView returnsToggle = (TextView) rootView.findViewById(R.id.product_returns_toggle);
            returnsToggle.setOnClickListener(this);
            returnsToggle.setClickable(true);

            setActionBarTitle(product.name);
        }
    }

    @Override
    public void onClick(final View v) {

        int evt = 0;

        switch(v.getId()) {
            case R.id.add_cart : {
                BasketCache.instance().get().addProduct(product);
                evt = R.string.added_basket;
                break;
            }

            case R.id.add_to_wish_list : {
                WishListCache.instance().get().addProduct(product);
                evt = R.string.added_wishlist;
                break;
            }

            case R.id.product_desc_toggle : {

                final View content = getView().findViewById(R.id.product_desc_content);

                int text = R.string.description_expand;

                if (View.GONE == content.getVisibility()) {
                    text = R.string.description_collapse;
                }

                ((TextView)v).setText(text);

                toggle(content);
                break;
            }

            case R.id.product_returns_toggle : {

                final View content = getView().findViewById(R.id.product_returns_content);

                int text = R.string.returns_expand;

                if (View.GONE == content.getVisibility()) {
                    text = R.string.returns_collapse;
                }

                ((TextView)v).setText(text);

                toggle(content);
                break;
            }
        }

        if (0 < evt) {
            final Snackbar snackbar = Snackbar.make(v, evt, Snackbar.LENGTH_LONG);

            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View inner) {
                    snackbar.dismiss();
                    Snackbar.make(v,"Item Removed",Snackbar.LENGTH_SHORT).show();
                }
            });

            snackbar.setActionTextColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
            snackbar.show();
        }
    }



    /**
     *
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        // TODO: Have a dance?
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f__product_detail, container, false);
        return rootView;
    }

    /**
     *
     */
    public class ImageAdapter extends FragmentPagerAdapter
    {
        /**
         *
         * @param fm
         */
        public ImageAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ImageViewer();

            Bundle save = new Bundle();
            save.putString(ImageViewer.IMAGE_URL, product.images.get(position).url);
            fragment.setArguments(save);

            return fragment;
        }

        /**
         *
         * @return
         */
        @Override
        public int getCount() {
            if (null == product) {
                return 0;
            }

            return product.images.size();
        }
    }

    /**
     *
     */
    public static class ImageViewer extends Fragment
    {
        /** */
        public final static String IMAGE_URL = "__IMAGE_URL";

        /** */
        protected String url;

        /**
         *
         */
        public ImageViewer() {
            super();
        }

        /**
         *
         * @param savedInstanceState
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (null == savedInstanceState) {
                savedInstanceState = getArguments();
            }

            if (null != savedInstanceState) {
                url = savedInstanceState.getString(IMAGE_URL);
            }
        }

        /**
         *
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.f__view_pager_image, container, false);

            if (null != url) {
                Picasso.with(getContext()).load(url).resize(0,512).into((ImageView)view);
            }

            return view;
        }
    }
}
