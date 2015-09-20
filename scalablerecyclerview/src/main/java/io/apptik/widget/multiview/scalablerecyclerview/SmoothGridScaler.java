package io.apptik.widget.multiview.scalablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public class SmoothGridScaler extends BaseGridScaler{


    public SmoothGridScaler(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public void scale(Float currScale, Integer currSpan, MotionEvent ev) {

    }
}
