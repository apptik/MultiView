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

package io.apptik.multiview.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.djodjo.json.JsonArray;

import java.util.Random;

import io.apptik.multiview.R;
import io.apptik.multiview.view.TouchImageView;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class BasicImageRecyclerAdapter extends RecyclerView.Adapter<BasicImageRecyclerAdapter.ViewHolder> {
    private static final String TAG = "BasicMixedAdapter";
    private JsonArray jarr;
    Cursor c;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TouchImageView img1;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            img1 = (TouchImageView)v.findViewById(R.id.img1);
        }

    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public BasicImageRecyclerAdapter(JsonArray jsonArray, Context ctx) {
        jarr = jsonArray;
        c = ctx.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_image, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Uri imageUri = null;
        if (c != null) {
            int count = c.getCount();
            if(position>=count) {
                Random rand = new Random();
                position = rand.nextInt(count);
            }
            if (c.moveToPosition(position)) {
                long id = c.getLong(c.getColumnIndex(MediaStore.Images.Media._ID));
                imageUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);
                Log.d("image", imageUri.toString());
            }

        }

        Log.d(TAG, "Element " + position + " set.");
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        if (imageUri != null) {
            //viewHolder.img1.setImageBitmap(BitmapLruCache.get().getBitmap(imageUri, viewHolder.img1.getContext(), viewHolder.img1.getWidth(), viewHolder.img1.getHeight()));
            Glide.with(viewHolder.img1.getContext()).load(imageUri).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    viewHolder.img1.setImageBitmap(resource);
                    viewHolder.img1.setZoom(1f);
                }
            });

        }


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
