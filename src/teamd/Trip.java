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
 * Data structure class that is responsible for storing instances of  Trips
 */
public class Trip {
    private String routeId;
    private String serviceId;
    private String tripId;
    private String tripHeadSign;
    private int directionId;
    private String blockId;
    private String shapeId;


    /**
     * Constructor method for Trip
     *
     * @param routeId      the routeId the trip instance belongs to
     * @param serviceId    the serviceId the trip belongs to
     * @param tripId       the identification for the given trip
     * @param tripHeadSign text that appears on signage to id a trip
     * @param directionId  indicates travel direction, 0 = outbound, 1 = inbound
     * @param blockId      identifies the block that the trip belongs to
     * @param shapeId      Identifies a geospatial shape that
     *                     describes the vehicle travel path for a trip.
     */
    public Trip(String routeId, String serviceId, String tripId,
                String tripHeadSign, int directionId, String blockId,
                String shapeId) {
        this.routeId = routeId; //Required
        this.serviceId = serviceId;
        this.tripId = tripId; //Required
        this.tripHeadSign = tripHeadSign;
        this.directionId = directionId;
        this.blockId = blockId;
        this.shapeId = shapeId;
    }

    /**
     * The setter for tripId
     *
     * @param tripId the identification for the given trip
     */
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     * The getter for tripId
     *
     * @return tripId the identification for the given trip
     */
    public String getTripId() {
        return tripId;
    }

    /**
     * The getter for routeId
     *
     * @return routeId the identification for the given trip
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * The getter for serviceId
     *
     * @return serviceId the serviceId the trip belongs to
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * The getter for directionId
     *
     * @return Indicates the direction of travel for a trip.
     */
    public int getDirectionId() {
        return directionId;
    }

    /**
     * The getter for shapeId
     *
     * @return Identifies a geospatial shape that describes the vehicle travel path for a trip.
     */
    public String getShapeId() {
        return shapeId;
    }

    /**
     * The getter for tripHeadSign
     *
     * @return Text that appears on signage identifying the trip's destination to riders.
     */
    public String getTripHeadSign() {
        return tripHeadSign;
    }

    /**
     * setter for blockId
     *
     * @param blockId Identifies the block to which the trip belongs.
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * setter for directionId
     *
     * @param directionId indicates the direction of travel for a trip.
     */
    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    /**
     * setter for shapeId
     *
     * @param shapeId Identifies a
     *                geospatial shape that describes the vehicle travel path for a trip.
     */
    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    /**
     * The setter for tripHeadSign
     *
     * @param tripHeadSign Text
     *                     that appears on signage identifying the trip's destination to riders.
     */
    public void setTripHeadSign(String tripHeadSign) {
        this.tripHeadSign = tripHeadSign;
    }


    /**
     * The getter for blockId
     *
     * @return identifies the block to which the trip belongs
     */
    public String getBlockId() {
        return blockId;
    }

    /**
     * The setter for routeId
     *
     * @param routeId the routeId the trip instance belongs to
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    /**
     * The setter for serviceId
     *
     * @param serviceId the serviceId the trip instance belongs to
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }


}