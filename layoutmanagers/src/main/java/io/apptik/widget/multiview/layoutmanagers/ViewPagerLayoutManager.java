package io.apptik.widget.multiview.layoutmanagers;


import android.content.Context;
import android.util.AttributeSet;

public class ViewPagerLayoutManager extends SnapperLinearLayoutManager {
    public ViewPagerLayoutManager(Context context) {
        super(context);
        withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        withFlingOneItemOnly(true);
    }

}
