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

package io.apptik.widget.multiview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;


public class WrapperAdapter<A extends RecyclerView.Adapter, VG extends ViewGroup> extends RecyclerView.Adapter<WrapperAdapter.ViewHolder> {


    A originalAdapter;
    Class<VG> vgClass;


    public WrapperAdapter(A originalAdapter, Class<VG> vgClass) {
        this.originalAdapter = originalAdapter;
        this.vgClass = vgClass;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        VG viewGroup = null;
        try {
            viewGroup = vgClass.getConstructor(Context.class).newInstance(parent.getContext());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if(viewGroup==null) {
            throw new IllegalStateException("viewGroup container was not initialized");
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        viewGroup.setLayoutParams(lp);
        ViewHolder vh = new ViewHolder(originalAdapter.onCreateViewHolder(parent, viewType), viewGroup);
        View childView = vh.originalVH.itemView;
        ViewGroup.LayoutParams lpCh = childView.getLayoutParams();
        lpCh.width = parent.getWidth();
        lpCh.height = parent.getHeight();
        viewGroup.addView(childView);
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
        public final VG viewGroup;

        public ViewHolder(VH originViewHolder, VG viewGroup) {
            super(viewGroup);
            originalVH = originViewHolder;
            this.viewGroup = viewGroup;
        }
    }
}
