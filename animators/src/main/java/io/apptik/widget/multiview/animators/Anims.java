package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

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

}
