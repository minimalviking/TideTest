package com.minimalviking.tidetest.home.barList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minimalviking.tidetest.R;
import com.minimalviking.tidetest.data.NearbySearchResponse;
import com.minimalviking.tidetest.data.Result;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

public class BarListFragment extends Fragment {

	private ResultsAdapter adapter;

	public BarListFragment() {
	}

	public static BarListFragment newInstance() {
		BarListFragment fragment = new BarListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bar_list, container, false);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bar_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		adapter = new ResultsAdapter(Collections.<Result>emptyList());
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
	public void onMessageEvent(NearbySearchResponse event) {
		adapter.updateItems(event.getResults());
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

}
