package smarso.inn.shop.luckycart.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import smarso.inn.shop.luckycart.R;
import smarso.inn.shop.luckycart.activities.ProductActivity;
import smarso.inn.shop.luckycart.api.CategoryAPI;
import smarso.inn.shop.luckycart.caches.CategoryCache;
import smarso.inn.shop.luckycart.definitions.product.Category;
import smarso.inn.shop.luckycart.definitions.shopping.Product;
import smarso.inn.shop.luckycart.listeners.StaggeredLoaderListener;

/**
 * Category Products Fragment Class
 *
 */
public class CategoryProductsFragment extends LuckyCartFragment implements Callback<Category> {

    /** */
    protected final static String CATEGORY_ID = "__CATEGORY_ID";

    /** */
    protected boolean mTwoPane;

    /** */
    protected int categoryId;

    /** */
    protected StaggeredLoaderListener listener;

    /** */
    protected int limit = 5;

    /** */
    protected int offset = 0;

    /** */
    protected String[] attributes = {
            "price",
            "image"
    };

    /** */
    protected int columns = 2;

    /** */
    protected int staggeredReload = 6;

    /** */
    protected GridAdapter adapter = new GridAdapter();

    /**
     *
     * @param categoryId
     * @return
     */
    public static Fragment instance(int categoryId) {
        Fragment fragment = new CategoryProductsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID,categoryId);

        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     *
     */
    public CategoryProductsFragment() {
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            categoryId = bundle.getInt(CATEGORY_ID);
        }
    }

    /**
     *
     */
    public void getCategory() {
        CategoryAPI service = getRetrofit()
                .create(CategoryAPI.class);
        Call<Category> call = service.getCategory(categoryId,offset,limit,attributes);
        offset+=limit;
        call.enqueue(this);
    }

    /**
     *
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<Category> response, Retrofit retrofit) {
        Category category = CategoryCache.instance().get(categoryId);

        if (null == category) {
            category = response.body();
            CategoryCache.instance().set(categoryId,category);
            listener.resetTrigger(category.products.size());
        } else {
            Category newCategory = response.body();

            // Reset the trigger if we actually update our list!
            if (0 < newCategory.products.size()) {
                category.append(newCategory);
                listener.resetTrigger(category.products.size());
            }
        }

        adapter.notifyDataSetChanged();
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
        return inflater.inflate(R.layout.f__category, container, false);
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.item_list);
        assert recyclerView != null;

        recyclerView.setHasFixedSize(true);

        // The number of Columns
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(columns,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        listener = new StaggeredLoaderListener(mLayoutManager, staggeredReload) {
            @Override
            public void onLoadMore(int offset) {
                getCategory();
            }
        };

        // Ignore deprecation it's needed for backwards compatibility!
        recyclerView.setOnScrollListener(listener);
        setupRecyclerView(recyclerView);

        // Get the categories after we've initialised the components
        getCategory();
    }

    /**
     *
     * @param recyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
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
                    .inflate(R.layout.i__grid_long, parent, false);
            return new ViewHolder(view);
        }

        /**
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Category category = CategoryCache.instance().get(categoryId);

            if (null == category) {
                return;
            }

            List<Product> products = CategoryCache.instance().get(categoryId).products;

            holder.product = products.get(position);

            holder.mNameView.setText(holder.product.name);
            holder.mPriceView.setText(holder.product.price);

            holder.mSaleView.setText(holder.product.discount);
            holder.mSaleView.setPaintFlags(holder.mSaleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Picasso.with(getContext()).load(holder.product.getImage()).resize(0,512).into(holder.mImageView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(ProductDetailFragment.PRODUCT_ID, holder.product.id);
                        ProductDetailFragment fragment = new ProductDetailFragment();
                        fragment.setArguments(arguments);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ProductActivity.class);
                        intent.putExtra(ProductDetailFragment.PRODUCT_ID, holder.product.id);
                        context.startActivity(intent);
                    }
                }
            });
        }

        /**
         *
         * @return
         */
        @Override
        public int getItemCount() {
            Category category = CategoryCache.instance().get(categoryId);
            if (null == category) {
                return 0;
            }

            return category.products.size();
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