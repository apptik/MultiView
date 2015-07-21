package io.apptik.widget.multiview.layoutmanagers;


import android.content.Context;
import android.util.AttributeSet;

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
        scrollToPosition(getCenterPosition() + 1);
    }

    public void goToPrev() {
        scrollToPosition(getCenterPosition() - 1);
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
            pageChangeListener.onPageChange(prevPos, newPos);
        }
        super.onPositionChanged(prevPos, newPos);
    }

    public interface PageChangeListener {
        void onPageChanging(int currPage, int newPage);
        void onPageChange(int prevPage, int newPage);
    }

}
