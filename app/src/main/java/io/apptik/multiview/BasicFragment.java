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


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {


    RecyclerView recyclerView = null;
    BasicRecyclerAdapter recyclerAdapter;
    BasicMixedRecyclerAdapter recyclerMixedAdapter;

    public BasicFragment() {
        // Required empty public constructor
    }

    public static BasicFragment newInstance() {
        BasicFragment fragment = new BasicFragment();
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        //recyclerView.setAdapter(recyclerMixedAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.basic, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(recyclerView==null ) return false;
        switch (item.getItemId()) {
            case R.id.action_text_only: recyclerView.setAdapter(recyclerAdapter); break;
            case R.id.action_image_only: break;
            case R.id.action_Image_text: recyclerView.setAdapter(recyclerMixedAdapter); break;

            case R.id.action_layout_linear: recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); break;
            case R.id.action_layout_grid: recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2)); break;
            case R.id.action_layout_staggered: recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)); break;
        }
        return true;
    }
}
