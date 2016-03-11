package io.apptik.multiview.scalablerecyclerview;


import android.view.MotionEvent;

public interface GridScaler {

    int getInitialSpan();

    void scale(Float currScale, Integer currSpan, MotionEvent ev);

}
