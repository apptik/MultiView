package io.apptik.widget.multiview.decorations;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.AttributeSet;


public class GridSpacingDecoration extends ItemDecoration {
    public final static int DEFAULT_PADDING = 8;
    private int mPaddingPx = 8;
    private int mPaddingEdgesPx = 16;

    public GridSpacingDecoration(Context ctx) {
        final Resources resources = ctx.getResources();
        mPaddingPx = (int) resources.getDimension(R.dimen.paddingItemDecorationDefault);
        mPaddingEdgesPx = (int) resources.getDimension(R.dimen.paddingItemDecorationEdge);
    }
    public GridSpacingDecoration(int verticalSpacing, int horizontalSpacing) {
        mPaddingPx = verticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {

    }
}
