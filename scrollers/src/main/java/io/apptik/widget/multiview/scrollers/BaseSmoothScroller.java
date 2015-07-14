package io.apptik.widget.multiview.scrollers;


import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class BaseSmoothScroller extends LinearSmoothScroller {

    public static final String TAG = BaseSmoothScroller.class.getName();

    public static final int SNAP_START = LinearSmoothScroller.SNAP_TO_START;
    public static final int SNAP_END = LinearSmoothScroller.SNAP_TO_END;;
    public static final int SNAP_CENTER = 3;
    public static final int SNAP_NONE = LinearSmoothScroller.SNAP_TO_ANY;
    public static final float DEFAULT_MILLISECONDS_PER_INCH = 50f;

    private float millisecondsPerInch = DEFAULT_MILLISECONDS_PER_INCH;

    public BaseSmoothScroller(Context context) {
        super(context);
        millisecondsPerInch = calculateSpeedPerPixel(context.getResources().getDisplayMetrics());
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
            RecyclerView.LayoutManager lm = getLayoutManager();
            if (getChildCount() == 0) {
                return null;
            }
            final int firstChildPos = lm.getPosition(lm.getChildAt(0));
            final int direction = targetPosition < firstChildPos != false ? -1 : 1;

            return new PointF(direction, 0);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return DEFAULT_MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
    }
}
