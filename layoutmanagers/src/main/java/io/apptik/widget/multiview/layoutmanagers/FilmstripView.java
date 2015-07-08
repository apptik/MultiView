package io.apptik.widget.multiview.layoutmanagers;

 //        import com.android.camera.CameraActivity;
//        import com.android.camera.data.LocalData.ActionCallback;
//        import com.android.camera.debug.Log;
//        import com.android.camera.filmstrip.DataAdapter;
//        import com.android.camera.filmstrip.FilmstripController;
//        import com.android.camera.filmstrip.ImageData;
//        import com.android.camera.ui.FilmstripGestureRecognizer;
//        import com.android.camera.ui.ZoomView;
//        import com.android.camera.util.ApiHelper;
//        import com.android.camera.util.CameraUtil;
//        import com.android.camera2.R;
public class FilmstripView
    //    extends ViewGroup
{
    /**
     * An action callback to be used for actions on the local media data items.
     */
//    public static class ActionCallbackImpl implements ActionCallback {
//        private final WeakReference<Activity> mActivity;
//        /**
//         * The given activity is used to start intents. It is wrapped in a weak
//         * reference to prevent leaks.
//         */
//        public ActionCallbackImpl(Activity activity) {
//            mActivity = new WeakReference<Activity>(activity);
//        }
//        /**
//         * Fires an intent to play the video with the given URI and title.
//         */
//        @Override
//        public void playVideo(Uri uri, String title) {
//            Activity activity = mActivity.get();
//            if (activity != null) {
//                CameraUtil.playVideo(activity, uri, title);
//            }
//        }
//    }
//    private static final Log.Tag TAG = new Log.Tag("FilmstripView");
//    private static final int BUFFER_SIZE = 5;
//    private static final int GEOMETRY_ADJUST_TIME_MS = 400;
//    private static final int SNAP_IN_CENTER_TIME_MS = 600;
//    private static final float FLING_COASTING_DURATION_S = 0.05f;
//    private static final int ZOOM_ANIMATION_DURATION_MS = 200;
//    private static final int CAMERA_PREVIEW_SWIPE_THRESHOLD = 300;
//    private static final float FILM_STRIP_SCALE = 0.7f;
//    private static final float FULL_SCREEN_SCALE = 1f;
//    // The min velocity at which the user must have moved their finger in
//    // pixels per millisecond to count a vertical gesture as a promote/demote
//    // at short vertical distances.
//    private static final float PROMOTE_VELOCITY = 3.5f;
//    // The min distance relative to this view's height the user must have
//    // moved their finger to count a vertical gesture as a promote/demote if
//    // they moved their finger at least at PROMOTE_VELOCITY.
//    private static final float VELOCITY_PROMOTE_HEIGHT_RATIO = 1/10f;
//    // The min distance relative to this view's height the user must have
//    // moved their finger to count a vertical gesture as a promote/demote if
//    // they moved their finger at less than PROMOTE_VELOCITY.
//    private static final float PROMOTE_HEIGHT_RATIO = 1/2f;
//    private static final float TOLERANCE = 0.1f;
//    // Only check for intercepting touch events within first 500ms
//    private static final int SWIPE_TIME_OUT = 500;
//    private static final int DECELERATION_FACTOR = 4;
//    private CameraActivity mActivity;
//    private ActionCallback mActionCallback;
//    private FilmstripGestureRecognizer mGestureRecognizer;
//    private FilmstripGestureRecognizer.Listener mGestureListener;
//    private DataAdapter mDataAdapter;
//    private int mViewGapInPixel;
//    private final Rect mDrawArea = new Rect();
//    private final int mCurrentItem = (BUFFER_SIZE - 1) / 2;
//    private float mScale;
//    private MyController mController;
//    private int mCenterX = -1;
//    private final ViewItem[] mViewItem = new ViewItem[BUFFER_SIZE];
//    private FilmstripController.FilmstripListener mListener;
//    private ZoomView mZoomView = null;
//    private MotionEvent mDown;
//    private boolean mCheckToIntercept = true;
//    private int mSlop;
//    private TimeInterpolator mViewAnimInterpolator;
//    // This is true if and only if the user is scrolling,
//    private boolean mIsUserScrolling;
//    private int mDataIdOnUserScrolling;
//    private float mOverScaleFactor = 1f;
//    private boolean mFullScreenUIHidden = false;
//    private final SparseArray<Queue<View>> recycledViews = new SparseArray<Queue<View>>();
//    /**
//     * A helper class to tract and calculate the view coordination.
//     */
//    private class ViewItem {
//        private int mDataId;
//        /** The position of the left of the view in the whole filmstrip. */
//        private int mLeftPosition;
//        private final View mView;
//        private final ImageData mData;
//        private final RectF mViewArea;
//        private boolean mMaximumBitmapRequested;
//        private ValueAnimator mTranslationXAnimator;
//        private ValueAnimator mTranslationYAnimator;
//        private ValueAnimator mAlphaAnimator;
//        /**
//         * Constructor.
//         *
//         * @param id The id of the data from
//         *            {@link com.android.camera.filmstrip.DataAdapter}.
//         * @param v The {@code View} representing the data.
//         */
//        public ViewItem(int id, View v, ImageData data) {
//            v.setPivotX(0f);
//            v.setPivotY(0f);
//            mDataId = id;
//            mData = data;
//            mView = v;
//            mMaximumBitmapRequested = false;
//            mLeftPosition = -1;
//            mViewArea = new RectF();
//        }
//        public boolean isMaximumBitmapRequested() {
//            return mMaximumBitmapRequested;
//        }
//        public void setMaximumBitmapRequested() {
//            mMaximumBitmapRequested = true;
//        }
//        /**
//         * Returns the data id from
//         * {@link com.android.camera.filmstrip.DataAdapter}.
//         */
//        public int getId() {
//            return mDataId;
//        }
//        /**
//         * Sets the data id from
//         * {@link com.android.camera.filmstrip.DataAdapter}.
//         */
//        public void setId(int id) {
//            mDataId = id;
//        }
//        /** Sets the left position of the view in the whole filmstrip. */
//        public void setLeftPosition(int pos) {
//            mLeftPosition = pos;
//        }
//        /** Returns the left position of the view in the whole filmstrip. */
//        public int getLeftPosition() {
//            return mLeftPosition;
//        }
//        /** Returns the translation of Y regarding the view scale. */
//        public float getTranslationY() {
//            return mView.getTranslationY() / mScale;
//        }
//        /** Returns the translation of X regarding the view scale. */
//        public float getTranslationX() {
//            return mView.getTranslationX() / mScale;
//        }
//        /** Sets the translation of Y regarding the view scale. */
//        public void setTranslationY(float transY) {
//            mView.setTranslationY(transY * mScale);
//        }
//        /** Sets the translation of X regarding the view scale. */
//        public void setTranslationX(float transX) {
//            mView.setTranslationX(transX * mScale);
//        }
//        /** Forwarding of {@link android.view.View#setAlpha(float)}. */
//        public void setAlpha(float alpha) {
//            mView.setAlpha(alpha);
//        }
//        /** Forwarding of {@link android.view.View#getAlpha()}. */
//        public float getAlpha() {
//            return mView.getAlpha();
//        }
//        /** Forwarding of {@link android.view.View#getMeasuredWidth()}. */
//        public int getMeasuredWidth() {
//            return mView.getMeasuredWidth();
//        }
//        /**
//         * Animates the X translation of the view. Note: the animated value is
//         * not set directly by {@link android.view.View#setTranslationX(float)}
//         * because the value might be changed during in {@code onLayout()}.
//         * The animated value of X translation is specially handled in {@code
//         * layoutIn()}.
//         *
//         * @param targetX The final value.
//         * @param duration_ms The duration of the animation.
//         * @param interpolator Time interpolator.
//         */
//        public void animateTranslationX(
//                float targetX, long duration_ms, TimeInterpolator interpolator) {
//            if (mTranslationXAnimator == null) {
//                mTranslationXAnimator = new ValueAnimator();
//                mTranslationXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        // We invalidate the filmstrip view instead of setting the
//                        // translation X because the translation X of the view is
//                        // touched in onLayout(). See the documentation of
//                        // animateTranslationX().
//                        invalidate();
//                    }
//                });
//            }
//            runAnimation(mTranslationXAnimator, getTranslationX(), targetX, duration_ms,
//                    interpolator);
//        }
//        /**
//         * Animates the Y translation of the view.
//         *
//         * @param targetY The final value.
//         * @param duration_ms The duration of the animation.
//         * @param interpolator Time interpolator.
//         */
//        public void animateTranslationY(
//                float targetY, long duration_ms, TimeInterpolator interpolator) {
//            if (mTranslationYAnimator == null) {
//                mTranslationYAnimator = new ValueAnimator();
//                mTranslationYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        setTranslationY((Float) valueAnimator.getAnimatedValue());
//                    }
//                });
//            }
//            runAnimation(mTranslationYAnimator, getTranslationY(), targetY, duration_ms,
//                    interpolator);
//        }
//        /**
//         * Animates the alpha value of the view.
//         *
//         * @param targetAlpha The final value.
//         * @param duration_ms The duration of the animation.
//         * @param interpolator Time interpolator.
//         */
//        public void animateAlpha(float targetAlpha, long duration_ms,
//                                 TimeInterpolator interpolator) {
//            if (mAlphaAnimator == null) {
//                mAlphaAnimator = new ValueAnimator();
//                mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        ViewItem.this.setAlpha((Float) valueAnimator.getAnimatedValue());
//                    }
//                });
//            }
//            runAnimation(mAlphaAnimator, getAlpha(), targetAlpha, duration_ms, interpolator);
//        }
//        private void runAnimation(final ValueAnimator animator, final float startValue,
//                                  final float targetValue, final long duration_ms,
//                                  final TimeInterpolator interpolator) {
//            if (startValue == targetValue) {
//                return;
//            }
//            animator.setInterpolator(interpolator);
//            animator.setDuration(duration_ms);
//            animator.setFloatValues(startValue, targetValue);
//            animator.start();
//        }
//        /** Adjusts the translation of X regarding the view scale. */
//        public void translateXScaledBy(float transX) {
//            setTranslationX(getTranslationX() + transX * mScale);
//        }
//        /**
//         * Forwarding of {@link android.view.View#getHitRect(android.graphics.Rect)}.
//         */
//        public void getHitRect(Rect rect) {
//            mView.getHitRect(rect);
//        }
//        public int getCenterX() {
//            return mLeftPosition + mView.getMeasuredWidth() / 2;
//        }
//        /** Forwarding of {@link android.view.View#getVisibility()}. */
//        public int getVisibility() {
//            return mView.getVisibility();
//        }
//        /** Forwarding of {@link android.view.View#setVisibility(int)}. */
//        public void setVisibility(int visibility) {
//            mView.setVisibility(visibility);
//        }
//        /**
//         * Notifies the {@link com.android.camera.filmstrip.DataAdapter} to
//         * resize the view.
//         */
//        public void resizeView(Context context, int w, int h) {
//            mDataAdapter.resizeView(context, mDataId, mView, w, h);
//        }
//        /**
//         * Adds the view of the data to the view hierarchy if necessary.
//         */
//        public void addViewToHierarchy() {
//            if (indexOfChild(mView) < 0) {
//                mData.prepare();
//                addView(mView);
//            }
//            setVisibility(View.VISIBLE);
//            setAlpha(1f);
//            setTranslationX(0);
//            setTranslationY(0);
//        }
//        /**
//         * Removes from the hierarchy. Keeps the view in the view hierarchy if
//         * view type is {@code VIEW_TYPE_STICKY} and set to invisible instead.
//         *
//         * @param force {@code true} to remove the view from the hierarchy
//         *                          regardless of the view type.
//         */
//        public void removeViewFromHierarchy(boolean force) {
//            if (force || mData.getViewType() != ImageData.VIEW_TYPE_STICKY) {
//                removeView(mView);
//                mData.recycle(mView);
//                recycleView(mView, mDataId);
//            } else {
//                setVisibility(View.INVISIBLE);
//            }
//        }
//        /**
//         * Brings the view to front by
//         * {@link #bringChildToFront(android.view.View)}
//         */
//        public void bringViewToFront() {
//            bringChildToFront(mView);
//        }
//        /**
//         * The visual x position of this view, in pixels.
//         */
//        public float getX() {
//            return mView.getX();
//        }
//        /**
//         * The visual y position of this view, in pixels.
//         */
//        public float getY() {
//            return mView.getY();
//        }
//        /**
//         * Forwarding of {@link android.view.View#measure(int, int)}.
//         */
//        public void measure(int widthSpec, int heightSpec) {
//            mView.measure(widthSpec, heightSpec);
//        }
//        private void layoutAt(int left, int top) {
//            mView.layout(left, top, left + mView.getMeasuredWidth(),
//                    top + mView.getMeasuredHeight());
//        }
//        /**
//         * The bounding rect of the view.
//         */
//        public RectF getViewRect() {
//            RectF r = new RectF();
//            r.left = mView.getX();
//            r.top = mView.getY();
//            r.right = r.left + mView.getWidth() * mView.getScaleX();
//            r.bottom = r.top + mView.getHeight() * mView.getScaleY();
//            return r;
//        }
//        private View getView() {
//            return mView;
//        }
//        /**
//         * Layouts the view in the area assuming the center of the area is at a
//         * specific point of the whole filmstrip.
//         *
//         * @param drawArea The area when filmstrip will show in.
//         * @param refCenter The absolute X coordination in the whole filmstrip
//         *            of the center of {@code drawArea}.
//         * @param scale The scale of the view on the filmstrip.
//         */
//        public void layoutWithTranslationX(Rect drawArea, int refCenter, float scale) {
//            final float translationX =
//                    ((mTranslationXAnimator != null && mTranslationXAnimator.isRunning()) ?
//                            (Float) mTranslationXAnimator.getAnimatedValue() : 0);
//            int left =
//                    (int) (drawArea.centerX() + (mLeftPosition - refCenter + translationX) * scale);
//            int top = (int) (drawArea.centerY() - (mView.getMeasuredHeight() / 2) * scale);
//            layoutAt(left, top);
//            mView.setScaleX(scale);
//            mView.setScaleY(scale);
//            // update mViewArea for touch detection.
//            int l = mView.getLeft();
//            int t = mView.getTop();
//            mViewArea.set(l, t,
//                    l + mView.getMeasuredWidth() * scale,
//                    t + mView.getMeasuredHeight() * scale);
//        }
//        /** Returns true if the point is in the view. */
//        public boolean areaContains(float x, float y) {
//            return mViewArea.contains(x, y);
//        }
//        /**
//         * Return the width of the view.
//         */
//        public int getWidth() {
//            return mView.getWidth();
//        }
//        /**
//         * Returns the position of the left edge of the view area content is drawn in.
//         */
//        public int getDrawAreaLeft() {
//            return Math.round(mViewArea.left);
//        }
//        public void copyAttributes(ViewItem item) {
//            setLeftPosition(item.getLeftPosition());
//            // X
//            setTranslationX(item.getTranslationX());
//            if (item.mTranslationXAnimator != null) {
//                mTranslationXAnimator = item.mTranslationXAnimator;
//                mTranslationXAnimator.removeAllUpdateListeners();
//                mTranslationXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        // We invalidate the filmstrip view instead of setting the
//                        // translation X because the translation X of the view is
//                        // touched in onLayout(). See the documentation of
//                        // animateTranslationX().
//                        invalidate();
//                    }
//                });
//            }
//            // Y
//            setTranslationY(item.getTranslationY());
//            if (item.mTranslationYAnimator != null) {
//                mTranslationYAnimator = item.mTranslationYAnimator;
//                mTranslationYAnimator.removeAllUpdateListeners();
//                mTranslationYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        setTranslationY((Float) valueAnimator.getAnimatedValue());
//                    }
//                });
//            }
//            // Alpha
//            setAlpha(item.getAlpha());
//            if (item.mAlphaAnimator != null) {
//                mAlphaAnimator = item.mAlphaAnimator;
//                mAlphaAnimator.removeAllUpdateListeners();
//                mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        ViewItem.this.setAlpha((Float) valueAnimator.getAnimatedValue());
//                    }
//                });
//            }
//        }
//        /**
//         * Apply a scale factor (i.e. {@code postScale}) on top of current scale at
//         * pivot point ({@code focusX}, {@code focusY}). Visually it should be the
//         * same as post concatenating current view's matrix with specified scale.
//         */
//        void postScale(float focusX, float focusY, float postScale, int viewportWidth,
//                       int viewportHeight) {
//            float transX = mView.getTranslationX();
//            float transY = mView.getTranslationY();
//            // Pivot point is top left of the view, so we need to translate
//            // to scale around focus point
//            transX -= (focusX - getX()) * (postScale - 1f);
//            transY -= (focusY - getY()) * (postScale - 1f);
//            float scaleX = mView.getScaleX() * postScale;
//            float scaleY = mView.getScaleY() * postScale;
//            updateTransform(transX, transY, scaleX, scaleY, viewportWidth,
//                    viewportHeight);
//        }
//        void updateTransform(float transX, float transY, float scaleX, float scaleY,
//                             int viewportWidth, int viewportHeight) {
//            float left = transX + mView.getLeft();
//            float top = transY + mView.getTop();
//            RectF r = ZoomView.adjustToFitInBounds(new RectF(left, top,
//                            left + mView.getWidth() * scaleX,
//                            top + mView.getHeight() * scaleY),
//                    viewportWidth, viewportHeight);
//            mView.setScaleX(scaleX);
//            mView.setScaleY(scaleY);
//            transX = r.left - mView.getLeft();
//            transY = r.top - mView.getTop();
//            mView.setTranslationX(transX);
//            mView.setTranslationY(transY);
//        }
//        void resetTransform() {
//            mView.setScaleX(FULL_SCREEN_SCALE);
//            mView.setScaleY(FULL_SCREEN_SCALE);
//            mView.setTranslationX(0f);
//            mView.setTranslationY(0f);
//        }
//        @Override
//        public String toString() {
//            return "DataID = " + mDataId + "\n\t left = " + mLeftPosition
//                    + "\n\t viewArea = " + mViewArea
//                    + "\n\t centerX = " + getCenterX()
//                    + "\n\t view MeasuredSize = "
//                    + mView.getMeasuredWidth() + ',' + mView.getMeasuredHeight()
//                    + "\n\t view Size = " + mView.getWidth() + ',' + mView.getHeight()
//                    + "\n\t view scale = " + mView.getScaleX();
//        }
//    }
//    /** Constructor. */
//    public FilmstripView(Context context) {
//        super(context);
//        init((CameraActivity) context);
//    }
//    /** Constructor. */
//    public FilmstripView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init((CameraActivity) context);
//    }
//    /** Constructor. */
//    public FilmstripView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init((CameraActivity) context);
//    }
//    private void init(CameraActivity cameraActivity) {
//        setWillNotDraw(false);
//        mActivity = cameraActivity;
//        mActionCallback = new ActionCallbackImpl(mActivity);
//        mScale = 1.0f;
//        mDataIdOnUserScrolling = 0;
//        mController = new MyController(cameraActivity);
//        mViewAnimInterpolator = new DecelerateInterpolator();
//        mZoomView = new ZoomView(cameraActivity);
//        mZoomView.setVisibility(GONE);
//        addView(mZoomView);
//        mGestureListener = new MyGestureReceiver();
//        mGestureRecognizer =
//                new FilmstripGestureRecognizer(cameraActivity, mGestureListener);
//        mSlop = (int) getContext().getResources().getDimension(R.dimen.pie_touch_slop);
//        DisplayMetrics metrics = new DisplayMetrics();
//        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        // Allow over scaling because on high density screens, pixels are too
//        // tiny to clearly see the details at 1:1 zoom. We should not scale
//        // beyond what 1:1 would look like on a medium density screen, as
//        // scaling beyond that would only yield blur.
//        mOverScaleFactor = (float) metrics.densityDpi / (float) DisplayMetrics.DENSITY_HIGH;
//        if (mOverScaleFactor < 1f) {
//            mOverScaleFactor = 1f;
//        }
//        setAccessibilityDelegate(new AccessibilityDelegate() {
//            @Override
//            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
//                super.onInitializeAccessibilityNodeInfo(host, info);
//                info.setClassName(FilmstripView.class.getName());
//                info.setScrollable(true);
//                info.addAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                info.addAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
//            }
//            @Override
//            public boolean performAccessibilityAction(View host, int action, Bundle args) {
//                if (!mController.isScrolling()) {
//                    switch (action) {
//                        case AccessibilityNodeInfo.ACTION_SCROLL_FORWARD: {
//                            mController.goToNextItem();
//                            return true;
//                        }
//                        case AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD: {
//                            boolean wentToPrevious = mController.goToPreviousItem();
//                            if (!wentToPrevious) {
//                                // at beginning of filmstrip, hide and go back to preview
//                                mActivity.getCameraAppUI().hideFilmstrip();
//                            }
//                            return true;
//                        }
//                        case AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS: {
//                            // Prevent the view group itself from being selected.
//                            // Instead, select the item in the center
//                            final ViewItem currentItem = mViewItem[mCurrentItem];
//                            currentItem.getView().performAccessibilityAction(action, args);
//                            return true;
//                        }
//                    }
//                }
//                return super.performAccessibilityAction(host, action, args);
//            }
//        });
//    }
//    private void recycleView(View view, int dataId) {
//        final int viewType = (Integer) view.getTag(R.id.mediadata_tag_viewtype);
//        if (viewType > 0) {
//            Queue<View> recycledViewsForType = recycledViews.get(viewType);
//            if (recycledViewsForType == null) {
//                recycledViewsForType = new ArrayDeque<View>();
//                recycledViews.put(viewType, recycledViewsForType);
//            }
//            recycledViewsForType.offer(view);
//        }
//    }
//    private View getRecycledView(int dataId) {
//        final int viewType = mDataAdapter.getItemViewType(dataId);
//        Queue<View> recycledViewsForType = recycledViews.get(viewType);
//        View result = null;
//        if (recycledViewsForType != null) {
//            result = recycledViewsForType.poll();
//        }
//        return result;
//    }
//    /**
//     * Returns the controller.
//     *
//     * @return The {@code Controller}.
//     */
//    public FilmstripController getController() {
//        return mController;
//    }
//    /**
//     * Returns the draw area width of the current item.
//     */
//    public int  getCurrentItemLeft() {
//        return mViewItem[mCurrentItem].getDrawAreaLeft();
//    }
//    private void setListener(FilmstripController.FilmstripListener l) {
//        mListener = l;
//    }
//    private void setViewGap(int viewGap) {
//        mViewGapInPixel = viewGap;
//    }
//    /**
//     * Called after current item or zoom level has changed.
//     */
//    public void zoomAtIndexChanged() {
//        if (mViewItem[mCurrentItem] == null) {
//            return;
//        }
//        int id = mViewItem[mCurrentItem].getId();
//        mListener.onZoomAtIndexChanged(id, mScale);
//    }
//    /**
//     * Checks if the data is at the center.
//     *
//     * @param id The id of the data to check.
//     * @return {@code True} if the data is currently at the center.
//     */
//    private boolean isDataAtCenter(int id) {
//        if (mViewItem[mCurrentItem] == null) {
//            return false;
//        }
//        if (mViewItem[mCurrentItem].getId() == id
//                && isCurrentItemCentered()) {
//            return true;
//        }
//        return false;
//    }
//    private void measureViewItem(ViewItem item, int boundWidth, int boundHeight) {
//        int id = item.getId();
//        ImageData imageData = mDataAdapter.getImageData(id);
//        if (imageData == null) {
//            Log.e(TAG, "trying to measure a null item");
//            return;
//        }
//        Point dim = CameraUtil.resizeToFill(imageData.getWidth(), imageData.getHeight(),
//                imageData.getRotation(), boundWidth, boundHeight);
//        item.measure(MeasureSpec.makeMeasureSpec(dim.x, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(dim.y, MeasureSpec.EXACTLY));
//    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int boundWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int boundHeight = MeasureSpec.getSize(heightMeasureSpec);
//        if (boundWidth == 0 || boundHeight == 0) {
//            // Either width or height is unknown, can't measure children yet.
//            return;
//        }
//        for (ViewItem item : mViewItem) {
//            if (item != null) {
//                measureViewItem(item, boundWidth, boundHeight);
//            }
//        }
//        clampCenterX();
//        // Measure zoom view
//        mZoomView.measure(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY));
//    }
//    private int findTheNearestView(int pointX) {
//        int nearest = 0;
//        // Find the first non-null ViewItem.
//        while (nearest < BUFFER_SIZE
//                && (mViewItem[nearest] == null || mViewItem[nearest].getLeftPosition() == -1)) {
//            nearest++;
//        }
//        // No existing available ViewItem
//        if (nearest == BUFFER_SIZE) {
//            return -1;
//        }
//        int min = Math.abs(pointX - mViewItem[nearest].getCenterX());
//        for (int itemID = nearest + 1; itemID < BUFFER_SIZE && mViewItem[itemID] != null; itemID++) {
//            // Not measured yet.
//            if (mViewItem[itemID].getLeftPosition() == -1) {
//                continue;
//            }
//            int c = mViewItem[itemID].getCenterX();
//            int dist = Math.abs(pointX - c);
//            if (dist < min) {
//                min = dist;
//                nearest = itemID;
//            }
//        }
//        return nearest;
//    }
//    private ViewItem buildItemFromData(int dataID) {
//        if (mActivity.isDestroyed()) {
//            // Loading item data is call from multiple AsyncTasks and the
//            // activity may be finished when buildItemFromData is called.
//            Log.d(TAG, "Activity destroyed, don't load data");
//            return null;
//        }
//        ImageData data = mDataAdapter.getImageData(dataID);
//        if (data == null) {
//            return null;
//        }
//        // Always scale by fixed filmstrip scale, since we only show items when
//        // in filmstrip. Preloading images with a different scale and bounds
//        // interferes with caching.
//        int width = Math.round(FILM_STRIP_SCALE * getWidth());
//        int height = Math.round(FILM_STRIP_SCALE * getHeight());
//        Log.v(TAG, "suggesting item bounds: " + width + "x" + height);
//        mDataAdapter.suggestViewSizeBound(width, height);
//        data.prepare();
//        View recycled = getRecycledView(dataID);
//        View v = mDataAdapter.getView(mActivity.getAndroidContext(), recycled, dataID,
//                mActionCallback);
//        if (v == null) {
//            return null;
//        }
//        ViewItem item = new ViewItem(dataID, v, data);
//        item.addViewToHierarchy();
//        return item;
//    }
//    private void checkItemAtMaxSize() {
//        ViewItem item = mViewItem[mCurrentItem];
//        if (item.isMaximumBitmapRequested()) {
//            return;
//        };
//        item.setMaximumBitmapRequested();
//        // Request full size bitmap, or max that DataAdapter will create.
//        int id = item.getId();
//        int h = mDataAdapter.getImageData(id).getHeight();
//        int w = mDataAdapter.getImageData(id).getWidth();
//        item.resizeView(mActivity, w, h);
//    }
//    private void removeItem(int itemID) {
//        if (itemID >= mViewItem.length || mViewItem[itemID] == null) {
//            return;
//        }
//        ImageData data = mDataAdapter.getImageData(mViewItem[itemID].getId());
//        if (data == null) {
//            Log.e(TAG, "trying to remove a null item");
//            return;
//        }
//        mViewItem[itemID].removeViewFromHierarchy(false);
//        mViewItem[itemID] = null;
//    }
//    /**
//     * We try to keep the one closest to the center of the screen at position
//     * mCurrentItem.
//     */
//    private void stepIfNeeded() {
//        if (!inFilmstrip() && !inFullScreen()) {
//            // The good timing to step to the next view is when everything is
//            // not in transition.
//            return;
//        }
//        final int nearest = findTheNearestView(mCenterX);
//        // no change made.
//        if (nearest == -1 || nearest == mCurrentItem) {
//            return;
//        }
//        int prevDataId = (mViewItem[mCurrentItem] == null ? -1 : mViewItem[mCurrentItem].getId());
//        final int adjust = nearest - mCurrentItem;
//        if (adjust > 0) {
//            for (int k = 0; k < adjust; k++) {
//                removeItem(k);
//            }
//            for (int k = 0; k + adjust < BUFFER_SIZE; k++) {
//                mViewItem[k] = mViewItem[k + adjust];
//            }
//            for (int k = BUFFER_SIZE - adjust; k < BUFFER_SIZE; k++) {
//                mViewItem[k] = null;
//                if (mViewItem[k - 1] != null) {
//                    mViewItem[k] = buildItemFromData(mViewItem[k - 1].getId() + 1);
//                }
//            }
//            adjustChildZOrder();
//        } else {
//            for (int k = BUFFER_SIZE - 1; k >= BUFFER_SIZE + adjust; k--) {
//                removeItem(k);
//            }
//            for (int k = BUFFER_SIZE - 1; k + adjust >= 0; k--) {
//                mViewItem[k] = mViewItem[k + adjust];
//            }
//            for (int k = -1 - adjust; k >= 0; k--) {
//                mViewItem[k] = null;
//                if (mViewItem[k + 1] != null) {
//                    mViewItem[k] = buildItemFromData(mViewItem[k + 1].getId() - 1);
//                }
//            }
//        }
//        invalidate();
//        if (mListener != null) {
//            mListener.onDataFocusChanged(prevDataId, mViewItem[mCurrentItem].getId());
//            final int firstVisible = mViewItem[mCurrentItem].getId() - 2;
//            final int visibleItemCount = firstVisible + BUFFER_SIZE;
//            final int totalItemCount = mDataAdapter.getTotalNumber();
//            mListener.onScroll(firstVisible, visibleItemCount, totalItemCount);
//        }
//        zoomAtIndexChanged();
//    }
//    /**
//     * Check the bounds of {@code mCenterX}. Always call this function after: 1.
//     * Any changes to {@code mCenterX}. 2. Any size change of the view items.
//     *
//     * @return Whether clamp happened.
//     */
//    private boolean clampCenterX() {
//        ViewItem curr = mViewItem[mCurrentItem];
//        if (curr == null) {
//            return false;
//        }
//        boolean stopScroll = false;
//        if (curr.getId() == 1 && mCenterX < curr.getCenterX() && mDataIdOnUserScrolling > 1 &&
//                mDataAdapter.getImageData(0).getViewType() == ImageData.VIEW_TYPE_STICKY &&
//                mController.isScrolling()) {
//            stopScroll = true;
//        } else {
//            if (curr.getId() == 0 && mCenterX < curr.getCenterX()) {
//                // Stop at the first ViewItem.
//                stopScroll = true;
//            }
//        }
//        if (curr.getId() == mDataAdapter.getTotalNumber() - 1
//                && mCenterX > curr.getCenterX()) {
//            // Stop at the end.
//            stopScroll = true;
//        }
//        if (stopScroll) {
//            mCenterX = curr.getCenterX();
//        }
//        return stopScroll;
//    }
//    /**
//     * Reorders the child views to be consistent with their data ID. This method
//     * should be called after adding/removing views.
//     */
//    private void adjustChildZOrder() {
//        for (int i = BUFFER_SIZE - 1; i >= 0; i--) {
//            if (mViewItem[i] == null) {
//                continue;
//            }
//            mViewItem[i].bringViewToFront();
//        }
//        // ZoomView is a special case to always be in the front. In L set to
//        // max elevation to make sure ZoomView is above other elevated views.
//        bringChildToFront(mZoomView);
//        if (ApiHelper.isLOrHigher()) {
//            setMaxElevation(mZoomView);
//        }
//    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void setMaxElevation(View v) {
//        v.setElevation(Float.MAX_VALUE);
//    }
//    /**
//     * Returns the ID of the current item, or -1 if there is no data.
//     */
//    private int getCurrentId() {
//        ViewItem current = mViewItem[mCurrentItem];
//        if (current == null) {
//            return -1;
//        }
//        return current.getId();
//    }
//    /**
//     * Keep the current item in the center. This functions does not check if the
//     * current item is null.
//     */
//    private void snapInCenter() {
//        final ViewItem currItem = mViewItem[mCurrentItem];
//        if (currItem == null) {
//            return;
//        }
//        final int currentViewCenter = currItem.getCenterX();
//        if (mController.isScrolling() || mIsUserScrolling
//                || isCurrentItemCentered()) {
//            return;
//        }
//        int snapInTime = (int) (SNAP_IN_CENTER_TIME_MS
//                * ((float) Math.abs(mCenterX - currentViewCenter))
//                / mDrawArea.width());
//        mController.scrollToPosition(currentViewCenter,
//                snapInTime, false);
//        if (isViewTypeSticky(currItem) && !mController.isScaling() && mScale != FULL_SCREEN_SCALE) {
//            // Now going to full screen camera
//            mController.goToFullScreen();
//        }
//    }
//    /**
//     * Translates the {@link ViewItem} on the left of the current one to match
//     * the full-screen layout. In full-screen, we show only one {@link ViewItem}
//     * which occupies the whole screen. The other left ones are put on the left
//     * side in full scales. Does nothing if there's no next item.
//     *
//     * @param currItem The item ID of the current one to be translated.
//     * @param drawAreaWidth The width of the current draw area.
//     * @param scaleFraction A {@code float} between 0 and 1. 0 if the current
//     *            scale is {@code FILM_STRIP_SCALE}. 1 if the current scale is
//     *            {@code FULL_SCREEN_SCALE}.
//     */
//    private void translateLeftViewItem(
//            int currItem, int drawAreaWidth, float scaleFraction) {
//        if (currItem < 0 || currItem > BUFFER_SIZE - 1) {
//            Log.e(TAG, "currItem id out of bound.");
//            return;
//        }
//        final ViewItem curr = mViewItem[currItem];
//        final ViewItem next = mViewItem[currItem + 1];
//        if (curr == null || next == null) {
//            Log.e(TAG, "Invalid view item (curr or next == null). curr = "
//                    + currItem);
//            return;
//        }
//        final int currCenterX = curr.getCenterX();
//        final int nextCenterX = next.getCenterX();
//        final int translate = (int) ((nextCenterX - drawAreaWidth
//                - currCenterX) * scaleFraction);
//        curr.layoutWithTranslationX(mDrawArea, mCenterX, mScale);
//        curr.setAlpha(1f);
//        curr.setVisibility(VISIBLE);
//        if (inFullScreen()) {
//            curr.setTranslationX(translate * (mCenterX - currCenterX) / (nextCenterX - currCenterX));
//        } else {
//            curr.setTranslationX(translate);
//        }
//    }
//    /**
//     * Fade out the {@link ViewItem} on the right of the current one in
//     * full-screen layout. Does nothing if there's no previous item.
//     *
//     * @param currItemId The ID of the item to fade.
//     */
//    private void fadeAndScaleRightViewItem(int currItemId) {
//        if (currItemId < 1 || currItemId > BUFFER_SIZE) {
//            Log.e(TAG, "currItem id out of bound.");
//            return;
//        }
//        final ViewItem currItem = mViewItem[currItemId];
//        final ViewItem prevItem = mViewItem[currItemId - 1];
//        if (currItem == null || prevItem == null) {
//            Log.e(TAG, "Invalid view item (curr or prev == null). curr = "
//                    + currItemId);
//            return;
//        }
//        if (currItemId > mCurrentItem + 1) {
//            // Every item not right next to the mCurrentItem is invisible.
//            currItem.setVisibility(INVISIBLE);
//            return;
//        }
//        final int prevCenterX = prevItem.getCenterX();
//        if (mCenterX <= prevCenterX) {
//            // Shortcut. If the position is at the center of the previous one,
//            // set to invisible too.
//            currItem.setVisibility(INVISIBLE);
//            return;
//        }
//        final int currCenterX = currItem.getCenterX();
//        final float fadeDownFraction =
//                ((float) mCenterX - prevCenterX) / (currCenterX - prevCenterX);
//        currItem.layoutWithTranslationX(mDrawArea, currCenterX,
//                FILM_STRIP_SCALE + (1f - FILM_STRIP_SCALE) * fadeDownFraction);
//        currItem.setAlpha(fadeDownFraction);
//        currItem.setTranslationX(0);
//        currItem.setVisibility(VISIBLE);
//    }
//    private void layoutViewItems(boolean layoutChanged) {
//        if (mViewItem[mCurrentItem] == null ||
//                mDrawArea.width() == 0 ||
//                mDrawArea.height() == 0) {
//            return;
//        }
//        // If the layout changed, we need to adjust the current position so
//        // that if an item is centered before the change, it's still centered.
//        if (layoutChanged) {
//            mViewItem[mCurrentItem].setLeftPosition(
//                    mCenterX - mViewItem[mCurrentItem].getMeasuredWidth() / 2);
//        }
//        if (inZoomView()) {
//            return;
//        }
//        /**
//         * Transformed scale fraction between 0 and 1. 0 if the scale is
//         * {@link FILM_STRIP_SCALE}. 1 if the scale is {@link FULL_SCREEN_SCALE}
//         * .
//         */
//        final float scaleFraction = mViewAnimInterpolator.getInterpolation(
//                (mScale - FILM_STRIP_SCALE) / (FULL_SCREEN_SCALE - FILM_STRIP_SCALE));
//        final int fullScreenWidth = mDrawArea.width() + mViewGapInPixel;
//        // Decide the position for all view items on the left and the right
//        // first.
//        // Left items.
//        for (int itemID = mCurrentItem - 1; itemID >= 0; itemID--) {
//            final ViewItem curr = mViewItem[itemID];
//            if (curr == null) {
//                break;
//            }
//            // First, layout relatively to the next one.
//            final int currLeft = mViewItem[itemID + 1].getLeftPosition()
//                    - curr.getMeasuredWidth() - mViewGapInPixel;
//            curr.setLeftPosition(currLeft);
//        }
//        // Right items.
//        for (int itemID = mCurrentItem + 1; itemID < BUFFER_SIZE; itemID++) {
//            final ViewItem curr = mViewItem[itemID];
//            if (curr == null) {
//                break;
//            }
//            // First, layout relatively to the previous one.
//            final ViewItem prev = mViewItem[itemID - 1];
//            final int currLeft =
//                    prev.getLeftPosition() + prev.getMeasuredWidth()
//                            + mViewGapInPixel;
//            curr.setLeftPosition(currLeft);
//        }
//        // Special case for the one immediately on the right of the camera
//        // preview.
//        boolean immediateRight =
//                (mViewItem[mCurrentItem].getId() == 1 &&
//                        mDataAdapter.getImageData(0).getViewType() == ImageData.VIEW_TYPE_STICKY);
//        // Layout the current ViewItem first.
//        if (immediateRight) {
//            // Just do a simple layout without any special translation or
//            // fading. The implementation in Gallery does not push the first
//            // photo to the bottom of the camera preview. Simply place the
//            // photo on the right of the preview.
//            final ViewItem currItem = mViewItem[mCurrentItem];
//            currItem.layoutWithTranslationX(mDrawArea, mCenterX, mScale);
//            currItem.setTranslationX(0f);
//            currItem.setAlpha(1f);
//        } else if (scaleFraction == 1f) {
//            final ViewItem currItem = mViewItem[mCurrentItem];
//            final int currCenterX = currItem.getCenterX();
//            if (mCenterX < currCenterX) {
//                // In full-screen and mCenterX is on the left of the center,
//                // we draw the current one to "fade down".
//                fadeAndScaleRightViewItem(mCurrentItem);
//            } else if (mCenterX > currCenterX) {
//                // In full-screen and mCenterX is on the right of the center,
//                // we draw the current one translated.
//                translateLeftViewItem(mCurrentItem, fullScreenWidth, scaleFraction);
//            } else {
//                currItem.layoutWithTranslationX(mDrawArea, mCenterX, mScale);
//                currItem.setTranslationX(0f);
//                currItem.setAlpha(1f);
//            }
//        } else {
//            final ViewItem currItem = mViewItem[mCurrentItem];
//            // The normal filmstrip has no translation for the current item. If
//            // it has translation before, gradually set it to zero.
//            currItem.setTranslationX(currItem.getTranslationX() * scaleFraction);
//            currItem.layoutWithTranslationX(mDrawArea, mCenterX, mScale);
//            if (mViewItem[mCurrentItem - 1] == null) {
//                currItem.setAlpha(1f);
//            } else {
//                final int currCenterX = currItem.getCenterX();
//                final int prevCenterX = mViewItem[mCurrentItem - 1].getCenterX();
//                final float fadeDownFraction =
//                        ((float) mCenterX - prevCenterX) / (currCenterX - prevCenterX);
//                currItem.setAlpha(
//                        (1 - fadeDownFraction) * (1 - scaleFraction) + fadeDownFraction);
//            }
//        }
//        // Layout the rest dependent on the current scale.
//        // Items on the left
//        for (int itemID = mCurrentItem - 1; itemID >= 0; itemID--) {
//            final ViewItem curr = mViewItem[itemID];
//            if (curr == null) {
//                break;
//            }
//            translateLeftViewItem(itemID, fullScreenWidth, scaleFraction);
//        }
//        // Items on the right
//        for (int itemID = mCurrentItem + 1; itemID < BUFFER_SIZE; itemID++) {
//            final ViewItem curr = mViewItem[itemID];
//            if (curr == null) {
//                break;
//            }
//            curr.layoutWithTranslationX(mDrawArea, mCenterX, mScale);
//            if (curr.getId() == 1 && isViewTypeSticky(curr)) {
//                // Special case for the one next to the camera preview.
//                curr.setAlpha(1f);
//                continue;
//            }
//            if (scaleFraction == 1) {
//                // It's in full-screen mode.
//                fadeAndScaleRightViewItem(itemID);
//            } else {
//                boolean setToVisible = (curr.getVisibility() == INVISIBLE);
//                if (itemID == mCurrentItem + 1) {
//                    curr.setAlpha(1f - scaleFraction);
//                } else {
//                    if (scaleFraction == 0f) {
//                        curr.setAlpha(1f);
//                    } else {
//                        setToVisible = false;
//                    }
//                }
//                if (setToVisible) {
//                    curr.setVisibility(VISIBLE);
//                }
//                curr.setTranslationX(
//                        (mViewItem[mCurrentItem].getLeftPosition() - curr.getLeftPosition()) *
//                                scaleFraction);
//            }
//        }
//        stepIfNeeded();
//    }
//    private boolean isViewTypeSticky(ViewItem item) {
//        if (item == null) {
//            return false;
//        }
//        return mDataAdapter.getImageData(item.getId()).getViewType() ==
//                ImageData.VIEW_TYPE_STICKY;
//    }
//    @Override
//    public void onDraw(Canvas c) {
//        // TODO: remove layoutViewItems() here.
//        layoutViewItems(false);
//        super.onDraw(c);
//    }
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        mDrawArea.left = 0;
//        mDrawArea.top = 0;
//        mDrawArea.right = r - l;
//        mDrawArea.bottom = b - t;
//        mZoomView.layout(mDrawArea.left, mDrawArea.top, mDrawArea.right, mDrawArea.bottom);
//        // TODO: Need a more robust solution to decide when to re-layout
//        // If in the middle of zooming, only re-layout when the layout has
//        // changed.
//        if (!inZoomView() || changed) {
//            resetZoomView();
//            layoutViewItems(changed);
//        }
//    }
//    /**
//     * Clears the translation and scale that has been set on the view, cancels
//     * any loading request for image partial decoding, and hides zoom view. This
//     * is needed for when there is a layout change (e.g. when users re-enter the
//     * app, or rotate the device, etc).
//     */
//    private void resetZoomView() {
//        if (!inZoomView()) {
//            return;
//        }
//        ViewItem current = mViewItem[mCurrentItem];
//        if (current == null) {
//            return;
//        }
//        mScale = FULL_SCREEN_SCALE;
//        mController.cancelZoomAnimation();
//        mController.cancelFlingAnimation();
//        current.resetTransform();
//        mController.cancelLoadingZoomedImage();
//        mZoomView.setVisibility(GONE);
//        mController.setSurroundingViewsVisible(true);
//    }
//    private void hideZoomView() {
//        if (inZoomView()) {
//            mController.cancelLoadingZoomedImage();
//            mZoomView.setVisibility(GONE);
//        }
//    }
//    private void slideViewBack(ViewItem item) {
//        item.animateTranslationX(0, GEOMETRY_ADJUST_TIME_MS, mViewAnimInterpolator);
//        item.animateTranslationY(0, GEOMETRY_ADJUST_TIME_MS, mViewAnimInterpolator);
//        item.animateAlpha(1f, GEOMETRY_ADJUST_TIME_MS, mViewAnimInterpolator);
//    }
//    private void animateItemRemoval(int dataID, final ImageData data) {
//        if (mScale > FULL_SCREEN_SCALE) {
//            resetZoomView();
//        }
//        int removedItemId = findItemByDataID(dataID);
//        // adjust the data id to be consistent
//        for (int i = 0; i < BUFFER_SIZE; i++) {
//            if (mViewItem[i] == null || mViewItem[i].getId() <= dataID) {
//                continue;
//            }
//            mViewItem[i].setId(mViewItem[i].getId() - 1);
//        }
//        if (removedItemId == -1) {
//            return;
//        }
//        final ViewItem removedItem = mViewItem[removedItemId];
//        final int offsetX = removedItem.getMeasuredWidth() + mViewGapInPixel;
//        for (int i = removedItemId + 1; i < BUFFER_SIZE; i++) {
//            if (mViewItem[i] != null) {
//                mViewItem[i].setLeftPosition(mViewItem[i].getLeftPosition() - offsetX);
//            }
//        }
//        if (removedItemId >= mCurrentItem
//                && mViewItem[removedItemId].getId() < mDataAdapter.getTotalNumber()) {
//            // Fill the removed item by left shift when the current one or
//            // anyone on the right is removed, and there's more data on the
//            // right available.
//            for (int i = removedItemId; i < BUFFER_SIZE - 1; i++) {
//                mViewItem[i] = mViewItem[i + 1];
//            }
//            // pull data out from the DataAdapter for the last one.
//            int curr = BUFFER_SIZE - 1;
//            int prev = curr - 1;
//            if (mViewItem[prev] != null) {
//                mViewItem[curr] = buildItemFromData(mViewItem[prev].getId() + 1);
//            }
//            // The animation part.
//            if (inFullScreen()) {
//                mViewItem[mCurrentItem].setVisibility(VISIBLE);
//                ViewItem nextItem = mViewItem[mCurrentItem + 1];
//                if (nextItem != null) {
//                    nextItem.setVisibility(INVISIBLE);
//                }
//            }
//            // Translate the views to their original places.
//            for (int i = removedItemId; i < BUFFER_SIZE; i++) {
//                if (mViewItem[i] != null) {
//                    mViewItem[i].setTranslationX(offsetX);
//                }
//            }
//            // The end of the filmstrip might have been changed.
//            // The mCenterX might be out of the bound.
//            ViewItem currItem = mViewItem[mCurrentItem];
//            if(currItem!=null) {
//                if (currItem.getId() == mDataAdapter.getTotalNumber() - 1
//                        && mCenterX > currItem.getCenterX()) {
//                    int adjustDiff = currItem.getCenterX() - mCenterX;
//                    mCenterX = currItem.getCenterX();
//                    for (int i = 0; i < BUFFER_SIZE; i++) {
//                        if (mViewItem[i] != null) {
//                            mViewItem[i].translateXScaledBy(adjustDiff);
//                        }
//                    }
//                }
//            } else {
//                // CurrItem should NOT be NULL, but if is, at least don't crash.
//                Log.w(TAG,"Caught invalid update in removal animation.");
//            }
//        } else {
//            // fill the removed place by right shift
//            mCenterX -= offsetX;
//            for (int i = removedItemId; i > 0; i--) {
//                mViewItem[i] = mViewItem[i - 1];
//            }
//            // pull data out from the DataAdapter for the first one.
//            int curr = 0;
//            int next = curr + 1;
//            if (mViewItem[next] != null) {
//                mViewItem[curr] = buildItemFromData(mViewItem[next].getId() - 1);
//            }
//            // Translate the views to their original places.
//            for (int i = removedItemId; i >= 0; i--) {
//                if (mViewItem[i] != null) {
//                    mViewItem[i].setTranslationX(-offsetX);
//                }
//            }
//        }
//        int transY = getHeight() / 8;
//        if (removedItem.getTranslationY() < 0) {
//            transY = -transY;
//        }
//        removedItem.animateTranslationY(removedItem.getTranslationY() + transY,
//                GEOMETRY_ADJUST_TIME_MS, mViewAnimInterpolator);
//        removedItem.animateAlpha(0f, GEOMETRY_ADJUST_TIME_MS, mViewAnimInterpolator);
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                removedItem.removeViewFromHierarchy(false);
//            }
//        }, GEOMETRY_ADJUST_TIME_MS);
//        adjustChildZOrder();
//        invalidate();
//        // Now, slide every one back.
//        if (mViewItem[mCurrentItem] == null) {
//            return;
//        }
//        for (int i = 0; i < BUFFER_SIZE; i++) {
//            if (mViewItem[i] != null
//                    && mViewItem[i].getTranslationX() != 0f) {
//                slideViewBack(mViewItem[i]);
//            }
//        }
//        if (isCurrentItemCentered() && isViewTypeSticky(mViewItem[mCurrentItem])) {
//            // Special case for scrolling onto the camera preview after removal.
//            mController.goToFullScreen();
//        }
//    }
//    // returns -1 on failure.
//    private int findItemByDataID(int dataID) {
//        for (int i = 0; i < BUFFER_SIZE; i++) {
//            if (mViewItem[i] != null
//                    && mViewItem[i].getId() == dataID) {
//                return i;
//            }
//        }
//        return -1;
//    }
//    private void updateInsertion(int dataID) {
//        int insertedItemId = findItemByDataID(dataID);
//        if (insertedItemId == -1) {
//            // Not in the current item buffers. Check if it's inserted
//            // at the end.
//            if (dataID == mDataAdapter.getTotalNumber() - 1) {
//                int prev = findItemByDataID(dataID - 1);
//                if (prev >= 0 && prev < BUFFER_SIZE - 1) {
//                    // The previous data is in the buffer and we still
//                    // have room for the inserted data.
//                    insertedItemId = prev + 1;
//                }
//            }
//        }
//        // adjust the data id to be consistent
//        for (int i = 0; i < BUFFER_SIZE; i++) {
//            if (mViewItem[i] == null || mViewItem[i].getId() < dataID) {
//                continue;
//            }
//            mViewItem[i].setId(mViewItem[i].getId() + 1);
//        }
//        if (insertedItemId == -1) {
//            return;
//        }
//        final ImageData data = mDataAdapter.getImageData(dataID);
//        Point dim = CameraUtil
//                .resizeToFill(data.getWidth(), data.getHeight(), data.getRotation(),
//                        getMeasuredWidth(), getMeasuredHeight());
//        final int offsetX = dim.x + mViewGapInPixel;
//        ViewItem viewItem = buildItemFromData(dataID);
//        if (viewItem == null) {
//            Log.w(TAG, "unable to build inserted item from data");
//            return;
//        }
//        if (insertedItemId >= mCurrentItem) {
//            if (insertedItemId == mCurrentItem) {
//                viewItem.setLeftPosition(mViewItem[mCurrentItem].getLeftPosition());
//            }
//            // Shift right to make rooms for newly inserted item.
//            removeItem(BUFFER_SIZE - 1);
//            for (int i = BUFFER_SIZE - 1; i > insertedItemId; i--) {
//                mViewItem[i] = mViewItem[i - 1];
//                if (mViewItem[i] != null) {
//                    mViewItem[i].setTranslationX(-offsetX);
//                    slideViewBack(mViewItem[i]);
//                }
//            }
//        } else {
//            // Shift left. Put the inserted data on the left instead of the
//            // found position.
//            --insertedItemId;
//            if (insertedItemId < 0) {
//                return;
//            }
//            removeItem(0);
//            for (int i = 1; i <= insertedItemId; i++) {
//                if (mViewItem[i] != null) {
//                    mViewItem[i].setTranslationX(offsetX);
//                    slideViewBack(mViewItem[i]);
//                    mViewItem[i - 1] = mViewItem[i];
//                }
//            }
//        }
//        mViewItem[insertedItemId] = viewItem;
//        viewItem.setAlpha(0f);
//        viewItem.setTranslationY(getHeight() / 8);
//        slideViewBack(viewItem);
//        adjustChildZOrder();
//        invalidate();
//    }
//    private void setDataAdapter(DataAdapter adapter) {
//        mDataAdapter = adapter;
//        int maxEdge = (int) (Math.max(this.getHeight(), this.getWidth())
//                * FILM_STRIP_SCALE);
//        mDataAdapter.suggestViewSizeBound(maxEdge, maxEdge);
//        mDataAdapter.setListener(new DataAdapter.Listener() {
//            @Override
//            public void onDataLoaded() {
//                reload();
//            }
//            @Override
//            public void onDataUpdated(DataAdapter.UpdateReporter reporter) {
//                update(reporter);
//            }
//            @Override
//            public void onDataInserted(int dataId, ImageData data) {
//                if (mViewItem[mCurrentItem] == null) {
//                    // empty now, simply do a reload.
//                    reload();
//                } else {
//                    updateInsertion(dataId);
//                }
//                if (mListener != null) {
//                    mListener.onDataFocusChanged(dataId, getCurrentId());
//                }
//            }
//            @Override
//            public void onDataRemoved(int dataId, ImageData data) {
//                animateItemRemoval(dataId, data);
//                if (mListener != null) {
//                    mListener.onDataFocusChanged(dataId, getCurrentId());
//                }
//            }
//        });
//    }
//    private boolean inFilmstrip() {
//        return (mScale == FILM_STRIP_SCALE);
//    }
//    private boolean inFullScreen() {
//        return (mScale == FULL_SCREEN_SCALE);
//    }
//    private boolean inZoomView() {
//        return (mScale > FULL_SCREEN_SCALE);
//    }
//    private boolean isCameraPreview() {
//        return isViewTypeSticky(mViewItem[mCurrentItem]);
//    }
//    private boolean inCameraFullscreen() {
//        return isDataAtCenter(0) && inFullScreen()
//                && (isViewTypeSticky(mViewItem[mCurrentItem]));
//    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (mController.isScrolling()) {
//            return true;
//        }
//        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
//            mCheckToIntercept = true;
//            mDown = MotionEvent.obtain(ev);
//            ViewItem viewItem = mViewItem[mCurrentItem];
//            // Do not intercept touch if swipe is not enabled
//            if (viewItem != null && !mDataAdapter.canSwipeInFullScreen(viewItem.getId())) {
//                mCheckToIntercept = false;
//            }
//            return false;
//        } else if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
//            // Do not intercept touch once child is in zoom mode
//            mCheckToIntercept = false;
//            return false;
//        } else {
//            if (!mCheckToIntercept) {
//                return false;
//            }
//            if (ev.getEventTime() - ev.getDownTime() > SWIPE_TIME_OUT) {
//                return false;
//            }
//            int deltaX = (int) (ev.getX() - mDown.getX());
//            int deltaY = (int) (ev.getY() - mDown.getY());
//            if (ev.getActionMasked() == MotionEvent.ACTION_MOVE
//                    && deltaX < mSlop * (-1)) {
//                // intercept left swipe
//                if (Math.abs(deltaX) >= Math.abs(deltaY) * 2) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return mGestureRecognizer.onTouchEvent(ev);
//    }
//    FilmstripGestureRecognizer.Listener getGestureListener() {
//        return mGestureListener;
//    }
//    private void updateViewItem(int itemID) {
//        ViewItem item = mViewItem[itemID];
//        if (item == null) {
//            Log.e(TAG, "trying to update an null item");
//            return;
//        }
//        item.removeViewFromHierarchy(true);
//        ViewItem newItem = buildItemFromData(item.getId());
//        if (newItem == null) {
//            Log.e(TAG, "new item is null");
//            // keep using the old data.
//            item.addViewToHierarchy();
//            return;
//        }
//        newItem.copyAttributes(item);
//        mViewItem[itemID] = newItem;
//        mZoomView.resetDecoder();
//        boolean stopScroll = clampCenterX();
//        if (stopScroll) {
//            mController.stopScrolling(true);
//        }
//        adjustChildZOrder();
//        invalidate();
//        if (mListener != null) {
//            mListener.onDataUpdated(newItem.getId());
//        }
//    }
//    /** Some of the data is changed. */
//    private void update(DataAdapter.UpdateReporter reporter) {
//        // No data yet.
//        if (mViewItem[mCurrentItem] == null) {
//            reload();
//            return;
//        }
//        // Check the current one.
//        ViewItem curr = mViewItem[mCurrentItem];
//        int dataId = curr.getId();
//        if (reporter.isDataRemoved(dataId)) {
//            reload();
//            return;
//        }
//        if (reporter.isDataUpdated(dataId)) {
//            updateViewItem(mCurrentItem);
//            final ImageData data = mDataAdapter.getImageData(dataId);
//            if (!mIsUserScrolling && !mController.isScrolling()) {
//                // If there is no scrolling at all, adjust mCenterX to place
//                // the current item at the center.
//                Point dim = CameraUtil.resizeToFill(data.getWidth(), data.getHeight(),
//                        data.getRotation(), getMeasuredWidth(), getMeasuredHeight());
//                mCenterX = curr.getLeftPosition() + dim.x / 2;
//            }
//        }
//        // Check left
//        for (int i = mCurrentItem - 1; i >= 0; i--) {
//            curr = mViewItem[i];
//            if (curr != null) {
//                dataId = curr.getId();
//                if (reporter.isDataRemoved(dataId) || reporter.isDataUpdated(dataId)) {
//                    updateViewItem(i);
//                }
//            } else {
//                ViewItem next = mViewItem[i + 1];
//                if (next != null) {
//                    mViewItem[i] = buildItemFromData(next.getId() - 1);
//                }
//            }
//        }
//        // Check right
//        for (int i = mCurrentItem + 1; i < BUFFER_SIZE; i++) {
//            curr = mViewItem[i];
//            if (curr != null) {
//                dataId = curr.getId();
//                if (reporter.isDataRemoved(dataId) || reporter.isDataUpdated(dataId)) {
//                    updateViewItem(i);
//                }
//            } else {
//                ViewItem prev = mViewItem[i - 1];
//                if (prev != null) {
//                    mViewItem[i] = buildItemFromData(prev.getId() + 1);
//                }
//            }
//        }
//        adjustChildZOrder();
//        // Request a layout to find the measured width/height of the view first.
//        requestLayout();
//        // Update photo sphere visibility after metadata fully written.
//    }
//    /**
//     * The whole data might be totally different. Flush all and load from the
//     * start. Filmstrip will be centered on the first item, i.e. the camera
//     * preview.
//     */
//    private void reload() {
//        mController.stopScrolling(true);
//        mController.stopScale();
//        mDataIdOnUserScrolling = 0;
//        int prevId = -1;
//        if (mViewItem[mCurrentItem] != null) {
//            prevId = mViewItem[mCurrentItem].getId();
//        }
//        // Remove all views from the mViewItem buffer, except the camera view.
//        for (int i = 0; i < mViewItem.length; i++) {
//            if (mViewItem[i] == null) {
//                continue;
//            }
//            mViewItem[i].removeViewFromHierarchy(false);
//        }
//        // Clear out the mViewItems and rebuild with camera in the center.
//        Arrays.fill(mViewItem, null);
//        int dataNumber = mDataAdapter.getTotalNumber();
//        if (dataNumber == 0) {
//            return;
//        }
//        mViewItem[mCurrentItem] = buildItemFromData(0);
//        if (mViewItem[mCurrentItem] == null) {
//            return;
//        }
//        mViewItem[mCurrentItem].setLeftPosition(0);
//        for (int i = mCurrentItem + 1; i < BUFFER_SIZE; i++) {
//            mViewItem[i] = buildItemFromData(mViewItem[i - 1].getId() + 1);
//            if (mViewItem[i] == null) {
//                break;
//            }
//        }
//        // Ensure that the views in mViewItem will layout the first in the
//        // center of the display upon a reload.
//        mCenterX = -1;
//        mScale = FILM_STRIP_SCALE;
//        adjustChildZOrder();
//        invalidate();
//        if (mListener != null) {
//            mListener.onDataReloaded();
//            mListener.onDataFocusChanged(prevId, mViewItem[mCurrentItem].getId());
//        }
//    }
//    private void promoteData(int itemID, int dataID) {
//        if (mListener != null) {
//            mListener.onFocusedDataPromoted(dataID);
//        }
//    }
//    private void demoteData(int itemID, int dataID) {
//        if (mListener != null) {
//            mListener.onFocusedDataDemoted(dataID);
//        }
//    }
//    private void onEnterFilmstrip() {
//        if (mListener != null) {
//            mListener.onEnterFilmstrip(getCurrentId());
//        }
//    }
//    private void onLeaveFilmstrip() {
//        if (mListener != null) {
//            mListener.onLeaveFilmstrip(getCurrentId());
//        }
//    }
//    private void onEnterFullScreen() {
//        mFullScreenUIHidden = false;
//        if (mListener != null) {
//            mListener.onEnterFullScreenUiShown(getCurrentId());
//        }
//    }
//    private void onLeaveFullScreen() {
//        if (mListener != null) {
//            mListener.onLeaveFullScreenUiShown(getCurrentId());
//        }
//    }
//    private void onEnterFullScreenUiHidden() {
//        mFullScreenUIHidden = true;
//        if (mListener != null) {
//            mListener.onEnterFullScreenUiHidden(getCurrentId());
//        }
//    }
//    private void onLeaveFullScreenUiHidden() {
//        mFullScreenUIHidden = false;
//        if (mListener != null) {
//            mListener.onLeaveFullScreenUiHidden(getCurrentId());
//        }
//    }
//    private void onEnterZoomView() {
//        if (mListener != null) {
//            mListener.onEnterZoomView(getCurrentId());
//        }
//    }
//    private void onLeaveZoomView() {
//        mController.setSurroundingViewsVisible(true);
//    }
//    /**
//     * MyController controls all the geometry animations. It passively tells the
//     * geometry information on demand.
//     */
//    private class MyController implements FilmstripController {
//        private final ValueAnimator mScaleAnimator;
//        private ValueAnimator mZoomAnimator;
//        private AnimatorSet mFlingAnimator;
//        private final MyScroller mScroller;
//        private boolean mCanStopScroll;
//        private final MyScroller.Listener mScrollerListener =
//                new MyScroller.Listener() {
//                    @Override
//                    public void onScrollUpdate(int currX, int currY) {
//                        mCenterX = currX;
//                        boolean stopScroll = clampCenterX();
//                        if (stopScroll) {
//                            mController.stopScrolling(true);
//                        }
//                        invalidate();
//                    }
//                    @Override
//                    public void onScrollEnd() {
//                        mCanStopScroll = true;
//                        if (mViewItem[mCurrentItem] == null) {
//                            return;
//                        }
//                        snapInCenter();
//                        if (isCurrentItemCentered()
//                                && isViewTypeSticky(mViewItem[mCurrentItem])) {
//                            // Special case for the scrolling end on the camera
//                            // preview.
//                            goToFullScreen();
//                        }
//                    }
//                };
//        private final ValueAnimator.AnimatorUpdateListener mScaleAnimatorUpdateListener =
//                new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        if (mViewItem[mCurrentItem] == null) {
//                            return;
//                        }
//                        mScale = (Float) animation.getAnimatedValue();
//                        invalidate();
//                    }
//                };
//        MyController(Context context) {
//            TimeInterpolator decelerateInterpolator = new DecelerateInterpolator(1.5f);
//            mScroller = new MyScroller(mActivity.getAndroidContext(),
//                    new Handler(mActivity.getMainLooper()),
//                    mScrollerListener, decelerateInterpolator);
//            mCanStopScroll = true;
//            mScaleAnimator = new ValueAnimator();
//            mScaleAnimator.addUpdateListener(mScaleAnimatorUpdateListener);
//            mScaleAnimator.setInterpolator(decelerateInterpolator);
//            mScaleAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animator) {
//                    if (mScale == FULL_SCREEN_SCALE) {
//                        onLeaveFullScreen();
//                    } else {
//                        if (mScale == FILM_STRIP_SCALE) {
//                            onLeaveFilmstrip();
//                        }
//                    }
//                }
//                @Override
//                public void onAnimationEnd(Animator animator) {
//                    if (mScale == FULL_SCREEN_SCALE) {
//                        onEnterFullScreen();
//                    } else {
//                        if (mScale == FILM_STRIP_SCALE) {
//                            onEnterFilmstrip();
//                        }
//                    }
//                    zoomAtIndexChanged();
//                }
//                @Override
//                public void onAnimationCancel(Animator animator) {
//                }
//                @Override
//                public void onAnimationRepeat(Animator animator) {
//                }
//            });
//        }
//        @Override
//        public void setImageGap(int imageGap) {
//            FilmstripView.this.setViewGap(imageGap);
//        }
//        @Override
//        public int getCurrentId() {
//            return FilmstripView.this.getCurrentId();
//        }
//        @Override
//        public void setDataAdapter(DataAdapter adapter) {
//            FilmstripView.this.setDataAdapter(adapter);
//        }
//        @Override
//        public boolean inFilmstrip() {
//            return FilmstripView.this.inFilmstrip();
//        }
//        @Override
//        public boolean inFullScreen() {
//            return FilmstripView.this.inFullScreen();
//        }
//        @Override
//        public boolean isCameraPreview() {
//            return FilmstripView.this.isCameraPreview();
//        }
//        @Override
//        public boolean inCameraFullscreen() {
//            return FilmstripView.this.inCameraFullscreen();
//        }
//        @Override
//        public void setListener(FilmstripListener l) {
//            FilmstripView.this.setListener(l);
//        }
//        @Override
//        public boolean isScrolling() {
//            return !mScroller.isFinished();
//        }
//        @Override
//        public boolean isScaling() {
//            return mScaleAnimator.isRunning();
//        }
//        private int estimateMinX(int dataID, int leftPos, int viewWidth) {
//            return leftPos - (dataID + 100) * (viewWidth + mViewGapInPixel);
//        }
//        private int estimateMaxX(int dataID, int leftPos, int viewWidth) {
//            return leftPos
//                    + (mDataAdapter.getTotalNumber() - dataID + 100)
//                    * (viewWidth + mViewGapInPixel);
//        }
//        /** Zoom all the way in or out on the image at the given pivot point. */
//        private void zoomAt(final ViewItem current, final float focusX, final float focusY) {
//            // End previous zoom animation, if any
//            if (mZoomAnimator != null) {
//                mZoomAnimator.end();
//            }
//            // Calculate end scale
//            final float maxScale = getCurrentDataMaxScale(false);
//            final float endScale = mScale < maxScale - maxScale * TOLERANCE
//                    ? maxScale : FULL_SCREEN_SCALE;
//            mZoomAnimator = new ValueAnimator();
//            mZoomAnimator.setFloatValues(mScale, endScale);
//            mZoomAnimator.setDuration(ZOOM_ANIMATION_DURATION_MS);
//            mZoomAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    if (mScale == FULL_SCREEN_SCALE) {
//                        if (mFullScreenUIHidden) {
//                            onLeaveFullScreenUiHidden();
//                        } else {
//                            onLeaveFullScreen();
//                        }
//                        setSurroundingViewsVisible(false);
//                    } else if (inZoomView()) {
//                        onLeaveZoomView();
//                    }
//                    cancelLoadingZoomedImage();
//                }
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    // Make sure animation ends up having the correct scale even
//                    // if it is cancelled before it finishes
//                    if (mScale != endScale) {
//                        current.postScale(focusX, focusY, endScale / mScale, mDrawArea.width(),
//                                mDrawArea.height());
//                        mScale = endScale;
//                    }
//                    if (inFullScreen()) {
//                        setSurroundingViewsVisible(true);
//                        mZoomView.setVisibility(GONE);
//                        current.resetTransform();
//                        onEnterFullScreenUiHidden();
//                    } else {
//                        mController.loadZoomedImage();
//                        onEnterZoomView();
//                    }
//                    mZoomAnimator = null;
//                    zoomAtIndexChanged();
//                }
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                    // Do nothing.
//                }
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//                    // Do nothing.
//                }
//            });
//            mZoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float newScale = (Float) animation.getAnimatedValue();
//                    float postScale = newScale / mScale;
//                    mScale = newScale;
//                    current.postScale(focusX, focusY, postScale, mDrawArea.width(),
//                            mDrawArea.height());
//                }
//            });
//            mZoomAnimator.start();
//        }
//        @Override
//        public void scroll(float deltaX) {
//            if (!stopScrolling(false)) {
//                return;
//            }
//            mCenterX += deltaX;
//            boolean stopScroll = clampCenterX();
//            if (stopScroll) {
//                mController.stopScrolling(true);
//            }
//            invalidate();
//        }
//        @Override
//        public void fling(float velocityX) {
//            if (!stopScrolling(false)) {
//                return;
//            }
//            final ViewItem item = mViewItem[mCurrentItem];
//            if (item == null) {
//                return;
//            }
//            float scaledVelocityX = velocityX / mScale;
//            if (inFullScreen() && isViewTypeSticky(item) && scaledVelocityX < 0) {
//                // Swipe left in camera preview.
//                goToFilmstrip();
//            }
//            int w = getWidth();
//            // Estimation of possible length on the left. To ensure the
//            // velocity doesn't become too slow eventually, we add a huge number
//            // to the estimated maximum.
//            int minX = estimateMinX(item.getId(), item.getLeftPosition(), w);
//            // Estimation of possible length on the right. Likewise, exaggerate
//            // the possible maximum too.
//            int maxX = estimateMaxX(item.getId(), item.getLeftPosition(), w);
//            mScroller.fling(mCenterX, 0, (int) -velocityX, 0, minX, maxX, 0, 0);
//        }
//        void flingInsideZoomView(float velocityX, float velocityY) {
//            if (!inZoomView()) {
//                return;
//            }
//            final ViewItem current = mViewItem[mCurrentItem];
//            if (current == null) {
//                return;
//            }
//            final int factor = DECELERATION_FACTOR;
//            // Deceleration curve for distance:
//            // S(t) = s + (e - s) * (1 - (1 - t/T) ^ factor)
//            // Need to find the ending distance (e), so that the starting
//            // velocity is the velocity of fling.
//            // Velocity is the derivative of distance
//            // V(t) = (e - s) * factor * (-1) * (1 - t/T) ^ (factor - 1) * (-1/T)
//            //      = (e - s) * factor * (1 - t/T) ^ (factor - 1) / T
//            // Since V(0) = V0, we have e = T / factor * V0 + s
//            // Duration T should be long enough so that at the end of the fling,
//            // image moves at 1 pixel/s for about P = 50ms = 0.05s
//            // i.e. V(T - P) = 1
//            // V(T - P) = V0 * (1 - (T -P) /T) ^ (factor - 1) = 1
//            // T = P * V0 ^ (1 / (factor -1))
//            final float velocity = Math.max(Math.abs(velocityX), Math.abs(velocityY));
//            // Dynamically calculate duration
//            final float duration = (float) (FLING_COASTING_DURATION_S
//                    * Math.pow(velocity, (1f / (factor - 1f))));
//            final float translationX = current.getTranslationX() * mScale;
//            final float translationY = current.getTranslationY() * mScale;
//            final ValueAnimator decelerationX = ValueAnimator.ofFloat(translationX,
//                    translationX + duration / factor * velocityX);
//            final ValueAnimator decelerationY = ValueAnimator.ofFloat(translationY,
//                    translationY + duration / factor * velocityY);
//            decelerationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float transX = (Float) decelerationX.getAnimatedValue();
//                    float transY = (Float) decelerationY.getAnimatedValue();
//                    current.updateTransform(transX, transY, mScale,
//                            mScale, mDrawArea.width(), mDrawArea.height());
//                }
//            });
//            mFlingAnimator = new AnimatorSet();
//            mFlingAnimator.play(decelerationX).with(decelerationY);
//            mFlingAnimator.setDuration((int) (duration * 1000));
//            mFlingAnimator.setInterpolator(new TimeInterpolator() {
//                @Override
//                public float getInterpolation(float input) {
//                    return (float) (1.0f - Math.pow((1.0f - input), factor));
//                }
//            });
//            mFlingAnimator.addListener(new Animator.AnimatorListener() {
//                private boolean mCancelled = false;
//                @Override
//                public void onAnimationStart(Animator animation) {
//                }
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    if (!mCancelled) {
//                        loadZoomedImage();
//                    }
//                    mFlingAnimator = null;
//                }
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                    mCancelled = true;
//                }
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//                }
//            });
//            mFlingAnimator.start();
//        }
//        @Override
//        public boolean stopScrolling(boolean forced) {
//            if (!isScrolling()) {
//                return true;
//            } else if (!mCanStopScroll && !forced) {
//                return false;
//            }
//            mScroller.forceFinished(true);
//            return true;
//        }
//        private void stopScale() {
//            mScaleAnimator.cancel();
//        }
//        @Override
//        public void scrollToPosition(int position, int duration, boolean interruptible) {
//            if (mViewItem[mCurrentItem] == null) {
//                return;
//            }
//            mCanStopScroll = interruptible;
//            mScroller.startScroll(mCenterX, 0, position - mCenterX, 0, duration);
//        }
//        @Override
//        public boolean goToNextItem() {
//            return goToItem(mCurrentItem + 1);
//        }
//        @Override
//        public boolean goToPreviousItem() {
//            return goToItem(mCurrentItem - 1);
//        }
//        private boolean goToItem(int itemIndex) {
//            final ViewItem nextItem = mViewItem[itemIndex];
//            if (nextItem == null) {
//                return false;
//            }
//            stopScrolling(true);
//            scrollToPosition(nextItem.getCenterX(), GEOMETRY_ADJUST_TIME_MS * 2, false);
//            if (isViewTypeSticky(mViewItem[mCurrentItem])) {
//                // Special case when moving from camera preview.
//                scaleTo(FILM_STRIP_SCALE, GEOMETRY_ADJUST_TIME_MS);
//            }
//            return true;
//        }
//        private void scaleTo(float scale, int duration) {
//            if (mViewItem[mCurrentItem] == null) {
//                return;
//            }
//            stopScale();
//            mScaleAnimator.setDuration(duration);
//            mScaleAnimator.setFloatValues(mScale, scale);
//            mScaleAnimator.start();
//        }
//        @Override
//        public void goToFilmstrip() {
//            if (mViewItem[mCurrentItem] == null) {
//                return;
//            }
//            if (mScale == FILM_STRIP_SCALE) {
//                return;
//            }
//            scaleTo(FILM_STRIP_SCALE, GEOMETRY_ADJUST_TIME_MS);
//            final ViewItem currItem = mViewItem[mCurrentItem];
//            final ViewItem nextItem = mViewItem[mCurrentItem + 1];
//            if (currItem.getId() == 0 && isViewTypeSticky(currItem) && nextItem != null) {
//                // Deal with the special case of swiping in camera preview.
//                scrollToPosition(nextItem.getCenterX(), GEOMETRY_ADJUST_TIME_MS, false);
//            }
//            if (mScale == FILM_STRIP_SCALE) {
//                onLeaveFilmstrip();
//            }
//        }
//        @Override
//        public void goToFullScreen() {
//            if (inFullScreen()) {
//                return;
//            }
//            scaleTo(FULL_SCREEN_SCALE, GEOMETRY_ADJUST_TIME_MS);
//        }
//        private void cancelFlingAnimation() {
//            // Cancels flinging for zoomed images
//            if (isFlingAnimationRunning()) {
//                mFlingAnimator.cancel();
//            }
//        }
//        private void cancelZoomAnimation() {
//            if (isZoomAnimationRunning()) {
//                mZoomAnimator.cancel();
//            }
//        }
//        private void setSurroundingViewsVisible(boolean visible) {
//            // Hide everything on the left
//            // TODO: Need to find a better way to toggle the visibility of views
//            // around the current view.
//            for (int i = 0; i < mCurrentItem; i++) {
//                if (i == mCurrentItem || mViewItem[i] == null) {
//                    continue;
//                }
//                mViewItem[i].setVisibility(visible ? VISIBLE : INVISIBLE);
//            }
//        }
//        private Uri getCurrentUri() {
//            ViewItem curr = mViewItem[mCurrentItem];
//            if (curr == null) {
//                return Uri.EMPTY;
//            }
//            return mDataAdapter.getImageData(curr.getId()).getUri();
//        }
//        /**
//         * Here we only support up to 1:1 image zoom (i.e. a 100% view of the
//         * actual pixels). The max scale that we can apply on the view should
//         * make the view same size as the image, in pixels.
//         */
//        private float getCurrentDataMaxScale(boolean allowOverScale) {
//            ViewItem curr = mViewItem[mCurrentItem];
//            if (curr == null) {
//                return FULL_SCREEN_SCALE;
//            }
//            ImageData imageData = mDataAdapter.getImageData(curr.getId());
//            if (imageData == null || !imageData.isUIActionSupported(ImageData.ACTION_ZOOM)) {
//                return FULL_SCREEN_SCALE;
//            }
//            float imageWidth = imageData.getWidth();
//            if (imageData.getRotation() == 90
//                    || imageData.getRotation() == 270) {
//                imageWidth = imageData.getHeight();
//            }
//            float scale = imageWidth / curr.getWidth();
//            if (allowOverScale) {
//                // In addition to the scale we apply to the view for 100% view
//                // (i.e. each pixel on screen corresponds to a pixel in image)
//                // we allow scaling beyond that for better detail viewing.
//                scale *= mOverScaleFactor;
//            }
//            return scale;
//        }
//        private void loadZoomedImage() {
//            if (!inZoomView()) {
//                return;
//            }
//            ViewItem curr = mViewItem[mCurrentItem];
//            if (curr == null) {
//                return;
//            }
//            ImageData imageData = mDataAdapter.getImageData(curr.getId());
//            if (!imageData.isUIActionSupported(ImageData.ACTION_ZOOM)) {
//                return;
//            }
//            Uri uri = getCurrentUri();
//            RectF viewRect = curr.getViewRect();
//            if (uri == null || uri == Uri.EMPTY) {
//                return;
//            }
//            int orientation = imageData.getRotation();
//            mZoomView.loadBitmap(uri, orientation, viewRect);
//        }
//        private void cancelLoadingZoomedImage() {
//            mZoomView.cancelPartialDecodingTask();
//        }
//        @Override
//        public void goToFirstItem() {
//            if (mViewItem[mCurrentItem] == null) {
//                return;
//            }
//            resetZoomView();
//            // TODO: animate to camera if it is still in the mViewItem buffer
//            // versus a full reload which will perform an immediate transition
//            reload();
//        }
//        public boolean inZoomView() {
//            return FilmstripView.this.inZoomView();
//        }
//        public boolean isFlingAnimationRunning() {
//            return mFlingAnimator != null && mFlingAnimator.isRunning();
//        }
//        public boolean isZoomAnimationRunning() {
//            return mZoomAnimator != null && mZoomAnimator.isRunning();
//        }
//    }
//    private boolean isCurrentItemCentered() {
//        return mViewItem[mCurrentItem].getCenterX() == mCenterX;
//    }
//    private static class MyScroller {
//        public interface Listener {
//            public void onScrollUpdate(int currX, int currY);
//            public void onScrollEnd();
//        }
//        private final Handler mHandler;
//        private final Listener mListener;
//        private final Scroller mScroller;
//        private final ValueAnimator mXScrollAnimator;
//        private final Runnable mScrollChecker = new Runnable() {
//            @Override
//            public void run() {
//                boolean newPosition = mScroller.computeScrollOffset();
//                if (!newPosition) {
//                    mListener.onScrollEnd();
//                    return;
//                }
//                mListener.onScrollUpdate(mScroller.getCurrX(), mScroller.getCurrY());
//                mHandler.removeCallbacks(this);
//                mHandler.post(this);
//            }
//        };
//        private final ValueAnimator.AnimatorUpdateListener mXScrollAnimatorUpdateListener =
//                new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mListener.onScrollUpdate((Integer) animation.getAnimatedValue(), 0);
//                    }
//                };
//        private final Animator.AnimatorListener mXScrollAnimatorListener =
//                new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        // Do nothing.
//                    }
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mListener.onScrollEnd();
//                    }
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//                        // Do nothing.
//                    }
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        // Do nothing.
//                    }
//                };
//        public MyScroller(Context ctx, Handler handler, Listener listener,
//                          TimeInterpolator interpolator) {
//            mHandler = handler;
//            mListener = listener;
//            mScroller = new Scroller(ctx);
//            mXScrollAnimator = new ValueAnimator();
//            mXScrollAnimator.addUpdateListener(mXScrollAnimatorUpdateListener);
//            mXScrollAnimator.addListener(mXScrollAnimatorListener);
//            mXScrollAnimator.setInterpolator(interpolator);
//        }
//        public void fling(
//                int startX, int startY,
//                int velocityX, int velocityY,
//                int minX, int maxX,
//                int minY, int maxY) {
//            mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
//            runChecker();
//        }
//        public void startScroll(int startX, int startY, int dx, int dy) {
//            mScroller.startScroll(startX, startY, dx, dy);
//            runChecker();
//        }
//        /** Only starts and updates scroll in x-axis. */
//        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
//            mXScrollAnimator.cancel();
//            mXScrollAnimator.setDuration(duration);
//            mXScrollAnimator.setIntValues(startX, startX + dx);
//            mXScrollAnimator.start();
//        }
//        public boolean isFinished() {
//            return (mScroller.isFinished() && !mXScrollAnimator.isRunning());
//        }
//        public void forceFinished(boolean finished) {
//            mScroller.forceFinished(finished);
//            if (finished) {
//                mXScrollAnimator.cancel();
//            }
//        }
//        private void runChecker() {
//            if (mHandler == null || mListener == null) {
//                return;
//            }
//            mHandler.removeCallbacks(mScrollChecker);
//            mHandler.post(mScrollChecker);
//        }
//    }
//    private class MyGestureReceiver implements FilmstripGestureRecognizer.Listener {
//        private static final int SCROLL_DIR_NONE = 0;
//        private static final int SCROLL_DIR_VERTICAL = 1;
//        private static final int SCROLL_DIR_HORIZONTAL = 2;
//        // Indicating the current trend of scaling is up (>1) or down (<1).
//        private float mScaleTrend;
//        private float mMaxScale;
//        private int mScrollingDirection = SCROLL_DIR_NONE;
//        private long mLastDownTime;
//        private float mLastDownY;
//        @Override
//        public boolean onSingleTapUp(float x, float y) {
//            ViewItem centerItem = mViewItem[mCurrentItem];
//            if (inFilmstrip()) {
//                if (centerItem != null && centerItem.areaContains(x, y)) {
//                    mController.goToFullScreen();
//                    return true;
//                }
//            } else if (inFullScreen()) {
//                if (mFullScreenUIHidden) {
//                    onLeaveFullScreenUiHidden();
//                    onEnterFullScreen();
//                } else {
//                    onLeaveFullScreen();
//                    onEnterFullScreenUiHidden();
//                }
//                return true;
//            }
//            return false;
//        }
//        @Override
//        public boolean onDoubleTap(float x, float y) {
//            ViewItem current = mViewItem[mCurrentItem];
//            if (current == null) {
//                return false;
//            }
//            if (inFilmstrip()) {
//                mController.goToFullScreen();
//                return true;
//            } else if (mScale < FULL_SCREEN_SCALE || inCameraFullscreen()) {
//                return false;
//            }
//            if (!mController.stopScrolling(false)) {
//                return false;
//            }
//            if (inFullScreen()) {
//                mController.zoomAt(current, x, y);
//                checkItemAtMaxSize();
//                return true;
//            } else if (mScale > FULL_SCREEN_SCALE) {
//                // In zoom view.
//                mController.zoomAt(current, x, y);
//            }
//            return false;
//        }
//        @Override
//        public boolean onDown(float x, float y) {
//            mLastDownTime = SystemClock.uptimeMillis();
//            mLastDownY = y;
//            mController.cancelFlingAnimation();
//            if (!mController.stopScrolling(false)) {
//                return false;
//            }
//            return true;
//        }
//        @Override
//        public boolean onUp(float x, float y) {
//            ViewItem currItem = mViewItem[mCurrentItem];
//            if (currItem == null) {
//                return false;
//            }
//            if (mController.isZoomAnimationRunning() || mController.isFlingAnimationRunning()) {
//                return false;
//            }
//            if (inZoomView()) {
//                mController.loadZoomedImage();
//                return true;
//            }
//            float promoteHeight = getHeight() * PROMOTE_HEIGHT_RATIO;
//            float velocityPromoteHeight = getHeight() * VELOCITY_PROMOTE_HEIGHT_RATIO;
//            mIsUserScrolling = false;
//            mScrollingDirection = SCROLL_DIR_NONE;
//            // Finds items promoted/demoted.
//            float speedY = Math.abs(y - mLastDownY)
//                    / (SystemClock.uptimeMillis() - mLastDownTime);
//            for (int i = 0; i < BUFFER_SIZE; i++) {
//                if (mViewItem[i] == null) {
//                    continue;
//                }
//                float transY = mViewItem[i].getTranslationY();
//                if (transY == 0) {
//                    continue;
//                }
//                int id = mViewItem[i].getId();
//                if (mDataAdapter.getImageData(id)
//                        .isUIActionSupported(ImageData.ACTION_DEMOTE)
//                        && ((transY > promoteHeight)
//                        || (transY > velocityPromoteHeight && speedY > PROMOTE_VELOCITY))) {
//                    demoteData(i, id);
//                } else if (mDataAdapter.getImageData(id)
//                        .isUIActionSupported(ImageData.ACTION_PROMOTE)
//                        && (transY < -promoteHeight
//                        || (transY < -velocityPromoteHeight && speedY > PROMOTE_VELOCITY))) {
//                    promoteData(i, id);
//                } else {
//                    // put the view back.
//                    slideViewBack(mViewItem[i]);
//                }
//            }
//            // The data might be changed. Re-check.
//            currItem = mViewItem[mCurrentItem];
//            if (currItem == null) {
//                return true;
//            }
//            int currId = currItem.getId();
//            if (mCenterX > currItem.getCenterX() + CAMERA_PREVIEW_SWIPE_THRESHOLD && currId == 0 &&
//                    isViewTypeSticky(currItem) && mDataIdOnUserScrolling == 0) {
//                mController.goToFilmstrip();
//                // Special case to go from camera preview to the next photo.
//                if (mViewItem[mCurrentItem + 1] != null) {
//                    mController.scrollToPosition(
//                            mViewItem[mCurrentItem + 1].getCenterX(),
//                            GEOMETRY_ADJUST_TIME_MS, false);
//                } else {
//                    // No next photo.
//                    snapInCenter();
//                }
//            }
//            if (isCurrentItemCentered() && currId == 0 && isViewTypeSticky(currItem)) {
//                mController.goToFullScreen();
//            } else {
//                if (mDataIdOnUserScrolling == 0 && currId != 0) {
//                    // Special case to go to filmstrip when the user scroll away
//                    // from the camera preview and the current one is not the
//                    // preview anymore.
//                    mController.goToFilmstrip();
//                    mDataIdOnUserScrolling = currId;
//                }
//                snapInCenter();
//            }
//            return false;
//        }
//        @Override
//        public void onLongPress(float x, float y) {
//            final int dataId = getCurrentId();
//            if (dataId == -1) {
//                return;
//            }
//            mListener.onFocusedDataLongPressed(dataId);
//        }
//        @Override
//        public boolean onScroll(float x, float y, float dx, float dy) {
//            final ViewItem currItem = mViewItem[mCurrentItem];
//            if (currItem == null) {
//                return false;
//            }
//            if (inFullScreen() && !mDataAdapter.canSwipeInFullScreen(currItem.getId())) {
//                return false;
//            }
//            hideZoomView();
//            // When image is zoomed in to be bigger than the screen
//            if (inZoomView()) {
//                ViewItem curr = mViewItem[mCurrentItem];
//                float transX = curr.getTranslationX() * mScale - dx;
//                float transY = curr.getTranslationY() * mScale - dy;
//                curr.updateTransform(transX, transY, mScale, mScale, mDrawArea.width(),
//                        mDrawArea.height());
//                return true;
//            }
//            int deltaX = (int) (dx / mScale);
//            // Forces the current scrolling to stop.
//            mController.stopScrolling(true);
//            if (!mIsUserScrolling) {
//                mIsUserScrolling = true;
//                mDataIdOnUserScrolling = mViewItem[mCurrentItem].getId();
//            }
//            if (inFilmstrip()) {
//                // Disambiguate horizontal/vertical first.
//                if (mScrollingDirection == SCROLL_DIR_NONE) {
//                    mScrollingDirection = (Math.abs(dx) > Math.abs(dy)) ? SCROLL_DIR_HORIZONTAL :
//                            SCROLL_DIR_VERTICAL;
//                }
//                if (mScrollingDirection == SCROLL_DIR_HORIZONTAL) {
//                    if (mCenterX == currItem.getCenterX() && currItem.getId() == 0 && dx < 0) {
//                        // Already at the beginning, don't process the swipe.
//                        mIsUserScrolling = false;
//                        mScrollingDirection = SCROLL_DIR_NONE;
//                        return false;
//                    }
//                    mController.scroll(deltaX);
//                } else {
//                    // Vertical part. Promote or demote.
//                    int hit = 0;
//                    Rect hitRect = new Rect();
//                    for (; hit < BUFFER_SIZE; hit++) {
//                        if (mViewItem[hit] == null) {
//                            continue;
//                        }
//                        mViewItem[hit].getHitRect(hitRect);
//                        if (hitRect.contains((int) x, (int) y)) {
//                            break;
//                        }
//                    }
//                    if (hit == BUFFER_SIZE) {
//                        // Hit none.
//                        return true;
//                    }
//                    ImageData data = mDataAdapter.getImageData(mViewItem[hit].getId());
//                    float transY = mViewItem[hit].getTranslationY() - dy / mScale;
//                    if (!data.isUIActionSupported(ImageData.ACTION_DEMOTE) &&
//                            transY > 0f) {
//                        transY = 0f;
//                    }
//                    if (!data.isUIActionSupported(ImageData.ACTION_PROMOTE) &&
//                            transY < 0f) {
//                        transY = 0f;
//                    }
//                    mViewItem[hit].setTranslationY(transY);
//                }
//            } else if (inFullScreen()) {
//                if (mViewItem[mCurrentItem] == null || (deltaX < 0 && mCenterX <=
//                        currItem.getCenterX() && currItem.getId() == 0)) {
//                    return false;
//                }
//                // Multiplied by 1.2 to make it more easy to swipe.
//                mController.scroll((int) (deltaX * 1.2));
//            }
//            invalidate();
//            return true;
//        }
//        @Override
//        public boolean onFling(float velocityX, float velocityY) {
//            final ViewItem currItem = mViewItem[mCurrentItem];
//            if (currItem == null) {
//                return false;
//            }
//            if (!mDataAdapter.canSwipeInFullScreen(currItem.getId())) {
//                return false;
//            }
//            if (inZoomView()) {
//                // Fling within the zoomed image
//                mController.flingInsideZoomView(velocityX, velocityY);
//                return true;
//            }
//            if (Math.abs(velocityX) < Math.abs(velocityY)) {
//                // ignore vertical fling.
//                return true;
//            }
//            // In full-screen, fling of a velocity above a threshold should go
//            // to the next/prev photos
//            if (mScale == FULL_SCREEN_SCALE) {
//                int currItemCenterX = currItem.getCenterX();
//                if (velocityX > 0) { // left
//                    if (mCenterX > currItemCenterX) {
//                        // The visually previous item is actually the current
//                        // item.
//                        mController.scrollToPosition(
//                                currItemCenterX, GEOMETRY_ADJUST_TIME_MS, true);
//                        return true;
//                    }
//                    ViewItem prevItem = mViewItem[mCurrentItem - 1];
//                    if (prevItem == null) {
//                        return false;
//                    }
//                    mController.scrollToPosition(
//                            prevItem.getCenterX(), GEOMETRY_ADJUST_TIME_MS, true);
//                } else { // right
//                    if (mController.stopScrolling(false)) {
//                        if (mCenterX < currItemCenterX) {
//                            // The visually next item is actually the current
//                            // item.
//                            mController.scrollToPosition(
//                                    currItemCenterX, GEOMETRY_ADJUST_TIME_MS, true);
//                            return true;
//                        }
//                        final ViewItem nextItem = mViewItem[mCurrentItem + 1];
//                        if (nextItem == null) {
//                            return false;
//                        }
//                        mController.scrollToPosition(
//                                nextItem.getCenterX(), GEOMETRY_ADJUST_TIME_MS, true);
//                        if (isViewTypeSticky(currItem)) {
//                            mController.goToFilmstrip();
//                        }
//                    }
//                }
//            }
//            if (mScale == FILM_STRIP_SCALE) {
//                mController.fling(velocityX);
//            }
//            return true;
//        }
//        @Override
//        public boolean onScaleBegin(float focusX, float focusY) {
//            if (inCameraFullscreen()) {
//                return false;
//            }
//            hideZoomView();
//            mScaleTrend = 1f;
//            // If the image is smaller than screen size, we should allow to zoom
//            // in to full screen size
//            mMaxScale = Math.max(mController.getCurrentDataMaxScale(true), FULL_SCREEN_SCALE);
//            return true;
//        }
//        @Override
//        public boolean onScale(float focusX, float focusY, float scale) {
//            if (inCameraFullscreen()) {
//                return false;
//            }
//            mScaleTrend = mScaleTrend * 0.3f + scale * 0.7f;
//            float newScale = mScale * scale;
//            if (mScale < FULL_SCREEN_SCALE && newScale < FULL_SCREEN_SCALE) {
//                if (newScale <= FILM_STRIP_SCALE) {
//                    newScale = FILM_STRIP_SCALE;
//                }
//                // Scaled view is smaller than or equal to screen size both
//                // before and after scaling
//                if (mScale != newScale) {
//                    if (mScale == FILM_STRIP_SCALE) {
//                        onLeaveFilmstrip();
//                    }
//                    if (newScale == FILM_STRIP_SCALE) {
//                        onEnterFilmstrip();
//                    }
//                }
//                mScale = newScale;
//                invalidate();
//            } else if (mScale < FULL_SCREEN_SCALE && newScale >= FULL_SCREEN_SCALE) {
//                // Going from smaller than screen size to bigger than or equal
//                // to screen size
//                if (mScale == FILM_STRIP_SCALE) {
//                    onLeaveFilmstrip();
//                }
//                mScale = FULL_SCREEN_SCALE;
//                onEnterFullScreen();
//                mController.setSurroundingViewsVisible(false);
//                invalidate();
//            } else if (mScale >= FULL_SCREEN_SCALE && newScale < FULL_SCREEN_SCALE) {
//                // Going from bigger than or equal to screen size to smaller
//                // than screen size
//                if (inFullScreen()) {
//                    if (mFullScreenUIHidden) {
//                        onLeaveFullScreenUiHidden();
//                    } else {
//                        onLeaveFullScreen();
//                    }
//                } else {
//                    onLeaveZoomView();
//                }
//                mScale = newScale;
//                onEnterFilmstrip();
//                invalidate();
//            } else {
//                // Scaled view bigger than or equal to screen size both before
//                // and after scaling
//                if (!inZoomView()) {
//                    mController.setSurroundingViewsVisible(false);
//                }
//                ViewItem curr = mViewItem[mCurrentItem];
//                // Make sure the image is not overly scaled
//                newScale = Math.min(newScale, mMaxScale);
//                if (newScale == mScale) {
//                    return true;
//                }
//                float postScale = newScale / mScale;
//                curr.postScale(focusX, focusY, postScale, mDrawArea.width(), mDrawArea.height());
//                mScale = newScale;
//                if (mScale == FULL_SCREEN_SCALE) {
//                    onEnterFullScreen();
//                } else {
//                    onEnterZoomView();
//                }
//                checkItemAtMaxSize();
//            }
//            return true;
//        }
//        @Override
//        public void onScaleEnd() {
//            zoomAtIndexChanged();
//            if (mScale > FULL_SCREEN_SCALE + TOLERANCE) {
//                return;
//            }
//            mController.setSurroundingViewsVisible(true);
//            if (mScale <= FILM_STRIP_SCALE + TOLERANCE) {
//                mController.goToFilmstrip();
//            } else if (mScaleTrend > 1f || mScale > FULL_SCREEN_SCALE - TOLERANCE) {
//                if (inZoomView()) {
//                    mScale = FULL_SCREEN_SCALE;
//                    resetZoomView();
//                }
//                mController.goToFullScreen();
//            } else {
//                mController.goToFilmstrip();
//            }
//            mScaleTrend = 1f;
//        }
//    }
}

