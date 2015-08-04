package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

public interface AnimationProvider {

    ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args);
}
