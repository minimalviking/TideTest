package com.minimalviking.tidetest.home.map;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.minimalviking.tidetest.data.NearbySearchResponse;
import com.minimalviking.tidetest.data.Result;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarMapFragment extends SupportMapFragment {

	private Map<String, Marker> markersIdsMap = new HashMap<String, Marker>();;

	public static BarMapFragment newInstance() {
		return new BarMapFragment();
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
	public void onMessageEvent(final NearbySearchResponse response) {
		getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap map) {
				final List<MarkerOptions> markerOptionsList = new ArrayList<>();
				final LatLngBounds.Builder builder = new LatLngBounds.Builder();

				for (Result result : response.getResults()) {
					com.minimalviking.tidetest.data.LatLng location = result.getGeometry().getLocation();
					builder.include(new LatLng(location.getLat(), location.getLng()));

					Marker marker = map.addMarker(new MarkerOptions()
						.position(new LatLng(location.getLat(), location.getLng()))
						.title(result.getName())
						.snippet("Distance: " + result.getFormattedDistanceToTheUser()));
					markersIdsMap.put(result.getId(), marker);
				}
				map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
			}
		});

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(final Result result) {
		Marker marker = markersIdsMap.get(result.getId());
		if (marker != null) {
			marker.showInfoWindow();
			getMapAsync(new OnMapReadyCallback() {
				@Override
				public void onMapReady(GoogleMap map) {
					map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng())));
				}
			});
		}
	}
}
