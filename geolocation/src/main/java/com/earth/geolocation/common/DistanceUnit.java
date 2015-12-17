package com.earth.geolocation.common;

/**
 * @author visruth
 *
 */
public enum DistanceUnit {

    KM(1000), METER(1);

    private float metres;

    private DistanceUnit(float metres) {
        this.metres = metres;
    }

    public float getMetres() {
        return metres;
    }
}
