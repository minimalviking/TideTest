package com.minimalviking.tidetest.data;

import android.location.Location;

public class LatLng {
	final double lat, lng;

	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public LatLng(Location location) {
		this.lat = location.getLatitude();
		this.lng = location.getLongitude();
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return Double.toString(lat) + "," + Double.toString(lng);
	}
}
