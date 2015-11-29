package com.devpaul.materiallibrary.abstracts;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Paul on 10/11/2015.
 *
 * Makes listening for scrolls on a recycler view really easy with simple onScrollUp and onScrollDown
 * methods. Currently only works with LinearLayoutManagers.
 */
public abstract class AbstractRecyclerScrollListener extends RecyclerView.OnScrollListener {

    /**
     * Last scroll
     */
    private int mLastScrollY;

    /**
     * Last first visible item.
     */
    private int mPreviousFirstVisibleItem;

    /**
     * The recycler view.
     */
    private RecyclerView mRecyclerView;

    /**
     * Scroll threshold.
     */
    private int mScrollThreshold;

    /**
     * Layout manager for the recycler view.
     */
    private RecyclerView.LayoutManager layoutManager;

    /**
     * Holder for the linear layout manager if it is not null.
     */
    private LinearLayoutManager linearLayoutManager;

    /**
     * Called when the recycler view is scrolled up.
     */
    public abstract void onScrollUp();

    /**
     * Called when the recycler view is scrolled down.
     */
    public abstract void onScrollDown();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(layoutManager instanceof LinearLayoutManager) {
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            if(totalItemCount == 0) {
                return;
            }
            if (isSameRow(firstVisibleItem)) {
                int newScrollY = getTopItemScrollY();
                boolean isSignificantDelta = Math.abs(mLastScrollY - newScrollY) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (mLastScrollY > newScrollY) {
                        onScrollUp();
                    } else {
                        onScrollDown();
                    }
                }
                mLastScrollY = newScrollY;
            } else {
                if (firstVisibleItem > mPreviousFirstVisibleItem) {
                    onScrollUp();
                } else {
                    onScrollDown();
                }

                mLastScrollY = getTopItemScrollY();
                mPreviousFirstVisibleItem = firstVisibleItem;
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    /**
     * Set the threshold to trigger a scroll event.
     * @param scrollThreshold the threshold for scrolling.
     */
    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }

    /**
     * Set the recycler view for the listener. Note that the layout manager for the RecyclerView
     * needs to already have been set.
     * @param recyclerView the recycler view. s
     */
    public void setRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        this.layoutManager = mRecyclerView.getLayoutManager();
        if(this.layoutManager instanceof LinearLayoutManager) {
            this.linearLayoutManager = (LinearLayoutManager) layoutManager;
        }
    }

    /**
     * Check if we're at the same row.
     * @param firstVisibleItem the firstVisibleItem position.
     * @return true if we're at the same row.
     */
    private boolean isSameRow(int firstVisibleItem) {
        return firstVisibleItem == mPreviousFirstVisibleItem;
    }

    /**
     * Return the top item's y position.
     * @return the top items y position.
     */
    private int getTopItemScrollY() {
        if (mRecyclerView == null || mRecyclerView.getChildAt(0) == null) return 0;
        View topChild = mRecyclerView.getChildAt(0);
        return topChild.getTop();
    }
}
