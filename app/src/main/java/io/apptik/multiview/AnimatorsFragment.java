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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.multiview.adapter.BasicMixedRecyclerAdapter;
import io.apptik.multiview.adapter.BasicRecyclerAdapter;
import io.apptik.multiview.mock.MockData;
import io.apptik.widget.multiview.animators.AnimatorProvider;
import io.apptik.widget.multiview.animators.FlexiItemAnimator;
import io.apptik.widget.multiview.animators.Providers;


public class AnimatorsFragment extends Fragment {


    RecyclerView recyclerView = null;
    BasicRecyclerAdapter recyclerAdapter;
    BasicMixedRecyclerAdapter recyclerMixedAdapter;

    private AnimatorProvider addProvider = Providers.defaultAddAnimProvider();
    private AnimatorProvider removeProvider = Providers.defaultRemoveAnimProvider();
    private AnimatorProvider moveProvider = Providers.defaultMoveAnimProvider();
    private AnimatorProvider changeOldVHProvider = Providers.defaultChangeOldViewAnimProvider();
    private AnimatorProvider changeNewVHProvider = Providers.defaultChangeNewViewAnimProvider();

    public AnimatorsFragment() {
        // Required empty public constructor
    }

    public static AnimatorsFragment newInstance() {
        AnimatorsFragment fragment = new AnimatorsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        recyclerAdapter = new BasicRecyclerAdapter(MockData.getMockJsonArray(333, 500));
        recyclerMixedAdapter = new BasicMixedRecyclerAdapter(MockData.getMockJsonArray(333, 500), getActivity().getApplicationContext());
        //recyclerAdapter.setHasStableIds(true);
        recyclerMixedAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        //recyclerView.setAdapter(recyclerMixedAdapter);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        inflater.inflate(R.menu.animators, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (recyclerView == null) return false;
        switch (item.getItemId()) {
            case R.id.action_text_only:
                recyclerView.setAdapter(recyclerAdapter);
                break;
            case R.id.action_image_only:
                break;
            case R.id.action_Image_text:
                recyclerView.setAdapter(recyclerMixedAdapter);
                break;

            case R.id.action_layout_linear:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case R.id.action_layout_grid:
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                break;
            case R.id.action_layout_staggered:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;

            case R.id.action_rv_add:
                resetItemAnimator();
                recyclerAdapter.jarr.put(1, MockData.getRandomEntry(1, 500));
                recyclerView.getAdapter().notifyItemInserted(1);
                break;
            case R.id.action_rv_remove:
                resetItemAnimator();
                recyclerAdapter.jarr.remove(1);
                recyclerView.getAdapter().notifyItemRemoved(1);
                break;
            case R.id.action_rv_move:
                resetItemAnimator();
                recyclerAdapter.jarr.put(2, recyclerAdapter.jarr.get(1));
                recyclerAdapter.jarr.put(1, MockData.getRandomEntry(1, 500));
                recyclerView.getAdapter().notifyItemMoved(1, 2);
                break;
            case R.id.action_rv_change:
                resetItemAnimator();
                recyclerAdapter.jarr.put(1, MockData.getRandomEntry(1, 500));
                recyclerView.getAdapter().notifyItemChanged(1);
                break;

            case R.id.action_add_animation_default:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    addProvider = Providers.defaultAddAnimProvider();
                }
                break;
            case R.id.action_add_animation_garagedoor:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    addProvider = Providers.garageDoorAddProvider();
                }
                break;

            case R.id.action_remove_animation_default:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    removeProvider = Providers.defaultRemoveAnimProvider();
                }
                break;
            case R.id.action_remove_animation_garagedoor:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    removeProvider = Providers.garageDoorRemoveProvider();
                }
                break;
            case R.id.action_add_animation_slide_to_left:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    addProvider = Providers.slideInRightProvider();
                }
                break;
            case R.id.action_remove_animation_slide_to_left:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    removeProvider = Providers.slideOutLeftProvider();
                }
                break;
            case R.id.action_add_animation_slide_to_right:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    addProvider = Providers.slideInLeftProvider();
                }
                break;
            case R.id.action_remove_animation_slide_to_right:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    removeProvider = Providers.slideOutRightProvider();
                }
                break;

            case R.id.action_add_animation_slide_to_top:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    addProvider = Providers.slideInBottomProvider();
                }
                break;
            case R.id.action_remove_animation_slide_to_top:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    removeProvider = Providers.slideOutTopProvider();
                }
                break;
            case R.id.action_add_animation_slide_to_bottom:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    addProvider = Providers.slideInTopProvider();
                }
                break;
            case R.id.action_remove_animation_slide_to_bottom:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    removeProvider = Providers.slideOutBottomProvider();
                }
                break;

        }
        return true;
    }

    private void resetItemAnimator() {
        RecyclerView.ItemAnimator itemAnimator = new FlexiItemAnimator(
                addProvider,
                changeOldVHProvider,
                changeNewVHProvider,
                moveProvider,
                removeProvider);
        itemAnimator.setMoveDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        itemAnimator.setChangeDuration(1000);
        itemAnimator.setAddDuration(1000);
        itemAnimator.setSupportsChangeAnimations(true);
        recyclerView.setItemAnimator(itemAnimator);
    }
}
