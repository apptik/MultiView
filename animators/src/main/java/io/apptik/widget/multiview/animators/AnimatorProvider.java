package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

public interface AnimatorProvider {

    ViewPropertyAnimatorCompat getAnim(final RecyclerView.ViewHolder viewHolder, Object... args);
    Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args);
    Runnable getAfterAction(final RecyclerView.ViewHolder viewHolder, Object... args);
}
