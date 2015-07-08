/*
* Copyright (C) 2014 The Android Open Source Project
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

package io.apptik.multiview.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import org.djodjo.json.JsonArray;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class BasicRecyclerAdapter extends RecyclerView.Adapter<BasicRecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    //view anim
    protected static final long ANIM_DEFAULT_SPEED = 1000L;

    protected Interpolator interpolator;
    protected ImageListFragment.ListScrollListener scrollListener;
    protected SparseBooleanArray positionsMapper;
    protected int lastPosition;
    protected int height;
    // protected EstateResultScrollListener scrollListener;

    protected double speed;
    protected long animDuration;
    //

    private static final int ITEM_PLACE = 0;
    private static final int ITEM_TOWN = 1;
    private static final int ITEM_MUSEUM = 2;

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
    public BasicRecyclerAdapter(JsonArray jsonArray, Activity activity, ImageListFragment.ListScrollListener scrollListener) {
        jarr = jsonArray;
        this.scrollListener = scrollListener;
        animDuration = ANIM_DEFAULT_SPEED;
        lastPosition = -1;
        positionsMapper = new SparseBooleanArray(jarr.size());

        interpolator = new DecelerateInterpolator();

        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_card, viewGroup, false);

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
