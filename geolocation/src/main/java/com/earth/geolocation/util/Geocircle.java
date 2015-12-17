package com.earth.geolocation.util;

import java.io.Serializable;

public strictfp class Geocircle implements Serializable {

	private static final long serialVersionUID = 1L;

	private Geolocation center;

	private double diameter;

	private double minLatitude;

	private double maxLatitude;

	private double minLongitude;

	private double maxLongitude;

	private double minLatitudeBearing;

	private double maxLatitudeBearing;

	private double minLongitudeBearing;

	private double maxLongitudeBearing;

	private Geolocation[] border;

	public Geocircle() {
		super();
	}

	public Geolocation getCenter() {
		return center;
	}

	public void setCenter(Geolocation center) {
		this.center = center;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getMinLatitude() {
		return minLatitude;
	}

	public void setMinLatitude(double minLatitude) {
		this.minLatitude = minLatitude;
	}

	public double getMaxLatitude() {
		return maxLatitude;
	}

	public void setMaxLatitude(double maxLatitude) {
		this.maxLatitude = maxLatitude;
	}

	public double getMinLongitude() {
		return minLongitude;
	}

	public void setMinLongitude(double minLongitude) {
		this.minLongitude = minLongitude;
	}

	public double getMaxLongitude() {
		return maxLongitude;
	}

	public void setMaxLongitude(double maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

	public double getMinLatitudeBearing() {
		return minLatitudeBearing;
	}

	public void setMinLatitudeBearing(double minLatitudeBearing) {
		this.minLatitudeBearing = minLatitudeBearing;
	}

	public double getMaxLatitudeBearing() {
		return maxLatitudeBearing;
	}

	public void setMaxLatitudeBearing(double maxLatitudeBearing) {
		this.maxLatitudeBearing = maxLatitudeBearing;
	}

	public double getMinLongitudeBearing() {
		return minLongitudeBearing;
	}

	public void setMinLongitudeBearing(double minLongitudeBearing) {
		this.minLongitudeBearing = minLongitudeBearing;
	}

	public double getMaxLongitudeBearing() {
		return maxLongitudeBearing;
	}

	public void setMaxLongitudeBearing(double maxLongitudeBearing) {
		this.maxLongitudeBearing = maxLongitudeBearing;
	}

	public Geolocation[] getBorder() {
		return border;
	}

	public void setBorder(Geolocation[] border) {
		this.border = border;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(maxLatitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxLongitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minLatitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minLongitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geocircle other = (Geocircle) obj;
		if (Double.doubleToLongBits(maxLatitude) != Double.doubleToLongBits(other.maxLatitude))
			return false;
		if (Double.doubleToLongBits(maxLongitude) != Double.doubleToLongBits(other.maxLongitude))
			return false;
		if (Double.doubleToLongBits(minLatitude) != Double.doubleToLongBits(other.minLatitude))
			return false;
		if (Double.doubleToLongBits(minLongitude) != Double.doubleToLongBits(other.minLongitude))
			return false;
		return true;
	}

}
