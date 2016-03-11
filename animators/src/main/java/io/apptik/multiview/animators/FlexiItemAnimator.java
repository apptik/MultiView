/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.multiview.animators;


import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;

import io.apptik.multiview.common.Log;

public class FlexiItemAnimator extends BaseItemAnimator {


    private AnimatorProvider vpaRemove;
    private AnimatorProvider vpaAdd;
    private AnimatorProvider vpaChnageOld;
    private AnimatorProvider vpaChangeNew;
    private AnimatorProvider vpaMove;

    private Interpolator ipRemove;
    private Interpolator ipAdd;
    private Interpolator ipChangeOld;
    private Interpolator ipChangeNew;
    private Interpolator ipMove;

    public FlexiItemAnimator(AnimatorProvider vpaAdd,
                             AnimatorProvider vpaChnageOld,
                             AnimatorProvider vpaChangeNew,
                             AnimatorProvider vpaMove,
                             AnimatorProvider vpaRemove) {
        this.vpaAdd = vpaAdd;
        this.vpaChnageOld = vpaChnageOld;
        this.vpaChangeNew = vpaChangeNew;
        this.vpaMove = vpaMove;
        this.vpaRemove = vpaRemove;

        Log.v("add: " + vpaAdd);
        Log.v("change old: " + vpaChnageOld);
        Log.v("change new: " + vpaChangeNew);
        Log.v("move: " + vpaMove);
        Log.v("remove: " + vpaRemove);
    }

    public Interpolator getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(Interpolator ipAdd) {
        this.ipAdd = ipAdd;
    }

    public Interpolator getIpChangeNew() {
        return ipChangeNew;
    }

    public void setIpChangeNew(Interpolator ipChangeNew) {
        this.ipChangeNew = ipChangeNew;
    }

    public Interpolator getIpChangeOld() {
        return ipChangeOld;
    }

    public void setIpChangeOld(Interpolator ipChangeOld) {
        this.ipChangeOld = ipChangeOld;
    }

    public Interpolator getIpMove() {
        return ipMove;
    }

    public void setIpMove(Interpolator ipMove) {
        this.ipMove = ipMove;
    }

    public Interpolator getIpRemove() {
        return ipRemove;
    }

    public void setIpRemove(Interpolator ipRemove) {
        this.ipRemove = ipRemove;
    }

