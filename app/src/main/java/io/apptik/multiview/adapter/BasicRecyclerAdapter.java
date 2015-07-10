package io.apptik.multiview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.djodjo.json.JsonArray;

import io.apptik.multiview.R;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class BasicRecyclerAdapter extends RecyclerView.Adapter<BasicRecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private JsonArray jarr;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txt1;
        public final TextView txt2;
        public final TextView txt3;
        public final TextView txt4;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
             txt1 = (TextView)v.findViewById(R.id.txt1);
            txt2 = (TextView)v.findViewById(R.id.txt2);
            txt3 = (TextView)v.findViewById(R.id.txt3);
            txt4 = (TextView)v.findViewById(R.id.txt4);
          //  textView = (TextView) v.findViewById(R.id.textView);
        }

    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public BasicRecyclerAdapter(JsonArray jsonArray) {
        jarr = jsonArray;

    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_card, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.txt1.setText("title");
        viewHolder.txt2.setText("pos: " + position);



    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return jarr.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position%2;
    }
}
