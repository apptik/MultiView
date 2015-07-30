package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FlexiItemAnimator extends BaseItemAnimator{


    private ViewPropertyAnimatorCompat vpaRemove;
    private ViewPropertyAnimatorCompat vpaAdd;
    private ViewPropertyAnimatorCompat vpaChnage;
    private ViewPropertyAnimatorCompat vpaMove;

    public FlexiItemAnimator(ViewPropertyAnimatorCompat vpaAdd,
                             ViewPropertyAnimatorCompat vpaChnage,
                             ViewPropertyAnimatorCompat vpaMove,
                             ViewPropertyAnimatorCompat vpaRemove) {
        this.vpaAdd = vpaAdd;
        this.vpaChnage = vpaChnage;
        this.vpaMove = vpaMove;
        this.vpaRemove = vpaRemove;
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        if(vpaRemove!=null) {
            final View view = holder.itemView;
            final ViewPropertyAnimatorCompat animation = vpaRemove;
            animation.setDuration(getRemoveDuration())
                    .setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchRemoveStarting(holder);
                }

                @Override
                public void onAnimationEnd(View view) {
                    animation.setListener(null);
                    ViewCompat.setAlpha(view, 1);
                    dispatchRemoveFinished(holder);
                    mRemoveAnimations.remove(holder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
        mRemoveAnimations.add(holder);
    }
}
