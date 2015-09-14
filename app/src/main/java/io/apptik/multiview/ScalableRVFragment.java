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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import io.apptik.multiview.adapter.BasicMixedRecyclerAdapter;
import io.apptik.multiview.adapter.BasicRecyclerAdapter;
import io.apptik.multiview.mock.MockData;
import io.apptik.widget.multiview.animators.FlexiItemAnimator;
import io.apptik.widget.multiview.animators.Providers;
import io.apptik.widget.multiview.layoutmanagers.ScalableGridLayoutManager;
import io.apptik.widget.multiview.scalablerecyclerview.ScalableRecyclerGridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScalableRVFragment extends Fragment {


    RecyclerView recyclerView = null;
    BasicRecyclerAdapter recyclerAdapter;

    BasicMixedRecyclerAdapter recyclerMixedAdapter;
    RecyclerView.ItemAnimator defaultAnimator;
    FlexiItemAnimator customAnimator;

    public ScalableRVFragment() {
        // Required empty public constructor
    }

    public static ScalableRVFragment newInstance() {
        ScalableRVFragment fragment = new ScalableRVFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_scalablerv, container, false);
        recyclerView = (ScalableRecyclerGridView) v.findViewById(R.id.recyclerView);

        recyclerMixedAdapter = new BasicMixedRecyclerAdapter(MockData.getMockJsonArray(333, 500), getActivity().getApplicationContext());

        recyclerView.setAdapter(recyclerMixedAdapter);
        defaultAnimator = new DefaultItemAnimator();
//                new FlexiItemAnimator(
//                        Providers.defaultAddAnimProvider(),
//                        Providers.defaultChangeOldViewAnimProvider(),
//                        Providers.defaultChangeNewViewAnimProvider(),
//                        Providers.defaultMoveAnimProvider(),
//                        Providers.defaultRemoveAnimProvider()
//                );
        customAnimator =
                new FlexiItemAnimator(
                        Providers.teleportAddNewViewAnimProvider(),
                        Providers.teleportChangeOldViewAnimProvider(),
                        Providers.teleportChangeNewViewAnimProvider(),
                        null,
                        null
                );
        defaultAnimator.setChangeDuration(500);
        customAnimator.setChangeDuration(500);
        customAnimator.setIpChangeNew(new LinearInterpolator());
        customAnimator.setIpChangeOld(new LinearInterpolator());

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.scalable_rv, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (recyclerView == null) return false;
        switch (item.getItemId()) {
            case R.id.action_scale_no_anim:
                ((ScalableGridLayoutManager) recyclerView.getLayoutManager())
                        .setAnimateItemChangedOnScaleChange(false);
                break;
            case R.id.action_scale_default_anim:
                ((ScalableGridLayoutManager) recyclerView.getLayoutManager())
                        .setAnimateItemChangedOnScaleChange(true);
                recyclerView.setItemAnimator(defaultAnimator);
                break;
            case R.id.action_scale_custom_anim:
                ((ScalableGridLayoutManager) recyclerView.getLayoutManager())
                        .setAnimateItemChangedOnScaleChange(true);
                recyclerView.setItemAnimator(customAnimator);
                break;

        }
        return true;
    }
}
