package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

public class Providers {
    private Providers() {
        throw new IllegalStateException("no instances");
    }


    public static AnimationProvider defaultRemoveAnimProvider() {
        return new AnimationProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultRemoveAnim(viewHolder.itemView);
            }
        };
    }

    public static AnimationProvider defaultAddAnimProvider() {
        return new AnimationProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultAddAnim(viewHolder.itemView);
            }
        };
    }

    public static AnimationProvider defaultMoveAnimProvider() {
        return new AnimationProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                BaseItemAnimator.MoveInfo moveInfo = (BaseItemAnimator.MoveInfo) ags[0];
                final int deltaX = moveInfo.toX - moveInfo.fromX;
                final int deltaY = moveInfo.toY - moveInfo.fromY;
                return Anims.defaultMoveAnim(viewHolder.itemView, deltaX, deltaY);
            }
        };
    }

    public static AnimationProvider defaultChangeOldViewAnimProvider() {
        return new AnimationProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultChangeOldViewAnim(viewHolder.itemView, (BaseItemAnimator.ChangeInfo)ags[0]);
            }
        };
    }

    public static AnimationProvider defaultChangeNewViewAnimeProvider() {
        return new AnimationProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultChangeNewViewAnim(viewHolder.itemView, (BaseItemAnimator.ChangeInfo)ags[0]);
            }
        };
    }


}
