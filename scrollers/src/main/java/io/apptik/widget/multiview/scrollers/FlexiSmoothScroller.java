package io.apptik.widget.multiview.scrollers;


import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;

public abstract class FlexiSmoothScroller extends BaseSmoothScroller {

    public FlexiSmoothScroller(Context context) {
        super(context);
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
}
