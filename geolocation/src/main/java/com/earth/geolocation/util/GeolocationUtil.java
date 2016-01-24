/*
 * Copyright since 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author visruth
 */
package com.earth.geolocation.util;

import com.earth.geolocation.common.DistanceUnit;

/**
 * @author visruth
 *
 */
public strictfp class GeolocationUtil {

	/**
	 * 6371000 metres
	 */
	public static final int RADIUS_OF_EARTH = 6371000;

	private GeolocationUtil() {
		throw new AssertionError();
	}

	private static double toRad(double value) {
		return value * Math.PI / 180;
	}

	/**
	 * calculating distance between two places by Haversine Distance Algorithm.
	 *
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return the distance between two points
	 * 
	 * @author visruth
	 */
	public static double getGelocationDistance(double lat1, double lon1, double lat2, double lon2,
			DistanceUnit distanceUnit) {

		double latDifference = toRad(lat2 - lat1);
		double lonDifference = toRad(lon2 - lon1);
		double a = Math.sin(latDifference / 2) * Math.sin(latDifference / 2) + Math.cos(toRad(lat1))
				* Math.cos(toRad(lat2)) * Math.sin(lonDifference / 2) * Math.sin(lonDifference / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = (RADIUS_OF_EARTH) * c;
		if (distanceUnit == null) {
			return distance;
		}

		return distance / (distanceUnit.getMetres());
	}

	/**
	 * gets the geolocation added by a given distance.
	 * 
	 * @param latitudeFrom
	 *            the latitude
	 * @param longitudeFrom
	 *            the longitude
	 * @param bearing
	 *            the angle to give direction from north pole. takes value from
	 *            0 to 359.
	 * @param distance
	 *            distance to add
	 * @param distanceUnit
	 *            unit of the given distance
	 * @return the {@code Geolocation} added by the given distance.
	 * 
	 * @author visruth
	 */
	public static Geolocation getGeolocationAddedByDistance(double latitudeFrom, double longitudeFrom, double bearing,
			double distance, DistanceUnit distanceUnit) {

		double phi1 = Math.toRadians(latitudeFrom);
		double lambda1 = Math.toRadians(longitudeFrom);

		double R = RADIUS_OF_EARTH / distanceUnit.getMetres();

		double theta = Math.toRadians(bearing);
		double delta = distance / R;

		double phi2 = Math.asin(Math.sin(phi1) * Math.cos(delta) + Math.cos(phi1) * Math.sin(delta) * Math.cos(theta));

		double lambda2 = lambda1
				+ Math.atan2(Math.sin(theta) * Math.sin(delta) * Math.cos(phi1), Math.cos(delta) - Math.sin(phi1) * Math.sin(phi2));

		double latitudeTo = Math.toDegrees(phi2);
		double longitudeTo = (Math.toDegrees(lambda2) + 540) % 360 - 180;

		return new Geolocation(latitudeTo, longitudeTo);
	}

	/**
	 * gets the geocircle added by a given distance.
	 * 
	 * @param latitudeFrom
	 * @param longitudeFrom
	 * @param distance
	 *            distance to add
	 * @param distanceUnit
	 *            unit of the given distance
	 * @return the geocircle added by a given distance.
	 * 
	 * @author visruth
	 */
	public static Geocircle getGeocircleDrawnByAddedDistance(double latitudeFrom, double longitudeFrom, double distance,
			DistanceUnit distanceUnit) {

		double maxLatitude = 0;
		double maxLongitude = 0;

		double maxLatitudeBearing = 0;
		double maxLongitudeBearing = 0;

		Geolocation[] geolocations = new Geolocation[360];

		for (int bearing = 0; bearing < 360; bearing++) {
			Geolocation geolocation = GeolocationUtil.getGeolocationAddedByDistance(latitudeFrom, longitudeFrom,
					bearing, distance, distanceUnit);

			geolocations[bearing] = geolocation;

			if (maxLatitude < geolocation.getLatitude()) {
				maxLatitude = geolocation.getLatitude();
				maxLatitudeBearing = bearing;
			}
			if (maxLongitude < geolocation.getLongitude()) {
				maxLongitude = geolocation.getLongitude();
				maxLongitudeBearing = bearing;
			}

		}

		double minLatitude = maxLatitude;
		double minLongitude = maxLongitude;

		double minLatitudeBearing = maxLatitudeBearing;
		double minLongitudeBearing = maxLongitudeBearing;

		for (int bearing = 0; bearing < 360; bearing++) {
			Geolocation geolocation = geolocations[bearing];

			if (minLatitude > geolocation.getLatitude()) {
				minLatitude = geolocation.getLatitude();
				minLatitudeBearing = bearing;
			}
			if (minLongitude > geolocation.getLongitude()) {
				minLongitude = geolocation.getLongitude();
				minLongitudeBearing = bearing;
			}
		}

		double maxMinLatitudeDiff = maxLatitude - minLatitude;

		double maxMinLongitudeDiff = maxLongitude - minLongitude;

		double averageDiameter = (maxMinLatitudeDiff + maxMinLongitudeDiff) / 2;

		Geocircle geocircle = new Geocircle();

		geocircle.setMinLatitude(minLatitude);
		geocircle.setMaxLatitude(maxLatitude);
		geocircle.setMinLongitude(minLongitude);
		geocircle.setMaxLongitude(maxLongitude);

		geocircle.setMinLatitudeBearing(minLatitudeBearing);
		geocircle.setMaxLatitudeBearing(maxLatitudeBearing);
		geocircle.setMinLongitudeBearing(minLongitudeBearing);
		geocircle.setMaxLongitudeBearing(maxLongitudeBearing);

		geocircle.setCenter(new Geolocation(latitudeFrom, longitudeFrom));
		geocircle.setBorder(geolocations);

		geocircle.setDiameter(averageDiameter);
		return geocircle;
	}

}
