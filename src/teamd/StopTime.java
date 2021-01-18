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

/**
 * Class that is responsible for keeping track of stopTime instances
 */
public class StopTime {

    private String tripId;
    private String arrivalTime;
    private String departureTime;
    private String stopId;
    private int stopSequence;
    private String stopHeadSign;
    private int pickupType;
    private int dropOffType;
    private double shapeDistTraveled;


    /**
     * The StopTime constructor
     *
     * @param tripId        the identification for the trip this stopTime belongs to
     * @param arrivalTime   the time that a trip arrives at the stop
     * @param departureTime the time that a trip departs the stop
     * @param stopId        Identifies the serviced stop
     * @param stopSequence  Order of stops for a particular trip.
     * @param stopHeadSign  Text that appears on signage
     *                      identifying the trip's destination to riders.
     * @param pickupType    Indicates pickup method.
     * @param dropOffType   the numerical value for the order of stops on a trip
     */
    public StopTime(String tripId, String arrivalTime, String departureTime,
                    String stopId, int stopSequence,
                    String stopHeadSign, int pickupType, int dropOffType) {
        this.tripId = tripId; //Required
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopId = stopId; //Required
        this.stopSequence = stopSequence; //Required
        this.stopHeadSign = stopHeadSign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;

    }

    /**
     * The getter for arrivalTime
     *
     * @return the time that a trip arrives at the stop
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * The getter for departure time
     *
     * @return the time that a trip deoarts the stop
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * The getter for shapeDistTraveled
     *
     * @return the distance that is covered by the trip from the first stop to this instance
     */
    public double getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    /**
     * The getter for stopSequence
     *
     * @return the numerical value for the order of stops on a trip
     */
    public int getStopSequence() {
        return stopSequence;
    }

    /**
     * The getter for tripId
     *
     * @return the identification for the trip this stopTime belongs to
     */
    public String getTripId() {
        return tripId;
    }

    /**
     * The getter for dropOffType
     *
     * @return Indicates drop off method.
     */
    public int getDropOffType() {
        return dropOffType;
    }

    /**
     * The getter for pickupType
     *
     * @return Indicates pickup method.
     */
    public int getPickupType() {
        return pickupType;
    }

    /**
     * The getter for stopId
     *
     * @return Identifies the serviced stop.
     */
    public String getStopId() {
        return stopId;
    }

    /**
     * The getter for stopHeadSign
     *
     * @return Text that appears on signage identifying the trip's destination to riders.
     */
    public String getStopHeadSign() {
        return stopHeadSign;
    }

    /**
     * The setter for dropOffType
     *
     * @param dropOffType Indicates drop off method
     */
    public void setDropOffType(int dropOffType) {
        this.dropOffType = dropOffType;
    }

    /**
     * setter for pickupType
     *
     * @param pickupType Indicates pickup method.
     */
    public void setPickupType(int pickupType) {
        this.pickupType = pickupType;
    }

    /**
     * The setter for stopHeadSign
     *
     * @param stopHeadSign Text that
     *                     appears on signage identifying the trip's destination to riders.
     */
    public void setStopHeadSign(String stopHeadSign) {
        this.stopHeadSign = stopHeadSign;
    }

    /**
     * The setter for stopId
     *
     * @param stopId Identifies the serviced stop.
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }


    /**
     * The setter for arrival time
     *
     * @param arrivalTime the time that a trip arrives at the stop
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * The setter for departureTime
     *
     * @param departureTime the time that a trip deoarts the stop
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * The setter for shapeDistTraveled
     *
     * @param shapeDistTraveled the distance that
     *                          is covered by the trip from the first stop to this instance
     */
    public void setShapeDistTraveled(int shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }


    /**
     * The setter for stopSequence
     *
     * @param stopSequence the numerical value for the order of stops on a trip
     */
    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    /**
     * The setter for tripId
     *
     * @param tripId the identification for the trip this stopTime belongs to
     */
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}