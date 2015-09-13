package io.apptik.widget.multiview.scrollers;


import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;

public abstract class FlexiSmoothScroller extends BaseSmoothScroller {

    private Runnable beforeScrollAction;
    private Runnable afterScrollAction;

    public FlexiSmoothScroller(Context context) {
        super(context);
    }

    public Runnable getAfterScrollAction() {
        return afterScrollAction;
    }

    public void setAfterScrollAction(Runnable afterScrollAction) {
        this.afterScrollAction = afterScrollAction;
    }

    public Runnable getBeforeScrollAction() {
        return beforeScrollAction;
    }

    public void setBeforeScrollAction(Runnable beforeScrollAction) {
        this.beforeScrollAction = beforeScrollAction;
    }

    @Override
    protected void onStop() {
        if(afterScrollAction!=null) {
            afterScrollAction.run();
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        if(beforeScrollAction!=null) {
            beforeScrollAction.run();
        }
        super.onStart();
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
