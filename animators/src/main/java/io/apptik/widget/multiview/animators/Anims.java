package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

public class Anims {

    private Anims() {
        throw new IllegalStateException("no instances");
    }

    public ViewPropertyAnimatorCompat defaultRemoveAnim(View view) {
        return ViewCompat.animate(view).alpha(0);
    }
}
