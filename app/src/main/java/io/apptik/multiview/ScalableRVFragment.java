package io.apptik.multiview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
public class ScalableRVFragment extends Fragment {


    RecyclerView recyclerView = null;
    BasicRecyclerAdapter recyclerAdapter;

    BasicMixedRecyclerAdapter recyclerMixedAdapter;

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
         recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        recyclerAdapter = new BasicRecyclerAdapter(MockData.getMockJsonArray(333, 500));

        recyclerMixedAdapter = new BasicMixedRecyclerAdapter(MockData.getMockJsonArray(333, 500), getActivity().getApplicationContext());

        recyclerView.setAdapter(recyclerMixedAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.scalable_rv, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(recyclerView==null ) return false;
        switch (item.getItemId()) {
            case R.id.action_text_only: recyclerView.setAdapter(recyclerAdapter); break;
            case R.id.action_image_only: break;
            case R.id.action_Image_text: recyclerView.setAdapter(recyclerMixedAdapter); break;

//            case R.id.action_layout_linear: recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); break;
//            case R.id.action_layout_grid: recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3)); break;
//            case R.id.action_layout_staggered: recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)); break;
        }
        return true;
    }
}
