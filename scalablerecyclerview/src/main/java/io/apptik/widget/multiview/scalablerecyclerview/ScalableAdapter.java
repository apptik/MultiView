package io.apptik.widget.multiview.scalablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class ScalableAdapter<A extends RecyclerView.Adapter> extends RecyclerView.Adapter<ScalableAdapter.ViewHolder> {


    A originalAdapter;

    public ScalableAdapter(A originalAdapter) {
        this.originalAdapter = originalAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ScalableViewGroup scalableViewGroup = new ScalableViewGroup(parent.getContext());
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        scalableViewGroup.setLayoutParams(lp);
        ViewHolder vh = new ViewHolder(originalAdapter.onCreateViewHolder(parent, viewType), scalableViewGroup);
        View childView = vh.originalVH.itemView;
        ViewGroup.LayoutParams lpCh = childView.getLayoutParams();
        lpCh.width = parent.getWidth();
        lpCh.height = parent.getHeight();
        scalableViewGroup.addView(childView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        originalAdapter.bindViewHolder(holder.originalVH, position);
    }

    @Override
    public int getItemCount() {
        return originalAdapter.getItemCount();
    }

    public class ViewHolder<VH extends RecyclerView.ViewHolder> extends RecyclerView.ViewHolder {

        public final VH originalVH;
        public final ScalableViewGroup scalableViewGroup;

        public ViewHolder(VH originViewHolder, ScalableViewGroup scalableViewGroup) {
            super(scalableViewGroup);
            originalVH = originViewHolder;
            this.scalableViewGroup = scalableViewGroup;
        }
    }
}
