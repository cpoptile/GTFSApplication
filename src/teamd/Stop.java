/*
 ******************************************************************
 *
 * Copyright 2020 Nigel Nelson, Paulick Jonathan, Claudia Poptile, Nicholas, Rossignol
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
 *
 ******************************************************************
 *
 * Course: SE2030 - 021
 * Fall 2020
 * Lab 10 - Presentations!
 * Name: Nigel Nelson, Claudia Poptile, Jonathan Paulick
 * Created: 11/10/2020
 */
package teamd;

import java.util.InputMismatchException;

/**
 * The class that is responsible for keeping track of an instance(s) of a stop
 */
public class Stop {

    private String stopId;
    private double stopLat;
    private double stopLon;
    private String stopDesc;
    private String stopName;

    /**
     * The constructor for an instance of a stop
     *
     * @param stopId  the identification for an instance of a stop
     * @param stopLat  the latitude of a stop
     * @param stopLon  the longitude of a stop
     * @param stopDesc the description of the stop
     * @param stopName the name of the stop
     */
    public Stop(String stopId, String stopName, String stopDesc, double stopLat, double stopLon) {
        this.stopId = stopId; //Required
        this.stopName = stopName;
        this.stopDesc = stopDesc;
        setStopLat(stopLat); //Required
        setStopLon(stopLon); //Required

    }

    /**
     * The getter for stopLat
     *
     * @return the latitude of a stop
     */
    public double getStopLat() {
        return stopLat;
    }

    /**
     * The getter for stopLon
     *
     * @return the longitude of a stop
     */
    public double getStopLon() {
        return stopLon;
    }

    /**
     * The getter for stopId
     *
     * @return the identification for an instance of a stop
     */
    public String getStopId() {
        return stopId;
    }

    /**
     * The getter for stopDesc
     *
     * @return the description of the stop
     */
    public String getStopDesc() {
        return stopDesc;
    }

    /**
     * The getter for stopName
     *
     * @return the name of the stop
     */
    public String getStopName() {
        return stopName;
    }

    /**
     * The setter for stopName
     *
     * @param stopName the name of the stop
     */
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    /**
     * The setter for stopDesc
     *
     * @param stopDesc the description of the stop
     */
    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    /**
     * The setter for stopId
     *
     * @param stopId the identification for an instance of a stop
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    /**
     * The setter for stopLat
     *
     * @param stopLat the latitude of a stop
     */
    public void setStopLat(double stopLat) {
        if (stopLat > -90 && stopLat < 90) {
            this.stopLat = stopLat;
        } else {
            throw new InputMismatchException("Latitude is not valid");
        }
    }

    /**
     * The setter for stopLon
     *
     * @param stopLon the longitude of a stop
     */
    public void setStopLon(double stopLon) {
        if (stopLon > -180 && stopLon < 180) {
            this.stopLon = stopLon;
        } else {
            throw new InputMismatchException("Longitude is not valid");
        }
    }
}