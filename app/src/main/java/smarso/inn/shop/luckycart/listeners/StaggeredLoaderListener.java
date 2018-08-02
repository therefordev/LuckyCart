package smarso.inn.shop.luckycart.listeners;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by M00SEMARKTWO on 31/12/2015.
 */
public abstract class StaggeredLoaderListener extends RecyclerView.OnScrollListener {

    /** */
    public static String TAG = StaggeredLoaderListener.class.getSimpleName();

    /** */
    protected final StaggeredGridLayoutManager layoutManager;

    /** */
    protected int triggerLoad = 0;

    /** */
    protected int loaded = 0;

    /** */
    protected final int reload;

    /** */
    protected boolean triggered = true;

    /** */
    protected final int[] columns;

    /**
     *
     * @param layoutManager
     */
    public StaggeredLoaderListener(StaggeredGridLayoutManager layoutManager, int reload) {
        this.layoutManager = layoutManager;
        this.reload = reload;
        this.columns = new int[layoutManager.getSpanCount()];
    }

    /**
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // If we've already trigger a request for more don't trigger again
        // also this prevents null ptr exceptions
        if (null == layoutManager || triggered) {
            return;
        }

        if (0 >= loaded) {
            return;
        }

        layoutManager.findLastVisibleItemPositions(columns);

        for (int i : columns) {
            if (0 < i && i >= loaded-reload) {
                triggered = true;
                this.onLoadMore(loaded);
                // Break out here or it will get called for each column that matches
                return;
            }
        }
    }

    /**
     * A notification point to allow the listener to know the previously triggered request
     * for data was fulfilled and we should be ready to trigger additional data
     */
    public void resetTrigger(int loaded) {
        this.loaded = loaded;
        triggered = false;
    }

    /**
     *
     * @param offset
     */
    public abstract void onLoadMore(int offset);
}