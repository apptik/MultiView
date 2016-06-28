package io.apptik.multiview.scalablerecyclerview;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import io.apptik.multiview.common.Log;

public abstract class BaseGridScaler implements GridScaler {

    private final RecyclerView mRecyclerView;
    private final InteractionListener mInteractionListener;
    private int minSpan = 2;
    private int maxSpan = 5;


    public BaseGridScaler(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mInteractionListener = new InteractionListener(recyclerView);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mInteractionListener.onTouchEvent(event);
                return mInteractionListener.scaleGestureDetector.isInProgress();
            }
        });
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int getMaxSpan() {
        return maxSpan;
    }

    public void setMaxSpan(int maxSpan) {
        this.maxSpan = maxSpan;
    }

    public int getMinSpan() {
        return minSpan;
    }

    public void setMinSpan(int minSpan) {
        this.minSpan = minSpan;
    }

    @Override
    public int getInitialSpan() {
        return 0;
    }

    private class InteractionListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

        private Context context;
        private RecyclerView recyclerView;
        private GestureDetectorCompat gestureDetector;
        private ScaleGestureDetector scaleGestureDetector;
        private float currScale = 1f;
        //initial span count when scale started
        volatile int initSpanCount = 0;
        volatile View currentView;
        //used to normalise the raw factor when scaling in not allowed direction
        volatile private float factorOffset;

        public InteractionListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            this.context = this.recyclerView.getContext();
            init();
        }


        private void init() {
            gestureDetector = new GestureDetectorCompat(context, this);
            gestureDetector.setOnDoubleTapListener(this);
            scaleGestureDetector = new ScaleGestureDetector(context, this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                scaleGestureDetector.setQuickScaleEnabled(false);
            }
        }

        public boolean onTouchEvent(MotionEvent ev) {
            scaleGestureDetector.onTouchEvent(ev);
            //if were in scale ignore the rest
            if (scaleGestureDetector.isInProgress()) {
                return true;
            } else {
                //return false;
                return gestureDetector.onTouchEvent(ev);
            }

        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("onSingleTapConfirmed: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("onDoubleTap: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()) + " v: " + currentView);
            if (scaleGestureDetector.isInProgress()) return false;
            //toggle min/max col span for grid mode

            View tmpView = recyclerView.findChildViewUnder(e.getX(), e.getY());

            if (((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount() >
                    minSpan) {
                scale(1f, minSpan, e);
            } else {
                scale(1f, maxSpan, e);
            }
            if (tmpView != null) {
                recyclerView.scrollToPosition(recyclerView.getChildAdapterPosition(tmpView));
            }
            recyclerView.requestLayout();


            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("onDoubleTapEvent: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return false;
            //TODO do we ?
            //not in use now but we have to return true
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("onDown: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()));
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("onShowPress: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()));
            //just ignore
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("onSingleTapUp: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("onScroll: " + (e1 == null ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + (e2 == null ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + distanceX + "/" + distanceY);
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("onFling: " + (e1 == null ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + (e2 == null ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + velocityX + "/" + velocityY);
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("onLongPress: " + (e == null ? "e==null" : e.getX() + "/" + e.getY()));
            //maybe menu
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("onScale: " + detector.getFocusX() + "/" + detector.getFocusY()
                    + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor()
                    + "/" + factorOffset
                    + " : " + detector.getEventTime());

            float currFactor = detector.getScaleFactor() + factorOffset;

            int newSpanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
            if (
                    (initSpanCount == newSpanCount) &&
                            ((currFactor > 1 && newSpanCount == minSpan)
                                    || (currFactor < 1 && currScale <= 1 && newSpanCount == maxSpan))
                    ) {
                factorOffset = 1 - detector.getScaleFactor();
                return false;
            }

            float newScale = currFactor;

            if (currFactor > 1.05f) {
                //zoomin
                //check if we did zoom out before
                if (newSpanCount > initSpanCount) {
                    newSpanCount = initSpanCount;
                }
                Log.d("onScale: zoomin " + newSpanCount);
                if (initSpanCount == 1) {
                    //change to single LM here
                    //setSingleMode();
                    //newScale = 1;
                } else {
                    newScale = Math.min(currFactor, (float) (initSpanCount) / (float) (initSpanCount - 1));
                }

            } else if (currFactor < 0.95) {
                //zoomout
                newSpanCount = initSpanCount + 1;
                Log.d("handleOnScaleGrid: zoomout " + newSpanCount);
                // newScale = Math.max(currFactor,(float)initSpanCount/(float)(initSpanCount+1));
                newScale = 1f + currFactor - (float) initSpanCount / (float) newSpanCount;
                newScale = Math.max(1f, newScale);
            } else {
                //in between dont change span count
                //check if we were zoomed out before so we have overflowing items
                if (newSpanCount > initSpanCount) {
                    newScale = 1f + currFactor - (float) initSpanCount / (float) newSpanCount;
                    newScale = Math.max(1f, newScale);
                } else {
                    if (initSpanCount == 1) {
                        //chnage to single LM here
                        //setSingleMode();
                        //newScale = 1;
                    } else {
                        newScale = Math.min(currFactor, (float) (initSpanCount) / (float) (initSpanCount - 1));
                    }
                }
            }

            currScale = newScale;

            scale(currScale, newSpanCount, null);

            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d("onScaleBegin: " + detector.getFocusX() + "/" + detector.getFocusY()
                    + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor()
                    + " : " + detector.getEventTime());
            currentView = recyclerView.findChildViewUnder(detector.getFocusX(), detector.getFocusY());
            initSpanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
            factorOffset = 0f;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d("onScaleEnd: " + detector.getFocusX() + "/" + detector.getFocusY()
                    + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor()
                    + "/" + factorOffset
                    + " : " + detector.getEventTime());

            float currFactor = detector.getScaleFactor() + factorOffset;

            if (currFactor > 1f + (1f / (float) (initSpanCount - 1)) / 2f) {
                if (initSpanCount == minSpan) {
                    //do nothing we reached our max span
                } else {
                    scale(1f, initSpanCount - 1, null);
                }
            } else if (currFactor < 1f - (1f / (float) (initSpanCount + 1)) / 2f) {
                scale(1f, initSpanCount + 1, null);
            } else {
                if (((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount() != initSpanCount) {
                    scale(1f, initSpanCount, null);
                }
            }


            if (initSpanCount != ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount()) {
                //recyclerView.getLayoutManager().scrollToPosition(recyclerView.getChildAdapterPosition(currentView));
            }
            currScale = 1f;
            initSpanCount = 0;
//            recyclerView.getLayoutManager().requestLayout();
//            recyclerView.invalidate();

            currentView = null;
        }

    }


}
