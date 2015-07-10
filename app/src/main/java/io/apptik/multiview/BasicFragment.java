package io.apptik.multiview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.multiview.adapter.BasicRecyclerAdapter;
import io.apptik.multiview.mock.MockData;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {


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
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        BasicRecyclerAdapter recyclerAdapter = new BasicRecyclerAdapter(MockData.getMockJsonArray(333, 500));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.basic, menu);

    }

}