    public FlexiItemAnimator(AnimatorSetProvider animatorSetProvider) {
        this.vpaAdd = animatorSetProvider.getAddAnimProvider();
        this.vpaChnageOld = animatorSetProvider.getChangeOldItemAnimProvider();
        this.vpaChangeNew = animatorSetProvider.getChangeNewItemAnimProvider();
        this.vpaMove = animatorSetProvider.getMoveAnimProvider();
        this.vpaRemove = animatorSetProvider.getRemoveAnimProvider();
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        if (vpaRemove == null) {
            dispatchRemoveFinished(holder);
            dispatchFinishedWhenDone();
            return;
        }
        Runnable beforeAction = vpaRemove.getBeforeAction(holder);
        if(beforeAction!=null) {
            beforeAction.run();
        }
        final ViewPropertyAnimatorCompat animation = vpaRemove.getAnim(holder);
        if(ipRemove!=null) {
            animation.setInterpolator(ipRemove);
        }
        animation.setDuration(getRemoveDuration())
                .setListener(new VoidVpaListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        Log.v("start remove anim: " + view);
                        dispatchRemoveStarting(holder);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        Log.v("cancel remove anim: " + view);
                        resetView(view);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        Log.v("end remove anim: " + view);
                        animation.setListener(null);
                        resetView(view);
                        dispatchRemoveFinished(holder);
                        mRemoveAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        if (vpaAdd == null) {
            resetView(holder.itemView);
            dispatchAddFinished(holder);
            dispatchFinishedWhenDone();
            return;
        }
        mAddAnimations.add(holder);

        Runnable beforeAction = vpaAdd.getBeforeAction(holder);
        if(beforeAction!=null) {
            beforeAction.run();
        }
        final ViewPropertyAnimatorCompat animation = vpaAdd.getAnim(holder);
        if(ipAdd!=null) {
            animation.setInterpolator(ipAdd);
        }
        animation.setDuration(getAddDuration()).
                setListener(new VoidVpaListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        Log.v("start add anim: " + view);
                        dispatchAddStarting(holder);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        Log.v("cancel add anim: " + view);
                        resetView(view);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        Log.v("end add anim: " + view);
                        animation.setListener(null);
                        resetView(view);
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

    @Override
    protected void animateMoveImpl(final RecyclerView.ViewHolder holder, final MoveInfo moveInfo) {
        if (vpaMove == null) {
            dispatchMoveFinished(holder);
            dispatchFinishedWhenDone();
            return;
        }
        final int deltaX = moveInfo.toX - moveInfo.fromX;
        final int deltaY = moveInfo.toY - moveInfo.fromY;

        mMoveAnimations.add(holder);
        Runnable beforeAction = vpaMove.getBeforeAction(holder, moveInfo);
        if(beforeAction!=null) {
            beforeAction.run();
        }
        final ViewPropertyAnimatorCompat animation = vpaMove.getAnim(holder, moveInfo);
        if(ipMove!=null) {
            animation.setInterpolator(ipMove);
        }
        animation.setDuration(getMoveDuration()).setListener(new VoidVpaListener() {
            @Override
            public void onAnimationStart(View view) {
                Log.v("start move anim: " + view);
                dispatchMoveStarting(holder);
            }

            @Override
            public void onAnimationCancel(View view) {
                Log.v("cancel move anim: " + view);
                resetView(view);
            }

            @Override
            public void onAnimationEnd(View view) {
                Log.v("end move anim: " + view);
                animation.setListener(null);
                resetView(view);
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
            Runnable beforeAction = vpaChnageOld.getBeforeAction(holder, changeInfo);
            if(beforeAction!=null) {
                beforeAction.run();
            }
            final ViewPropertyAnimatorCompat oldViewAnim = vpaChnageOld.getAnim(changeInfo.oldHolder, changeInfo).setDuration(
                    getChangeDuration());
            if(ipChangeOld!=null) {
                oldViewAnim.setInterpolator(ipChangeOld);
            }
            oldViewAnim.setListener(new VoidVpaListener() {
                @Override
                public void onAnimationStart(View view) {
                    Log.v("start change old anim: " + view);
                    dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                @Override
                public void onAnimationCancel(View view) {
                    Log.v("cancel change old anim: " + view);
                    resetView(view);
                }

                @Override
                public void onAnimationEnd(View view) {
                    Log.v("end change old anim: " + view);
                    oldViewAnim.setListener(null);
                    Log.v("end change old anim before: " + view);
                    resetView(view);
                    Log.v("end change old anim after: " + view);
                    dispatchChangeFinished(changeInfo.oldHolder, true);
                    mChangeAnimations.remove(changeInfo.oldHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        } else {
            dispatchChangeFinished(changeInfo.oldHolder, true);
            dispatchFinishedWhenDone();
        }
        if (newView != null && vpaChangeNew != null) {
            mChangeAnimations.add(changeInfo.newHolder);
            Runnable beforeAction = vpaChangeNew.getBeforeAction(holder, changeInfo);
            if(beforeAction!=null) {
                beforeAction.run();
            }
            final ViewPropertyAnimatorCompat newViewAnimation = vpaChangeNew.getAnim(changeInfo.newHolder, changeInfo);
            if(ipChangeNew!=null) {
                newViewAnimation.setInterpolator(ipChangeNew);
            }
            newViewAnimation.setDuration(getChangeDuration())
                    .setListener(new VoidVpaListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            Log.v("start change new anim: " + view);
                            dispatchChangeStarting(changeInfo.newHolder, false);
                        }

                        @Override
                        public void onAnimationCancel(View view) {
                            Log.v("cancel change new anim: " + view);
                            resetView(view);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            Log.v("end change new anim: " + view);
                            newViewAnimation.setListener(null);
                            resetView(view);
                            dispatchChangeFinished(changeInfo.newHolder, false);
                            mChangeAnimations.remove(changeInfo.newHolder);
                            dispatchFinishedWhenDone();
                        }
                    }).start();
        } else {
//            dispatchChangeFinished(changeInfo.newHolder, false);
            dispatchFinishedWhenDone();
        }
    }



}
