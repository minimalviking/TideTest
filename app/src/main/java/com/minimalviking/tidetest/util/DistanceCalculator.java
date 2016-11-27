package com.minimalviking.tidetest.util;

import android.location.Location;

import com.minimalviking.tidetest.data.LatLng;

public class DistanceCalculator {
	public static Float calculate(LatLng latLng, Location location) {
		float [] dist = new float[1];
		Location.distanceBetween(latLng.getLat(), latLng.getLng(), location.getLatitude(), location.getLongitude(), dist);
		return dist[0];
	}
}
