package io.apptik.multiview.scalablerecyclerview;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import io.apptik.multiview.common.Log;

public class SharpGridScaler extends BaseGridScaler{


    public SharpGridScaler(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public void scale(Float currScale, Integer currSpan, MotionEvent ev) {
        Log.d("scale: " + currScale + "/" + currSpan + " :: " +  ev);
        GridLayoutManager lm = (GridLayoutManager)getRecyclerView().getLayoutManager();

        if(lm.getSpanCount() !=currSpan) {
            lm.setSpanCount(currSpan);
            getRecyclerView().getLayoutManager().requestLayout();
        }

        ViewCompat.setScaleX(getRecyclerView(),currScale);
        ViewCompat.setScaleY(getRecyclerView(),currScale);
        getRecyclerView().invalidate();
    }
}
