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

package io.apptik.widget.multiview.animators;


import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

public class Providers {
    private Providers() {
        throw new IllegalStateException("no instances");
    }


    public static AnimatorProvider defaultRemoveAnimProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultRemoveAnim(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider defaultAddAnimProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultAddAnim(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider defaultMoveAnimProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.defaultMoveAnim(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                BaseItemAnimator.MoveInfo moveInfo = (BaseItemAnimator.MoveInfo) args[0];
                final int deltaX = moveInfo.toX - moveInfo.fromX;
                final int deltaY = moveInfo.toY - moveInfo.fromY;
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setTranslationX(viewHolder.itemView, -deltaX);
                        ViewCompat.setTranslationY(viewHolder.itemView, -deltaY);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider defaultChangeOldViewAnimProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultChangeOldViewAnim(viewHolder.itemView, (BaseItemAnimator.ChangeInfo) ags[0]);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider defaultChangeNewViewAnimProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... ags) {
                return Anims.defaultChangeNewViewAnim(viewHolder.itemView, (BaseItemAnimator.ChangeInfo) ags[0]);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }


    public static AnimatorProvider garageDoorAddProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.garageDoorClose(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setRotationX(viewHolder.itemView, 90);
                        ViewCompat.setTranslationY(viewHolder.itemView, -(viewHolder.itemView.getMeasuredHeight() / 2));
                        viewHolder.itemView.invalidate();
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider garageDoorRemoveProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.garageDoorOpen(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider slideInRightProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideInHorisontal(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setTranslationX(viewHolder.itemView,
                                viewHolder.itemView.getRootView().getWidth());
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };

    }

    public static AnimatorProvider slideOutRightProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideOutRight(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider slideInLeftProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideInHorisontal(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setTranslationX(viewHolder.itemView,
                                -viewHolder.itemView.getRootView().getWidth());
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider slideOutLeftProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideOutLeft(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }


    public static AnimatorProvider slideInTopProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideInVertical(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setTranslationY(viewHolder.itemView,
                                viewHolder.itemView.getRootView().getHeight());
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };

    }

    public static AnimatorProvider slideOutTopProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideOutTop(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider slideInBottomProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideInVertical(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setTranslationY(viewHolder.itemView,
                                -viewHolder.itemView.getRootView().getHeight());
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider slideOutBottomProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.slideOutBottom(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomInEnterRightProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoom2Normal(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth());
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight()/2);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight()/2);
                        ViewCompat.setScaleX(viewHolder.itemView, 0);
                        ViewCompat.setScaleY(viewHolder.itemView, 0);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };

    }

    public static AnimatorProvider zoomOutExitRightProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomOut(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth());
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight()/2);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight() / 2);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomInExitRightProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomIn(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth()*-.1f);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight()/2);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight() / 2);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomInEnterLeftProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoom2Normal(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setPivotX(viewHolder.itemView, 0);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight()/2);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight() / 2);
                        ViewCompat.setScaleX(viewHolder.itemView, 0);
                        ViewCompat.setScaleY(viewHolder.itemView, 0);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomOutExitLeftProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomOut(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, 0);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight()/2);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight() / 2);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }


    public static AnimatorProvider zoomInExitLeftProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomIn(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth()*1.1f);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight()/2);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight() / 2);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }


    public static AnimatorProvider zoomInEnterTopProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoom2Normal(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth()/2);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, 0);
                        viewHolder.itemView.setPivotY(0);
                        ViewCompat.setScaleX(viewHolder.itemView, 0);
                        ViewCompat.setScaleY(viewHolder.itemView, 0);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };

    }

    public static AnimatorProvider zoomOutExitTopProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomOut(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth() / 2);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, 0);
                        viewHolder.itemView.setPivotY(0);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomInExitTopProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomIn(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth() / 2);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, 0);
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight()*1.1f);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomInEnterBottomProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoom2Normal(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setAlpha(viewHolder.itemView, 1);
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth() / 2);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight());
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight());
                        ViewCompat.setScaleX(viewHolder.itemView, 0);
                        ViewCompat.setScaleY(viewHolder.itemView, 0);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

    public static AnimatorProvider zoomOutExitBottomProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomOut(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth() / 2);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight());
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight());
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }


    public static AnimatorProvider zoomInExitBottomProvider() {
        return new AnimatorProvider() {
            @Override
            public ViewPropertyAnimatorCompat getAnim(RecyclerView.ViewHolder viewHolder, Object... args) {
                return Anims.zoomIn(viewHolder.itemView);
            }

            @Override
            public Runnable getBeforeAction(final RecyclerView.ViewHolder viewHolder, Object... args) {
                return new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth()/2);
                        //TODO https://code.google.com/p/android/issues/detail?id=80863
                        //ViewCompat.setPivotY(holder.itemView, holder.itemView.getHeight());
                        viewHolder.itemView.setPivotY(viewHolder.itemView.getHeight()*-.1f);
                    }
                };
            }

            @Override
            public Runnable getAfterAction(RecyclerView.ViewHolder viewHolder, Object... args) {
                return null;
            }
        };
    }

}
