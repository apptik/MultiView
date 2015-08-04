package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FlexiItemAnimator extends BaseItemAnimator{


    private AnimationProvider vpaRemove;
    private AnimationProvider vpaAdd;
    private AnimationProvider vpaChnageOld;
    private AnimationProvider vpaChnageNew;
    private AnimationProvider vpaMove;

    public FlexiItemAnimator(AnimationProvider vpaAdd,
                             AnimationProvider vpaChnageOld,
                             AnimationProvider vpaChnageNew,
                             AnimationProvider vpaMove,
                             AnimationProvider vpaRemove) {
        this.vpaAdd = vpaAdd;
        this.vpaChnageOld = vpaChnageOld;
        this.vpaChnageNew = vpaChnageNew;
        this.vpaMove = vpaMove;
        this.vpaRemove = vpaRemove;
    }

    public FlexiItemAnimator(AnimationSetProvider animationSetProvider) {
        this.vpaAdd = animationSetProvider.getAddAnimProvider();
        this.vpaChnageOld = animationSetProvider.getChangeOldItemAnimProvider();
        this.vpaChnageNew = animationSetProvider.getChangeNewItemAnimProvider();
        this.vpaMove = animationSetProvider.getMoveAnimProvider();
        this.vpaRemove = animationSetProvider.getRemoveAnimProvider();
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        if(vpaRemove==null) return;
            final View view = holder.itemView;
            final ViewPropertyAnimatorCompat animation = vpaRemove.getAnim(holder);
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
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        if(vpaAdd==null) return;
        final View view = holder.itemView;
        mAddAnimations.add(holder);
        final ViewPropertyAnimatorCompat animation = vpaAdd.getAnim(holder);
        animation.setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        dispatchAddStarting(holder);
                    }
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setAlpha(view, 1);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        animation.setListener(null);
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

    @Override
    protected void animateMoveImpl(final RecyclerView.ViewHolder holder, final MoveInfo moveInfo) {
        if(vpaMove==null) return;
        final View view = holder.itemView;
        final int deltaX = moveInfo.toX - moveInfo.fromX;
        final int deltaY = moveInfo.toY - moveInfo.fromY;

        // TODO: make EndActions end listeners instead, since end actions aren't called when
        // vpas are canceled (and can't end them. why?)
        // need listener functionality in VPACompat for this. Ick.
        mMoveAnimations.add(holder);
        final ViewPropertyAnimatorCompat animation = vpaMove.getAnim(holder, moveInfo);
        animation.setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                dispatchMoveStarting(holder);
            }
            @Override
            public void onAnimationCancel(View view) {
                if (deltaX != 0) {
                    ViewCompat.setTranslationX(view, 0);
                }
                if (deltaY != 0) {
                    ViewCompat.setTranslationY(view, 0);
                }
            }
            @Override
            public void onAnimationEnd(View view) {
                animation.setListener(null);
                dispatchMoveFinished(holder);
                mMoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
    }

    @Override
    protected void animateChangeImpl(final BaseItemAnimator.ChangeInfo changeInfo) {
        final RecyclerView.ViewHolder holder = changeInfo.oldHolder;
        final View view = holder == null ? null : holder.itemView;
        final RecyclerView.ViewHolder newHolder = changeInfo.newHolder;
        final View newView = newHolder != null ? newHolder.itemView : null;
        if (view != null && vpaChnageOld != null) {
            mChangeAnimations.add(changeInfo.oldHolder);
            final ViewPropertyAnimatorCompat oldViewAnim = vpaChnageOld.getAnim(changeInfo.oldHolder, changeInfo).setDuration(
                    getChangeDuration());
            oldViewAnim.setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                @Override
                public void onAnimationEnd(View view) {
                    oldViewAnim.setListener(null);
                    ViewCompat.setAlpha(view, 1);
                    ViewCompat.setTranslationX(view, 0);
                    ViewCompat.setTranslationY(view, 0);
                    dispatchChangeFinished(changeInfo.oldHolder, true);
                    mChangeAnimations.remove(changeInfo.oldHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
        if (newView != null && vpaChnageNew != null) {
            mChangeAnimations.add(changeInfo.newHolder);
            final ViewPropertyAnimatorCompat newViewAnimation = vpaChnageNew.getAnim(changeInfo.newHolder, changeInfo);
            newViewAnimation.setDuration(getChangeDuration())
                    .setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchChangeStarting(changeInfo.newHolder, false);
                }
                @Override
                public void onAnimationEnd(View view) {
                    newViewAnimation.setListener(null);
                    ViewCompat.setAlpha(newView, 1);
                    ViewCompat.setTranslationX(newView, 0);
                    ViewCompat.setTranslationY(newView, 0);
                    dispatchChangeFinished(changeInfo.newHolder, false);
                    mChangeAnimations.remove(changeInfo.newHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
    }


}
