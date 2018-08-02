package smarso.inn.shop.luckycart.fragments;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;

import retrofit.Retrofit;
import smarso.inn.shop.luckycart.activities.LuckyCartActivity;

/**
 * Droid Cart Fragment Abstraction Layer
 *
 */
public abstract class LuckyCartFragment extends Fragment {

    /**
     *
     * @return
     */
    public Retrofit getRetrofit() {
        LuckyCartActivity activity = (LuckyCartActivity)getActivity();
        return activity.getRetrofit();
    }

    /**
     *
     * @return
     */
    public LuckyCartActivity getDroidCartActivity() {
        return (LuckyCartActivity) getActivity();
    }

    /**
     *
     * @param title
     */
    public void setActionBarTitle(String title) {
        LuckyCartActivity activity = getDroidCartActivity();
        if (null == activity) {
            return;
        }
        activity.setActionBarTitle(title);
    }

    /**
     *
     * @param resource
     */
    public void setActionBarTitle(int resource) {
        Resources resources = getResources();
        if (null == resources) {
            return;
        }
        setActionBarTitle(resources.getString(resource));
    }

    /**
     *
     * @param v
     */
    public void expand(final View v) {
        getDroidCartActivity().expand(v);
    }

    /**
     *
     * @param v
     */
    public void collapse(final View v) {
        getDroidCartActivity().collapse(v);
    }

    public void toggle(final View v) {
        if (View.GONE == v.getVisibility()) {
            expand(v);
        } else {
            collapse(v);
        }
    }
}
