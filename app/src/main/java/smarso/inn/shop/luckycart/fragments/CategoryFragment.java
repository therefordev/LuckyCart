package smarso.inn.shop.luckycart.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by M00SEMARKTWO on 05/12/2015.
 */
public class CategoryFragment extends Fragment
{
    private boolean mTwoPane;

    public CategoryFragment() {
    }
/*
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View recyclerView = view.findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f__category, container, false);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.i__recycler, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            holder.mNameView.setText(holder.mItem.content);
            holder.mPriceView.setText(holder.mItem.price);

            holder.mSaleView.setText(holder.mItem.sale);
            holder.mSaleView.setPaintFlags(holder.mSaleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.mView.setBackgroundResource(holder.mItem.background);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ProductDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ProductDetailFragment fragment = new ProductDetailFragment();
                        fragment.setArguments(arguments);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ProductFragment.class);
                        intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView mNameView;
            public final TextView mPriceView;
            public final TextView mSaleView;

            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNameView = (TextView) view.findViewById(R.id.name);
                mPriceView = (TextView) view.findViewById(R.id.price);
                mSaleView = (TextView) view.findViewById(R.id.sale);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNameView.getText() + "'";
            }
        }
    }



    public class MyAdapater extends FragmentPagerAdapter
    {
        public MyAdapater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new MyFragment();

            Bundle save = new Bundle();
            save.putInt("POSITION", position);
            fragment.setArguments(save);

            return fragment;
        }

        @Override
        public int getCount() {
            return MyFragment.IMAGES.length;
        }
    }

    public static class MyFragment extends Fragment
    {
        public static final int IMAGES[] = {
                R.drawable.prod_1,
                R.drawable.prod_2,
                R.drawable.prod_3,
                R.drawable.prod_4
        };

        protected int position;

        public MyFragment() {
            super();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (null != savedInstanceState) {
                position = savedInstanceState.getInt("POSITION", 0);
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.f__view_pager_image, container, false);

            ((ImageView)view).setImageResource(IMAGES[position]);

            return view;
        }
    }
*/
}
