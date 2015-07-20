package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ScalableViewGroup extends FrameLayout {



    private float mScaleFactor = 1.f;

    public ScalableViewGroup(Context context) {
        super(context);
    }

    public ScalableViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalableViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.restore();
    }
}
