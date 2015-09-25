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

package io.apptik.multiview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.multiview.adapter.BasicImageRecyclerAdapter;
import io.apptik.multiview.adapter.BasicMixedRecyclerAdapter;
import io.apptik.multiview.adapter.BasicRecyclerAdapter;
import io.apptik.multiview.mock.MockData;
import io.apptik.widget.multiview.layoutmanagers.SnapperLinearLayoutManager;
import io.apptik.widget.multiview.layoutmanagers.ViewPagerLayoutManager;
import io.apptik.widget.multiview.layoutmanagers.TouchChildPagerLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutsFragment extends Fragment {


    RecyclerView recyclerView = null;
    BasicRecyclerAdapter recyclerAdapter;
    BasicImageRecyclerAdapter recyclerImageAdapter;
    BasicMixedRecyclerAdapter recyclerMixedAdapter;

    public LayoutsFragment() {
        // Required empty public constructor
    }

    public static LayoutsFragment newInstance() {
        LayoutsFragment fragment = new LayoutsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
         recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
       // recyclerView.setNestedScrollingEnabled(false);

         recyclerAdapter = new BasicRecyclerAdapter(MockData.getMockJsonArray(333, 500));
        recyclerImageAdapter = new BasicImageRecyclerAdapter(MockData.getMockJsonArray(333, 500), getActivity().getApplicationContext());
         recyclerMixedAdapter = new BasicMixedRecyclerAdapter(MockData.getMockJsonArray(333, 500), getActivity().getApplicationContext());

        recyclerView.setLayoutManager(new SnapperLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.layouts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(recyclerView==null ) return false;
        switch (item.getItemId()) {
            case R.id.action_text_only: recyclerView.setAdapter(recyclerAdapter); break;
            case R.id.action_image_only: recyclerView.setAdapter(recyclerImageAdapter); break;
            case R.id.action_Image_text: recyclerView.setAdapter(recyclerMixedAdapter); break;

            case R.id.action_layout_snapper_basic:
                recyclerView.setLayoutManager(new SnapperLinearLayoutManager(getActivity())); break;
            case R.id.action_layout_snapper_single:
                recyclerView.setLayoutManager(new SnapperLinearLayoutManager(getActivity())
                        .withShowOneItemOnly(true));
               break;
            case R.id.action_layout_snapper_pager_v:
                recyclerView.setLayoutManager(new ViewPagerLayoutManager(getActivity()));
                break;
            case R.id.action_layout_snapper_pager_h:
                recyclerView.setLayoutManager(new TouchChildPagerLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                break;
        }
        return true;
    }
}
