package io.apptik.widget.multiview.layoutmanagers;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerLayoutManager extends SnapperLinearLayoutManager {

    PageChangeListener pageChangeListener;

    public ViewPagerLayoutManager(Context context) {
        super(context);
        super.withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        super.withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager withPageChangeListener(PageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
        return this;
    }

    @Override
    public SnapperLinearLayoutManager withFlingOneItemOnly(boolean flingOneItemOnly) {
        throw new IllegalStateException("ViewPagerLayoutManager can show and fling only one item");
    }

    @Override
    public SnapperLinearLayoutManager withShowOneItemOnly(boolean showOneItemOnly) {
        throw new IllegalStateException("ViewPagerLayoutManager can show and fling only one item");
    }

    public void goToNext() {
        scrollToPosition(getCenterItemPosition() + 1);
    }

    public void goToPrev() {
        scrollToPosition(getCenterItemPosition() - 1);
    }

    public int getCurrentPage() {
        return getCenterItemPosition();
    }

    public View getCurrentPageView() {
        if(getChildCount()==1) {
            return getChildAt(0);
        } else {
            return getCenterItem();
        }
    }

    @Override
    protected void onPositionChanging(int currPos, int newPos) {
        if(pageChangeListener!=null) {
            pageChangeListener.onPageChanging(currPos, newPos);
        }
        super.onPositionChanged(currPos, newPos);
    }

    @Override
    protected void onPositionChanged(int prevPos, int newPos) {
        if(pageChangeListener!=null) {
            pageChangeListener.onPageChanged(prevPos, newPos);
        }
        super.onPositionChanged(prevPos, newPos);
    }

    public interface PageChangeListener {
        void onPageChanging(int currPage, int newPage);
        void onPageChanged(int prevPage, int newPage);
    }

}
