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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Class developed to handle GTFS data and act as a subject
 */
public class GTFSData implements Subject {
    private Collection<Observer> observers;
    private Collection<Route> routes;
    private Collection<Trip> trips;
    private Collection<StopTime> stopTimes;
    private Map<String, Stop> stops;
    private List<String> searchResults;
    private DataVisualizer dataVisualizer;

    private String[] gtfsFiles = new String[] {
            "stops.txt", "routes.txt", "trips.txt", "stop_times.txt"};

    /**
     * Default Constructor
     * Sets all gtfs data
     *
     * @param path Is the path of the directory
     * @param dataVisualizer the dataVisualizer associated with this given instance
     * @throws IOException              If the file is invalid
     * @throws IllegalArgumentException If an argument is invalid
     * @throws InputMismatchException   If an element is missing
     */
    public GTFSData(Path path, DataVisualizer dataVisualizer, MapView mapViewer) throws IOException,
            IllegalArgumentException, InputMismatchException {
        stops = new HashMap<String, Stop>();
        routes = new ArrayList<Route>();
        trips = new ArrayList<Trip>();
        stopTimes = new ArrayList<StopTime>();
        observers = new ArrayList<>();
        searchResults = new ArrayList<>();
        attach(dataVisualizer);
        attach(mapViewer);

        for (String s : gtfsFiles) {
            set(path.toString() + File.separator + s);
        }
        notifyObservers();
    }

