package smarso.inn.shop.luckycart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import smarso.inn.shop.luckycart.R;
import smarso.inn.shop.luckycart.caches.BasketCache;
import smarso.inn.shop.luckycart.caches.ShopCache;
import smarso.inn.shop.luckycart.config.LuckyCartConfig;
import smarso.inn.shop.luckycart.definitions.product.Category;
import smarso.inn.shop.luckycart.definitions.product.Shop;
import smarso.inn.shop.luckycart.fragments.CategoryProductsFragment;

/**
 * Shop Front Activity Class
 *
 */
public class ShopFrontActivity extends LuckyCartActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /** */
    protected boolean multiPanel;

    /** */
    protected final int shopId;

    /** */
    protected Toolbar toolbar;

    /** */
    protected TabLayout tabLayout;

    /** */
    protected ViewPager viewPager;

    /** */
    protected DrawerLayout drawer;

    /** */
    protected NavigationView navigationView;

    /** */
    protected TextView basketCounter;


    /**
     *
     */
    public ShopFrontActivity() {
        super();
        shopId = LuckyCartConfig.getInt(LuckyCartConfig.SHOP_ID);
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a__shop_front);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (findViewById(R.id.item_detail_container) != null) {
            multiPanel = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateBasketCounter();
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_cart);

        MenuItemCompat.setActionView(item, R.layout.b__cart_badge);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        basketCounter = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);

        // Done this way for API version 10
        MenuItemCompat.getActionView(item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.performIdentifierAction(v.getId(),0);
            }
        });

        updateBasketCounter();

        return true;
    }

    /**
     *
     */
    public void updateBasketCounter() {
        if (null != basketCounter) {
            basketCounter.setText("" + BasketCache.instance().get().size());
        }
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        Intent intent = null;

        switch (id) {
            case R.id.action_search : {
                break;
            }
            case R.id.action_cart : {
                intent = new Intent(this,BasketActivity.class);
                break;
            }
        }

        if (null != intent) {
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Shop shop = ShopCache.instance().get();

        for (Category category : shop.categories) {
            Fragment fragment = CategoryProductsFragment.instance(category.id);
            adapter.addFragment(fragment, category.name);
        }

        viewPager.setAdapter(adapter);
    }

    /**
     *
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {

        /** */
        private final List<Fragment> mFragmentList = new ArrayList<>();

        /** */
        private final List<String> mFragmentTitleList = new ArrayList<>();

        /**
         *
         * @param manager
         */
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        /**
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        /**
         *
         * @return
         */
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         *
         * @param fragment
         * @param title
         */
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        /**
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
