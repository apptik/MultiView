package io.apptik.widget.multiview.scalablerecyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class DuplexAdapter extends RecyclerView.Adapter<DuplexAdapter.DuplexHolder>{
    @Override
    public DuplexAdapter.DuplexHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DuplexAdapter.DuplexHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DuplexHolder extends RecyclerView.ViewHolder {
        public DuplexHolder(View itemView) {
            super(itemView);
        }
    }
}
