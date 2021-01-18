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

import java.util.Collection;
import java.util.List;

/**
 * The class that is responsible for keeping track of route instances
 */
public class Route {

    private String routeId;
    private String agencyId;
    private String routeShortName;
    private String routeLongName;
    private String routeDesc;
    private int routeType;
    private String routeUrl;
    private String routeColor;
    private String routeTextColor;
    private Collection<Trip> trips;

    /**
     * The constructor for Route
     *
     * @param routeId        the id for the route instance
     * @param agencyId       agency for the specified route.
     * @param routeShortName Short name of a route.
     * @param routeLongName  Full name of a route.
     * @param routeDesc      Description of a route that provides useful, quality information.
     * @param routeType      Indicates the type of transportation used on a route.
     * @param routeUrl       URL of a web page about the particular route.
     * @param routeColor     Route color designation that matches public facing material.
     * @param routeTextColor Legible color to use for text drawn against a background of route_color
     */
    public Route(String routeId, String agencyId,
                 String routeShortName, String routeLongName, String routeDesc,
                 int routeType, String routeUrl, String routeColor, String routeTextColor) {

        this.routeId = routeId; //required
        this.agencyId = agencyId;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.routeDesc = routeDesc;
        this.routeType = routeType;
        this.routeUrl = routeUrl;
        this.routeColor = routeColor; //required
        this.routeTextColor = routeTextColor;
    }

    /**
     * The getter for routeId
     *
     * @return the id for the route instance
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * The getter for trips
     *
     * @return the list of trips that belong to this route instance
     */
    public Collection<Trip> getTrips() {
        return trips;
    }

    /**
     * Getter for agencyId
     *
     * @return Agency for the specified route.
     */
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Getter for routeType
     *
     * @return Indicates the type of transportation used on a route.
     */
    public int getRouteType() {
        return routeType;
    }

    /**
     * Getter for routeColor
     *
     * @return Route color designation that matches public facing material.
     */
    public String getRouteColor() {
        return routeColor;
    }

    /**
     * Getter for routeDesc
     *
     * @return Description of a route that provides useful, quality information.
     */
    public String getRouteDesc() {
        return routeDesc;
    }

    /**
     * Getter for routeLongName
     *
     * @return Full name of a route.
     */
    public String getRouteLongName() {
        return routeLongName;
    }

    /**
     * Getter for routeShortName
     *
     * @return Short name of a route.
     */
    public String getRouteShortName() {
        return routeShortName;
    }

    /**
     * Getter for routeTextColor
     *
     * @return Legible color to use for text drawn against a background of route_color.
     */
    public String getRouteTextColor() {
        return routeTextColor;
    }

    /**
     * Getter for routeUrl
     *
     * @return URL of a web page about the particular route.
     */
    public String getRouteUrl() {
        return routeUrl;
    }

    /**
     * Setter for agencyId
     *
     * @param agencyId Agency for the specified route.
     */
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * Setter for routeColor
     *
     * @param routeColor Route color designation that matches public facing material.
     */
    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    /**
     * Setter for routeDesc
     *
     * @param routeDesc Description of a route that provides useful, quality information.
     */
    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    /**
     * Setter for routeLongName
     *
     * @param routeLongName Full name of a route.
     */
    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    /**
     * Setter for routeShortName
     *
     * @param routeShortName Short name of a route.
     */
    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    /**
     * Setter for routeTextColor
     *
     * @param routeTextColor Route color designation that matches public facing material.
     */
    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    /**
     * Setter for routeType
     *
     * @param routeType Indicates the type of transportation used on a route.
     */
    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    /**
     * setter for routeUrl
     *
     * @param routeUrl URL of a web page about the particular route.
     */
    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    /**
     * The setter for trips
     *
     * @param trips the list of trips that belong to this route instance
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * The setter for routeId
     *
     * @param routeId the id for the route instance
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

}

