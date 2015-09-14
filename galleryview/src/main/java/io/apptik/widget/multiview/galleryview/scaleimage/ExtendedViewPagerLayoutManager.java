package io.apptik.widget.multiview.galleryview.scaleimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import io.apptik.widget.multiview.layoutmanagers.ViewPagerLayoutManager;
import io.apptik.widget.multiview.scalablerecyclerview.ScalableRecyclerGridView;


public class ExtendedViewPagerLayoutManager extends ViewPagerLayoutManager {
    public ExtendedViewPagerLayoutManager(Context context) {
        super(context);
    }

    public ExtendedViewPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ExtendedViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        final int layoutDirection = dx > 0 ? 1 : -1;

        if(mRecyclerView != null && mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_SETTLING && mRecyclerView instanceof ScalableRecyclerGridView){
           if(!(mRecyclerView.canScrollHorizontally(layoutDirection))) {
              // adjust();
               return 0;
           }
        }
        return super.scrollHorizontallyBy(dx, recycler, state);

    }
}
