package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ScalableViewGroup extends FrameLayout {


    private float mScaleFactor = 1.f;

    public ScalableViewGroup(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public ScalableViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalableViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getScaleFactor() {
        return mScaleFactor;
    }

    public void setScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
//        /canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        //canvas.restore();
        super.onDraw(canvas);

    }


}
