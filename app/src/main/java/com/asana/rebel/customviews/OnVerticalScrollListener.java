package com.asana.rebel.customviews;

/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

import android.support.v7.widget.RecyclerView;

public class OnVerticalScrollListener extends RecyclerView.OnScrollListener {
    private ScrollDirectionCallback mScrollDirectionCallback;

    public OnVerticalScrollListener(ScrollDirectionCallback scrollDirectionCallback) {
        mScrollDirectionCallback = scrollDirectionCallback;
    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0) {
            mScrollDirectionCallback.onScrolledUp();
        } else if (dy > 0) {
            mScrollDirectionCallback.onScrolledDown();
        }
    }

    public interface ScrollDirectionCallback {
        void onScrolledUp();

        void onScrolledDown();
    }
}
