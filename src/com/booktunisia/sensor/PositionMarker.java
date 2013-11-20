package com.booktunisia.sensor;

import java.util.Calendar;
public final class PositionMarker {

	private long techKey = 0;
	
	private Calendar creationDate = Calendar.getInstance();
	private double longitude;
	private double latitude;

	public PositionMarker() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PositionMarker(long techKey, Calendar creationDate,
			double longitude, double latitude) {
		super();
		this.techKey = techKey;
		this.creationDate = creationDate;
		this.longitude = longitude;
		this.latitude = latitude;

	}
	
	public void setTechKey(long techKey) {
		this.techKey = techKey;
	}
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public final long getTechKey() {
		return techKey;
	}
	public final Calendar getCreationDate() {
		return creationDate;
	}

	public final double getLongitude() {
		return longitude;
	}
	public final double getLatitude() {
		return latitude;
	}

}