    /**
     * Sets all vars based off path
     *
     * @param path Is the path of the directory
     * @throws IOException              If the file is invalid
     * @throws IllegalArgumentException If an argument is invalid
     * @throws InputMismatchException   If an element is missing
     */
    public void set(String path) throws IOException,
            IllegalArgumentException, InputMismatchException {
        try (Scanner scanner = new Scanner(new FileReader(path))) {
            if (!scanner.hasNextLine()) {
                throw new InputMismatchException("Empty File");
            }
            String[] header = scanner.nextLine().split(",");
            int headerLength = header.length;
            if (!scanner.hasNextLine()) {
                throw new InputMismatchException("File contains no data");
            }
            while (scanner.hasNextLine()) {
                String t = scanner.nextLine();

                String[] dataFields = t.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);

                if (dataFields.length != headerLength){
                    throw new InputMismatchException("Provided file is missing specified fields");
                }

                if (path.endsWith("stops.txt")) {
                    setStops(dataFields, header);
                } else if (path.endsWith("routes.txt")) {
                    setRoutes(dataFields, header);
                } else if (path.endsWith("trips.txt")) {
                    setTrips(dataFields, header);
                } else if (path.endsWith("stop_times.txt")) {
                    setStopTimes(dataFields, header);
                }
            }
        } catch (IOException e) {
            throw new IOException("The file " + path.toString() + " was not found");
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Missing or bad value in "
                    + path.toString());
        }
    }

    /**
     * Method to turn a string into a double
     *
     * @param string is the string to cast
     * @return The valid double
     * @throws InputMismatchException If the string can't be cast to a double
     */
    private double stringToDouble(String string) throws InputMismatchException {
        double toReturn;
        try {
            toReturn = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            throw new InputMismatchException(string);
        }
        return toReturn;
    }

    /**
     * Method to turn a string into a int
     *
     * @param string is the string to cast
     * @return The valid int
     * @throws InputMismatchException If the string can't be cast to an int
     */
    private int stringToInt(String string) throws InputMismatchException {
        int toReturn;
        try {
            toReturn = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new InputMismatchException(string);
        }
        return toReturn;
    }

    /**
     * Creates a list of stops
     *
     * @param dataFields Is the line of stop data
     * @param header    Is the list of all options
     * @throws IOException              If the file is invalid
     * @throws IllegalArgumentException If an argument is invalid
     * @throws InputMismatchException   If an element is missing
     */
    public void setStops(String[] dataFields, String[] header) throws IOException,
            IllegalArgumentException, InputMismatchException {
        Map<String, String> stopVars = new HashMap<String, String>() {
            {
                put("stop_id", "");
                put("stop_code", "");
                put("stop_name", "");
                put("stop_desc", "");
                put("stop_lat", "");
                put("stop_lon", "");
                put("zone_id", "");
                put("stop_url", "");
                put("location_type", ""); //default 0
                put("parent_station", "");
                put("stop_timezone", "");
                put("wheelchair_boarding", ""); //default 0
                put("level_id", "");
                put("platform_code", "");
            }
        };

        mapLoop(dataFields, header, stopVars);

        stops.put(validateString(stopVars.get("stop_id")),
                new Stop(validateString(stopVars.get("stop_id")),
                        stopVars.get("stop_name"), stopVars.get("stop_desc"),
                        stringToDouble(stopVars.get("stop_lat")),
                        stringToDouble(stopVars.get("stop_lon"))));
    }

    /**
     * Loops through a map to ensure order doesn't matter
     *
     * @param dataFields is the scanner used to separate the elements
     * @param header    specifies what elements are in the body
     * @param varMap    is all possible vars
     */
    private void mapLoop(String[] dataFields, String[] header, Map<String, String> varMap) {
        int x = 0;
        for (String s : header) {

            try {
                if (s.endsWith("desc")) {
                    Scanner scanner = new Scanner(dataFields[x]);
                    scanner.useDelimiter(",[0-9]");

                    String desc = scanner.next();
                    if (desc.equals(",")) {
                        desc = "";
                    }
                    varMap.put(s, desc);
                    scanner.close();
                } else {
                    varMap.put(s, dataFields[x]);
                }
            } catch (NoSuchElementException e) {
                varMap.put(s, "");
            }
            x++;
        }
    }


    /**
     * Creates a list of routes
     *
     * @param dataFields Is the line of route data
     * @param header    Is the list of all options
     * @throws IOException              If the file is invalid
     * @throws IllegalArgumentException If an argument is invalid
     * @throws InputMismatchException   If an element is missing
     */
    public void setRoutes(String[] dataFields, String[] header) throws IOException,
            IllegalArgumentException, InputMismatchException {
        Map<String, String> routeVars = new HashMap<String, String>() {
            {
                put("route_id", "");
                put("agency_id", "");
                put("route_short_name", "");
                put("route_long_name", "");
                put("route_desc", "");
                put("route_type", ""); //default 3
                put("route_url", "");
                put("route_color", ""); // default FFFFFF
                put("route_text_color", ""); //default 000000
                put("route_sort_order", "");
                put("continuous_pickup", ""); //default 1
                put("continuous_drop_off", ""); //default 1
            }
        };

        mapLoop(dataFields, header, routeVars);


        routes.add(new Route(validateString(routeVars.get("route_id")),
                routeVars.get("agency_id"), routeVars.get("route_short_name"),
                routeVars.get("route_long_name"), routeVars.get("route_desc"),
                stringToInt(setDefault(
                        routeVars.get("route_type"), "3")),
                routeVars.get("route_url"),
                validateString(routeVars.get("route_color")),
                setDefault(routeVars.get("route_text_color"), "000000")));
    }

    /**
     * Creates a list of trips
     *
     * @param dataFields Is the line of trip data
     * @param header    Is the list of all options
     * @throws IOException              If the file is invalid
     * @throws IllegalArgumentException If an argument is invalid
     * @throws InputMismatchException   If an element is missing
     */
    public void setTrips(String[] dataFields, String[] header) throws IOException,
            IllegalArgumentException, InputMismatchException {
        Map<String, String> tripVars = new HashMap<String, String>() {
            {
                put("route_id", "");
                put("service_id", "");
                put("trip_id", "");
                put("trip_headsign", "");
                put("trip_short_name", "");
                put("direction_id", ""); //default 0
                put("block_id", "");
                put("shape_id", "");
                put("wheelchair_accessible", ""); //default 0
                put("bikes_allowed", ""); //default 0
            }
        };

        mapLoop(dataFields, header, tripVars);

        trips.add(new Trip(validateString(tripVars.get("route_id")),
                tripVars.get("service_id"), tripVars.get("trip_id"),
                tripVars.get("trip_headsign"),
                stringToInt(setDefault(
                        tripVars.get("direction_id"), "0")),
                tripVars.get("block_id"), tripVars.get("shape_id")));
    }

    /**
     * Creates a list of stopTimes
     *
     * @param dataFields Is the line of stopTimes data
     * @param header    Is the list of all options
     * @throws IOException              If the file is invalid
     * @throws IllegalArgumentException If an argument is invalid
     * @throws InputMismatchException   If an element is missing
     */
    public void setStopTimes(String[] dataFields, String[] header) throws IOException,
            IllegalArgumentException, InputMismatchException {
        Map<String, String> stopTimeVars = new HashMap<String, String>() {
            {
                put("trip_id", "");
                put("arrival_time", "");
                put("departure_time", "");
                put("stop_id", "");
                put("stop_sequence", "");
                put("stop_headsign", "");
                put("pickup_time", ""); //default 0
                put("drop_off_type", ""); //default 0
                put("continuous_pickup", ""); //default 1
                put("continuous_drop_off", ""); //default 1
                put("shape_dist_traveled", "");
                put("timepoint", ""); //default 1
            }
        };

        mapLoop(dataFields, header, stopTimeVars);

        stopTimes.add(new StopTime(validateString(stopTimeVars.get("trip_id")),
                validateTime(stopTimeVars.get("arrival_time")),
                validateTime(stopTimeVars.get("departure_time")),
                validateString(stopTimeVars.get("stop_id")),
                stringToInt(stopTimeVars.get("stop_sequence")),
                stopTimeVars.get("stop_headsign"),
                stringToInt(setDefault(
                        stopTimeVars.get("pickup_time"), "0")),
                stringToInt(setDefault(
                        stopTimeVars.get("drop_off_type"), "0"))));
    }


    private String setDefault(String input, String defaultVal) {
        if (input.isEmpty()) {
            input = defaultVal;
        }
        return input;
    }

    /**
     * Calls out for all the GTFS files
     *
     * @param path the directory where the GTFS files will be stored
     * @throws FileNotFoundException If the directory is not found
     */
    public void export(Path path) throws FileNotFoundException {
        for (String s : gtfsFiles) {
            out(path.toString() + File.separator + s);
        }
    }

    /**
     * Calls individual export methods for each GTFS file
     *
     * @param path The directory of the GTFS file
     * @throws FileNotFoundException If the directory is not found
     */
    private void out(String path) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(path)) {
            if (path.endsWith("stops.txt")) {
                exportStops(writer);
            } else if (path.endsWith("routes.txt")) {
                exportRoutes(writer);
            } else if (path.endsWith("trips.txt")) {
                exportTrips(writer);
            } else if (path.endsWith("stop_times.txt")) {
                exportStopTimes(writer);
            }
        }
    }

    /**
     * Method to validate whether time is entered correctly
     *
     * @param potentialTime a potential time stamp
     * @return the time argument if it was formatted correctly
     * @throws InputMismatchException when times are entered improperly
     */
    public String validateTime(String potentialTime) throws InputMismatchException {
        boolean isProperFormat = potentialTime.matches("^(\\d\\d:\\d\\d:\\d\\d)");
        if (!isProperFormat) {
            throw new InputMismatchException("Incorrect time formatting found");
        }
        return potentialTime;
    }

    /**
     * Method to validate weather a string not null or empty
     *
     * @param string is the string to validate
     * @return The valid string
     * @throws InputMismatchException If the string is invalid
     */
    private String validateString(String string) throws InputMismatchException {
        if (string == null || string.equals("")) {
            throw new InputMismatchException("Missing a required value");
        }
        return string;
    }


    /**
     * Creates a new text file stops.txt
     *
     * @param writer The print writer to export the file
     */
    private void exportStops(PrintWriter writer) {
        writer.println("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
        for (Stop s : stops.values()) {
            writer.println(s.getStopId() + "," + s.getStopName() +
                    "," + s.getStopDesc() + "," + s.getStopLat() + ","
                    + s.getStopLon());
        }
    }

    /**
     * Creates a new text file routes.txt
     *
     * @param writer The print writer to export the file
     */
    private void exportRoutes(PrintWriter writer) {
        writer.println("route_id,agency_id,route_short_name," +
                "route_long_name,route_desc,route_type,route_url," +
                "route_color,route_text_color");
        for (Route r : routes) {
            writer.println(r.getRouteId() + "," + r.getAgencyId() +
                    "," + r.getRouteShortName() + ","
                    + r.getRouteLongName() + "," + r.getRouteDesc() +
                    "," + r.getRouteType() +
                    "," + r.getRouteUrl() + "," + r.getRouteColor() +
                    "," + r.getRouteTextColor());
        }
    }

    /**
     * Creates a new text file trips.txt
     *
     * @param writer The print writer to export the file
     */
    private void exportTrips(PrintWriter writer) {
        writer.println("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
        for (Trip t : trips) {
            writer.println(t.getRouteId() + "," +
                    t.getServiceId() + "," + t.getTripId() +
                    "," + t.getTripHeadSign() +
                    "," + t.getDirectionId() + "," +
                    t.getBlockId() + "," + t.getShapeId());
        }
    }

    /**
     * Creates a new text file stop_times.txt
     *
     * @param writer The print writer to export the file
     */
    private void exportStopTimes(PrintWriter writer) {
        writer.println("trip_id,arrival_time,departure_time," +
                "stop_id,stop_sequence,stop_headsign,pickup_type," +
                "drop_off_type");
        for (StopTime st : stopTimes) {
            writer.println(st.getTripId() + "," +
                    st.getArrivalTime() + "," + st.getDepartureTime() + ","
                    + st.getStopId() + "," + st.getStopSequence() + "," +
                    st.getStopHeadSign() + "," + st.getPickupType() + "," +
                    st.getDropOffType());
        }
    }


    /**
     * Not Implemented
     *
     * @param observer null
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Not Implemented
     *
     * @param observer null
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Not Implemented
     *
     * @return null
     */
    private double getCurrentTime() {
        return 0;
    }

    /**
     * Setter for the search results field
     * @param searchResults the results for a given search
     */
    public void setSearchResults(List<String> searchResults){
        this.searchResults = searchResults.stream().distinct().collect(Collectors.toList());
        notifyObservers();
    }

    /**
     * Not Implemented
     *
     * @param variable null
     * @param routeId  null
     * @param tripId   null
     * @param stopId   null
     * @return null
     */
    public double getData(String variable, int routeId, int tripId, int stopId) {
        return 0;
    }

    /**
     * Not Implemented
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(routes, trips, stopTimes, stops, searchResults);
        }

    }

    /**
     * Getter for the list of Routes
     *
     * @return The List of Routes
     */
    public Collection<Route> getRoutes() {
        return routes;
    }

    /**
     * The getter for stopTimes
     *
     * @return The list of stopTimes
     */
    public Collection<StopTime> getStopTimes() {
        return stopTimes;
    }

    /**
     * The getter for the list of Trips
     *
     * @return the list of Trips
     */
    public Collection<Trip> getTrips() {
        return trips;
    }

    /**
     * The getter for the list of Stops
     *
     * @return The list of Stops
     */
    public Map<String, Stop> getStops() {
        return stops;
    }

    /**
     * Setter used for testing purposes
     *
     * @param stops the stops list
     */
    public void setTestingStops(Map<String, Stop> stops) {
        this.stops = stops;
    }

    /**
     * Setter of routes used for testing purposes
     *
     * @param routes the routes collection
     */
    public void setTestingRoutes(Collection<Route> routes) {
        this.routes = routes;
    }

    /**
     * StopTimes setter used for testing
     *
     * @param stopTimes the collection of stopTimes
     */
    public void setTestingStopTimes(Collection<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

    /**
     * Trips setter used for testing purposes
     *
     * @param trips The collection of trips
     */
    public void setTestingTrips(Collection<Trip> trips) {
        this.trips = trips;
    }

}
