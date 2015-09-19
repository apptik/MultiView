package io.apptik.widget.multiview.scalablerecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;

public class DuplexGridLayoutManager extends RecyclerView.LayoutManager {

    GridLayoutManager lm1;
    GridLayoutManager lm2;


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return lm1.generateDefaultLayoutParams();
    }

    @Override
    public void attachView(View child, int index) {
        super.attachView(child, index);
    }

    @Override
    public void addDisappearingView(View child) {
        super.addDisappearingView(child);
    }

    @Override
    public void addDisappearingView(View child, int index) {
        super.addDisappearingView(child, index);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public void assertInLayoutOrScroll(String message) {
        super.assertInLayoutOrScroll(message);
    }

    @Override
    public void assertNotInLayoutOrScroll(String message) {
        super.assertNotInLayoutOrScroll(message);
    }

    @Override
    public void attachView(View child) {
        super.attachView(child);
    }

    @Override
    public void attachView(View child, int index, RecyclerView.LayoutParams lp) {
        super.attachView(child, index, lp);
    }

    @Override
    public void calculateItemDecorationsForChild(View child, Rect outRect) {
        super.calculateItemDecorationsForChild(child, outRect);
    }

    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return super.computeHorizontalScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return super.computeHorizontalScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return super.computeHorizontalScrollRange(state);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return super.computeVerticalScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return super.computeVerticalScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return super.computeVerticalScrollRange(state);
    }

    @Override
    public void detachAndScrapAttachedViews(RecyclerView.Recycler recycler) {
        super.detachAndScrapAttachedViews(recycler);
    }

    @Override
    public void detachAndScrapView(View child, RecyclerView.Recycler recycler) {
        super.detachAndScrapView(child, recycler);
    }

    @Override
    public void detachAndScrapViewAt(int index, RecyclerView.Recycler recycler) {
        super.detachAndScrapViewAt(index, recycler);
    }

    @Override
    public void detachView(View child) {
        super.detachView(child);
    }

    @Override
    public void detachViewAt(int index) {
        super.detachViewAt(index);
    }

    @Override
    public void endAnimation(View view) {
        super.endAnimation(view);
    }

    @Override
    public View findViewByPosition(int position) {
        return super.findViewByPosition(position);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return super.generateLayoutParams(c, attrs);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return super.generateLayoutParams(lp);
    }

    @Override
    public int getBaseline() {
        return super.getBaseline();
    }

    @Override
    public int getBottomDecorationHeight(View child) {
        return super.getBottomDecorationHeight(child);
    }

    @Override
    public View getChildAt(int index) {
        return super.getChildAt(index);
    }

    @Override
    public int getChildCount() {
        return super.getChildCount();
    }

    @Override
    public boolean getClipToPadding() {
        return super.getClipToPadding();
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getColumnCountForAccessibility(recycler, state);
    }

    @Override
    public int getDecoratedBottom(View child) {
        return super.getDecoratedBottom(child);
    }

    @Override
    public int getDecoratedLeft(View child) {
        return super.getDecoratedLeft(child);
    }

    @Override
    public int getDecoratedMeasuredHeight(View child) {
        return super.getDecoratedMeasuredHeight(child);
    }

    @Override
    public int getDecoratedMeasuredWidth(View child) {
        return super.getDecoratedMeasuredWidth(child);
    }

    @Override
    public int getDecoratedRight(View child) {
        return super.getDecoratedRight(child);
    }

    @Override
    public int getDecoratedTop(View child) {
        return super.getDecoratedTop(child);
    }

    @Override
    public View getFocusedChild() {
        return super.getFocusedChild();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(View view) {
        return super.getItemViewType(view);
    }

    @Override
    public int getLayoutDirection() {
        return super.getLayoutDirection();
    }

    @Override
    public int getLeftDecorationWidth(View child) {
        return super.getLeftDecorationWidth(child);
    }

    @Override
    public int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override
    public int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override
    public int getPaddingBottom() {
        return super.getPaddingBottom();
    }

    @Override
    public int getPaddingEnd() {
        return super.getPaddingEnd();
    }

    @Override
    public int getPaddingLeft() {
        return super.getPaddingLeft();
    }

    @Override
    public int getPaddingRight() {
        return super.getPaddingRight();
    }

    @Override
    public int getPaddingStart() {
        return super.getPaddingStart();
    }

    @Override
    public int getPaddingTop() {
        return super.getPaddingTop();
    }

    @Override
    public int getPosition(View view) {
        return super.getPosition(view);
    }

    @Override
    public int getRightDecorationWidth(View child) {
        return super.getRightDecorationWidth(child);
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getRowCountForAccessibility(recycler, state);
    }

    @Override
    public int getSelectionModeForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getSelectionModeForAccessibility(recycler, state);
    }

    @Override
    public int getTopDecorationHeight(View child) {
        return super.getTopDecorationHeight(child);
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public boolean hasFocus() {
        return super.hasFocus();
    }

    @Override
    public void ignoreView(View view) {
        super.ignoreView(view);
    }

    @Override
    public boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    @Override
    public boolean isFocused() {
        return super.isFocused();
    }

    @Override
    public boolean isLayoutHierarchical(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.isLayoutHierarchical(recycler, state);
    }

    @Override
    public boolean isSmoothScrolling() {
        return super.isSmoothScrolling();
    }

    @Override
    public void layoutDecorated(View child, int left, int top, int right, int bottom) {
        super.layoutDecorated(child, left, top, right, bottom);
    }

    public DuplexGridLayoutManager() {
        super();
    }

    @Override
    public void measureChild(View child, int widthUsed, int heightUsed) {
        super.measureChild(child, widthUsed, heightUsed);
    }

    @Override
    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);
    }

    @Override
    public void moveView(int fromIndex, int toIndex) {
        super.moveView(fromIndex, toIndex);
    }

    @Override
    public void offsetChildrenHorizontal(int dx) {
        super.offsetChildrenHorizontal(dx);
    }

    @Override
    public void offsetChildrenVertical(int dy) {
        super.offsetChildrenVertical(dy);
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        super.onAdapterChanged(oldAdapter, newAdapter);
    }

    @Override
    public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> views, int direction, int focusableMode) {
        return super.onAddFocusables(recyclerView, views, direction, focusableMode);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view) {
        super.onDetachedFromWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
    }

    @Nullable
    @Override
    public View onFocusSearchFailed(View focused, int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.onFocusSearchFailed(focused, direction, recycler, state);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityEvent(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(recycler, state, event);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, info);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfoForItem(recycler, state, host, info);
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        return super.onInterceptFocusSearch(focused, direction);
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsAdded(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        super.onItemsChanged(recyclerView);
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        super.onItemsMoved(recyclerView, from, to, itemCount);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount, Object payload) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount, payload);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    @Override
    public boolean onRequestChildFocus(RecyclerView parent, View child, View focused) {
        return super.onRequestChildFocus(parent, child, focused);
    }

    @Override
    public boolean onRequestChildFocus(RecyclerView parent, RecyclerView.State state, View child, View focused) {
        return super.onRequestChildFocus(parent, state, child, focused);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    @Override
    public boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int action, Bundle args) {
        return super.performAccessibilityAction(recycler, state, action, args);
    }

    @Override
    public boolean performAccessibilityActionForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, int action, Bundle args) {
        return super.performAccessibilityActionForItem(recycler, state, view, action, args);
    }

    @Override
    public void postOnAnimation(Runnable action) {
        super.postOnAnimation(action);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
    }

    @Override
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        super.removeAndRecycleAllViews(recycler);
    }

    @Override
    public void removeAndRecycleView(View child, RecyclerView.Recycler recycler) {
        super.removeAndRecycleView(child, recycler);
    }

    @Override
    public void removeAndRecycleViewAt(int index, RecyclerView.Recycler recycler) {
        super.removeAndRecycleViewAt(index, recycler);
    }

    @Override
    public boolean removeCallbacks(Runnable action) {
        return super.removeCallbacks(action);
    }

    @Override
    public void removeDetachedView(View child) {
        super.removeDetachedView(child);
    }

    @Override
    public void removeView(View child) {
        super.removeView(child);
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
    }

    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
        return super.requestChildRectangleOnScreen(parent, child, rect, immediate);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    @Override
    public void requestSimpleAnimationsInNextLayout() {
        super.requestSimpleAnimationsInNextLayout();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public void setMeasuredDimension(int widthSize, int heightSize) {
        super.setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }

    @Override
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        super.startSmoothScroll(smoothScroller);
    }

    @Override
    public void stopIgnoringView(View view) {
        super.stopIgnoringView(view);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return super.supportsPredictiveItemAnimations();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
