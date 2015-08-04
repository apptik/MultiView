package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class Anims {

    private Anims() {
        throw new IllegalStateException("no instances");
    }

    public static ViewPropertyAnimatorCompat defaultRemoveAnim(View view) {
        return ViewCompat.animate(view).alpha(0);
    }

    public static  ViewPropertyAnimatorCompat defaultAddAnim(View view) {
        return ViewCompat.animate(view).alpha(1);
    }

    public static ViewPropertyAnimatorCompat defaultMoveAnim(View view, int dX, int dY) {
        ViewPropertyAnimatorCompat res = ViewCompat.animate(view);

        if (dX != 0) {
            ViewCompat.setTranslationX(view, 0);
        }
        if (dY != 0) {
            ViewCompat.setTranslationY(view, 0);
        }
        return res;
    }

    public static ViewPropertyAnimatorCompat defaultChangeOldViewAnim(View view, final BaseItemAnimator.ChangeInfo changeInfo) {
        ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view);
        oldViewAnim.translationX(changeInfo.toX - changeInfo.fromX);
        oldViewAnim.translationY(changeInfo.toY - changeInfo.fromY);
        oldViewAnim.alpha(0);
        return oldViewAnim;
    }

    public static ViewPropertyAnimatorCompat defaultChangeNewViewAnim(View view, final BaseItemAnimator.ChangeInfo changeInfo) {
        ViewPropertyAnimatorCompat newViewAnim = ViewCompat.animate(view);
        newViewAnim.translationX(0).translationY(0).alpha(1);
        return newViewAnim;
    }

    public static ViewPropertyAnimatorCompat slideInRight(final View view) {
        return  ViewCompat.animate(view).withStartAction(new Runnable() {
            @Override
            public void run() {
                ViewCompat.setTranslationX(view, view.getRootView().getWidth());
            }
        }).translationX(0);
    }

    public static ViewPropertyAnimatorCompat slideOutRight(final View view) {
        return  ViewCompat.animate(view).translationX(view.getRootView().getWidth());
    }

    public static ViewPropertyAnimatorCompat slideInLeft(final View view) {
        return  ViewCompat.animate(view).withStartAction(new Runnable() {
            @Override
            public void run() {
                ViewCompat.setTranslationX(view, -view.getRootView().getWidth());
            }
        }).translationX(0);
    }

    public static ViewPropertyAnimatorCompat slideOutLeft(final View view) {
        return  ViewCompat.animate(view).translationX(-view.getRootView().getWidth());
    }

    public static ViewPropertyAnimatorCompat slideInTop(final View view) {
        return  ViewCompat.animate(view).withStartAction(new Runnable() {
            @Override
            public void run() {
                ViewCompat.setTranslationY(view, view.getRootView().getHeight());
            }
        }).translationY(0);
    }

    public static ViewPropertyAnimatorCompat slideOutTop(final View view) {
        return  ViewCompat.animate(view).translationY(view.getRootView().getHeight());
    }

    public static ViewPropertyAnimatorCompat slideInBottom(final View view) {
        return  ViewCompat.animate(view).withStartAction(new Runnable() {
            @Override
            public void run() {
                ViewCompat.setTranslationY(view, -view.getRootView().getHeight());
            }
        }).translationY(0);
    }

    public static ViewPropertyAnimatorCompat slideOutBottom(final View view) {
        return  ViewCompat.animate(view).translationY(-view.getRootView().getHeight());
    }


    /////

    public static ViewPropertyAnimatorCompat flipDownIn(final View view) {
        return ViewCompat.animate(view).withStartAction(
                new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setTranslationX(view, - (view.getMeasuredWidth() / 2));
                        ViewCompat.setRotationY(view, -90);
                    }
                })
                .rotationY(0)
                .translationX(0)
                .setInterpolator(new BounceInterpolator());
    }

    public static ViewPropertyAnimatorCompat flipDownOut(final View view) {
        return ViewCompat.animate(view)
                .rotationY(90)
                .translationX(-(view.getMeasuredWidth() / 4))
                .scaleX(0.5F)
                .scaleY(0.5F)
                .setInterpolator(new AccelerateInterpolator());
    }

    public static ViewPropertyAnimatorCompat garageDoorClose(final View view) {
        return ViewCompat.animate(view).withStartAction(
                new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setRotationX(view, 90);
                        ViewCompat.setTranslationY(view, - (view.getMeasuredHeight() / 2));
                    }
                })
                .rotationX(0)
                .translationY(0);
    }

    public static ViewPropertyAnimatorCompat garageDoorOpen(final View view) {
        return ViewCompat.animate(view)
                .rotationX(90)
                .translationY( - (view.getMeasuredHeight() / 2));
    }

}
