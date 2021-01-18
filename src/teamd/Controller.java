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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * Controller class for the GTFS application
 */
public class Controller {
    DataVisualizer dataVisualizer = new DataVisualizer();
    MapView mapViewer = new MapView();
    GTFSData gtfsData;
    @FXML
    Button tripsPerStopButton;
    @FXML
    Button nextTripButton;
    @FXML
    Button allRoutesButton;
    @FXML
    Button allStopsButton;
    @FXML
    Button exportButton;
    @FXML
    Button updateAttributesButton;
    @FXML
    Button displayFutureTripsGivenRouteID;

    /**
     * prompts the user to select a directory
     *
     * @param event when the open option is clicked
     * @throws IOException the IO exception that can be thrown if the file is not found or valid
     */
    @FXML
    private void chooseDirectory(ActionEvent event) throws IOException {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select a directory");
            File selectedFile = directoryChooser.showDialog(null);
            if (selectedFile != null) {
                if (event.toString().endsWith("'Import Files']")) {
                    importGTFS(selectedFile);
                    successfulLoad();
                } else if (event.toString().endsWith("'Export Files']")) {
                    export(selectedFile);
                    successfulExport();
                }
            }
        } catch (IllegalArgumentException |
                IllegalStateException | NoSuchElementException | IOException e) {
            catchE(e);
        }

    }

    /**
     * Creates a new GTFSData object and passes in file
     *
     * @param file is the path of the directory that contains the GTFS file
     * @throws IOException If file is not a valid
     */
    public void importGTFS(File file) throws IOException {

        if (file.isDirectory()) {
            gtfsData = new GTFSData(file.toPath(), dataVisualizer, mapViewer);
            if (tripsPerStopButton != null) {
                tripsPerStopButton.setDisable(false);
                exportButton.setDisable(false);
            }
            if (nextTripButton != null) {
                nextTripButton.setDisable(false);
            }
            if (allRoutesButton != null) {
                allRoutesButton.setDisable(false);
            }
            if (allStopsButton != null) {
                allStopsButton.setDisable(false);
            }
            if (exportButton != null) {
                exportButton.setDisable(false);
            }
            if (updateAttributesButton != null) {
                updateAttributesButton.setDisable(false);
            }
            if (displayFutureTripsGivenRouteID != null) {
                displayFutureTripsGivenRouteID.setDisable(false);
            }

        }

    }


    public void setDataVisualizer(DataVisualizer dataVisualizer){
        this.dataVisualizer = dataVisualizer;
    }


    /**
     * Calls export in GTFSData
     *
     * @param file is the path of the directory that will store the GTFS files
     * @throws FileNotFoundException If the file is not found
     */
    private void export(File file) throws FileNotFoundException {
        if (file.isDirectory()) {
            gtfsData.export(file.toPath());
        }
    }


    /**
     * Method that finds the number of trips that each
     * stop is found on
     *
     * @param e The Action Event
     */
    @FXML
    private void displayTripsPerStop(ActionEvent e) {
        int value;
        Map<java.lang.String, Integer> tripsPerStop = new HashMap<>();

        Collection<StopTime> stopTimes = gtfsData.getStopTimes();

        for (StopTime stopTime : stopTimes) {
            java.lang.String stopId = stopTime.getStopId();
            value = 1;
            if (tripsPerStop.containsKey(stopId)) {
                value = tripsPerStop.get(stopId) + 1;
            }
            tripsPerStop.put(stopTime.getStopId(), value);
        }

        ArrayList<String> searchList = new ArrayList<>();
        searchList.add("Stop_ID : Number of trips");
        for (Map.Entry<java.lang.String, Integer> stop : tripsPerStop.entrySet()) {
            searchList.add(stop.getKey() + " : " + stop.getValue());
        }
        gtfsData.setSearchResults(searchList);
    }


    /**
     * Method that finds the future trips given a stop_id and time
     * @param event The Action Event
     */
    @FXML
    private void displayNextTrip(ActionEvent event) {
        try {
            java.lang.String stopID = promptStopId();
            DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("kk:mm:ss");
            String time = String.valueOf(dataTimeFormatter.format(LocalDateTime.now()));

            Collection<StopTime> stopTimes = gtfsData.getStopTimes();
            List<String> trips = new ArrayList<>();
            trips.add("Future Trips for stop_id: " + stopID + " after " + time);
            for (StopTime stopTime : stopTimes) {
                if (stopID.equals(stopTime.getStopId())) {
                    java.lang.String arrivalTime = stopTime.getArrivalTime();
                    if (Integer.parseInt(time.substring(0, 1)) <
                            Integer.parseInt(arrivalTime.substring(0, 1))
                            && Integer.parseInt(time.substring(3, 4)) <
                            Integer.parseInt(arrivalTime.substring(3, 4))) {
                        trips.add(stopTime.getTripId());
                    }
                }
            }
            gtfsData.setSearchResults(trips);
        } catch (InputMismatchException e) {
            catchE(e);
        }
    }


    /**
     * Method that finds all route_ids given a valid stop_id
     * @param event When the button is clicked
     */
    @FXML
    private void displayAllRoutes(ActionEvent event) {
        try {
            String stopID = promptStopId();
            List<String> routes = new ArrayList<>();
            routes.add("route_ids that contain stop " + stopID + ":");
            Collection<Trip> trips = gtfsData.getTrips();
            for (StopTime stopTime : gtfsData.getStopTimes()) {
                if (stopID.equals(stopTime.getStopId())) {
                    for (Trip trip : trips) {
                        if (stopTime.getTripId().equals(trip.getTripId())) {
                            routes.add(trip.getRouteId());
                        }

                    }
                }
            }
            gtfsData.setSearchResults(routes);
        } catch (InputMismatchException e) {
            catchE(e);
        }
    }


    /**
     * Method that finds all stop_ids given a valid route_id
     * @param event When the button is clicked
     */
    @FXML
    private void displayAllStops(ActionEvent event) {
        try {
            String routeId = promptRouteId();
            List<String> stops = new ArrayList<>();
            stops.add("stop_ids that contain route " + routeId + ":");
            Collection<StopTime> stopTimes = gtfsData.getStopTimes();
            for (Trip trip: gtfsData.getTrips()) {
                if (routeId.equals(trip.getRouteId())) {
                    for (StopTime stopTime: stopTimes) {
                        if (trip.getTripId().equals(stopTime.getTripId())) {
                            stops.add(stopTime.getStopId());
                        }
                    }
                }
            }
            gtfsData.setSearchResults(stops);
        } catch (InputMismatchException e) {
            catchE(e);
        }
    }

    /**
     * Method that finds all trip_id's containing a given route_id
     * @param actionEvent When the button is clicked
     * Author: Nicholas Rossignol
     */
    @FXML
    private void displayFutureTripsGivenRouteID(ActionEvent actionEvent){
        try{
            DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("kk:mm:ss");
            String stringTime = String.valueOf(dataTimeFormatter.format(LocalDateTime.now()));
            // all stops test stringTime below
            //String stringTime = "00:00:00";


            java.lang.String routeId = promptRouteId();

            List<String> trips = new ArrayList<>();
            List<String> tripsToReturn = new ArrayList<>();
            tripsToReturn.add("Future Trips for route_id: " + routeId + " after " + stringTime);


            for(Trip trip : gtfsData.getTrips()) {
                if (routeId.equals(trip.getRouteId())) {
                    trips.add(trip.getTripId());

                }
            }

            Collection<StopTime> stopTimes = gtfsData.getStopTimes();

            for (StopTime stopTime : stopTimes) {
                for (String trip : trips) {
                    if (trip.equals(stopTime.getTripId())) {
                        java.lang.String arrivalTime = stopTime.getArrivalTime();
                        java.lang.String departureTime = stopTime.getDepartureTime();
                        if (Integer.parseInt(stringTime.substring(0, 1)) <=
                                Integer.parseInt(arrivalTime.substring(0, 1))
                                && Integer.parseInt(stringTime.substring(3, 4)) <=
                                Integer.parseInt(arrivalTime.substring(3, 4))) {
                            tripsToReturn.add(stopTime.getTripId());
                        } else if (Integer.parseInt(stringTime.substring(0, 1)) <=
                                Integer.parseInt(departureTime.substring(0, 1))
                                && Integer.parseInt(stringTime.substring(3, 4)) <=
                                Integer.parseInt(departureTime.substring(3, 4))) {
                            tripsToReturn.add(stopTime.getTripId());
                        }
                    }
                }
            }
            if(tripsToReturn.isEmpty()){

                tripsToReturn.add("There are no trips happening in the future for this route");
            }
            gtfsData.setSearchResults(tripsToReturn);
        } catch (InputMismatchException e){
            catchE(e);
        } 

    }

    /**
     * Method that updates the attributes upon the update attribute event occurring
     * @param actionEvent the action event that happens when update Attribute is called
     */
    @FXML
    public void updateAttribute(ActionEvent actionEvent) {
        try {

            //i solved my problem adding the following 2 lines of code...
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);

            Object[] options = {"Routes", "Trips", "Stop Times", "Stops"};

            Object firstSelection = JOptionPane.showInputDialog(frame,
                    "Choose which variable you'd like to change", "Choice Dialog",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            String firstChoice = firstSelection.toString();
            Object[] secondOptions;

            if (firstChoice.equals("Routes")) {
                secondOptions = new Object[] {"route_id",
                        "route_color",
                };

            } else if (firstChoice.equals("Trips")) {
                secondOptions = new Object[] {"trip_id",
                        "trip_headsign",
                        "trip_short_name"};
            } else if (firstChoice.equals("Stop Times")) {
                secondOptions = new Object[] {"arrival_time",
                        "departure_time",
                        "stop_sequence",
                };

            } else {
                secondOptions = new Object[] {"stop_id",
                        "stop_name",
                        "stop_desc",
                        "stop_lat",
                        "stop_lon",
                };
            }

            Object secondSelection = JOptionPane.showInputDialog(frame,
                    "Choose which variable you'd like to change", "Choice Dialog",
                    JOptionPane.PLAIN_MESSAGE, null, secondOptions, secondOptions[0]);
            String secondChoice = secondSelection.toString();

            JFrame inputFrame;


            if (firstChoice.equals("Routes")) {
                Route specifiedRoute;
                HashMap<String, Route> routeHashMap = new HashMap<>();
                Collection<Route> routes = gtfsData.getRoutes();
                for (Route route : routes) {
                    routeHashMap.put(route.getRouteId(), route);
                }

                Object routeSelection = JOptionPane.showInputDialog(frame,
                        "Choose which Route you'd like to edit by " +
                                "choosing a route ID", "Choice Dialog",
                        JOptionPane.PLAIN_MESSAGE, null, routeHashMap.keySet().toArray(), routeHashMap.keySet().toArray()[0]);
                String routeChoice = routeSelection.toString();
                specifiedRoute = routeHashMap.get(routeChoice);

                if (secondChoice.equals("route_id")) {
                    String newId = "";
                    do {
                        inputFrame = new JFrame();
                        newId = JOptionPane.showInputDialog(inputFrame,
                                "Enter your desired route_id name");
                    } while (newId.equals(""));
                    specifiedRoute.setRouteId(newId);
                    Collection<Trip> trips = gtfsData.getTrips();
                    for (Trip trip : trips) {
                        if (trip.getRouteId().equals(routeChoice)) {
                            trip.setRouteId(newId);
                        }
                    }
                } else if (secondChoice.equals("route_color")) {
                    String newColor = "";
                    do {
                        inputFrame = new JFrame();
                        newColor = JOptionPane.showInputDialog(inputFrame,
                                "Enter a new hex color code");
                    } while (newColor.length() != 6);
                    specifiedRoute.setRouteColor(newColor);
                }
            } else if (firstChoice.equals("Trips")) {

                Trip specifiedTrip;
                HashMap<String, Trip> tripHashMap = new HashMap<>();
                Collection<Trip> trips = gtfsData.getTrips();
                for (Trip trip : trips) {
                    tripHashMap.put(trip.getTripId(), trip);
                }

                Object tripSelection = JOptionPane.showInputDialog(frame,
                        "Choose which Trip you'd like to edit by choosing " +
                                "a trip ID", "Choice Dialog",
                        JOptionPane.PLAIN_MESSAGE, null, tripHashMap.keySet().toArray(), tripHashMap.keySet().toArray()[0]);
                String tripChoice = tripSelection.toString();
                specifiedTrip = tripHashMap.get(tripChoice);

                if (secondChoice.equals("trip_id")) {
                    int reply = JOptionPane.showConfirmDialog(null, "Would you like this trip_id to affect all related trips?",
                            "Change all Times dialog", JOptionPane.YES_NO_OPTION);
                    if (reply == 0) {
                        changeAllTripIds(specifiedTrip);
                    } else {
                        String newId = "";
                        do {
                            inputFrame = new JFrame();
                            newId = JOptionPane.showInputDialog(inputFrame,
                                    "Enter a valid Trip ID");
                        } while (newId.equals(""));
                        specifiedTrip.setTripId(newId);
                    }
                } else if (secondChoice.equals("trip_headsign")) {
                    String newHeadSign = "";
                    do {
                        inputFrame = new JFrame();
                        newHeadSign = JOptionPane.showInputDialog(inputFrame,
                                "Enter a valid Trip head sign");
                    } while (newHeadSign.equals(""));
                    specifiedTrip.setTripHeadSign(newHeadSign);


                }
            } else if (firstChoice.equals("Stop Times")) {
                StopTime specifiedStopTime;

                HashMap<String, StopTime> stopTimeHashMap = new HashMap<>();
                Collection<StopTime> stopTimes = gtfsData.getStopTimes();

                for (StopTime stopTime : stopTimes) {
                    stopTimeHashMap.put(stopTime.getStopId() + ", " + stopTime.getTripId(), stopTime);
                }

                Object stopTimeSelection = JOptionPane.showInputDialog(frame,
                        "Choose which StopTime you'd like to change, represented by its " +
                                "stop ID, and trip ID", "Choice Dialog",
                        JOptionPane.PLAIN_MESSAGE, null, stopTimeHashMap.keySet().toArray(), stopTimeHashMap.keySet().toArray()[0]);
                String stopTimeChoice = stopTimeSelection.toString();

                specifiedStopTime = stopTimeHashMap.get(stopTimeChoice);

                if (secondChoice.equals("arrival_time")) {

                    int reply = JOptionPane.showConfirmDialog(null, "Would you like this time to affect all related Stop Times?",
                            "Change all Times dialog", JOptionPane.YES_NO_OPTION);
                    if (reply == 0) {
                        changeAllStopTimes(specifiedStopTime, true);
                    } else {
                        String newTime = "";
                        do {
                            inputFrame = new JFrame();
                            newTime = JOptionPane.showInputDialog(inputFrame,
                                    "Enter a valid arrival Time");
                        } while (!newTime.matches("^(\\d\\d:\\d\\d:\\d\\d)"));
                        specifiedStopTime.setArrivalTime(newTime);
                    }
                } else if (secondChoice.equals("departure_time")) {
                    int reply = JOptionPane.showConfirmDialog(null,
                            "Would you like to change all related Stop Times to the same time?",
                            "Change all Times dialog", JOptionPane.YES_NO_OPTION);
                    if (reply == 0) {
                        changeAllStopTimes(specifiedStopTime, false);
                    } else {
                        String newTime = "";
                        do {
                            inputFrame = new JFrame();
                            newTime = JOptionPane.showInputDialog(inputFrame,
                                    "Enter a valid departure time");
                        } while (!newTime.matches("^(\\d\\d:\\d\\d:\\d\\d)"));
                        specifiedStopTime.setDepartureTime(newTime);
                    }
                } else if (secondChoice.equals("stop_sequence")) {
                    int reply = JOptionPane.showConfirmDialog(null,
                            "Would you like to change all related Stop Times to the same sequence assignment?",
                            "Change all Times dialog", JOptionPane.YES_NO_OPTION);
                    if (reply == 0){
                        changeAllStopSequences(specifiedStopTime);
                    } else {
                        String newStopSequence = "";
                        boolean isNumeric = false;
                        int stopSequence = -5;
                        do {
                            inputFrame = new JFrame();
                            newStopSequence = JOptionPane.showInputDialog(inputFrame,
                                    "Enter a valid stop sequence");
                            try {
                                stopSequence = Integer.parseInt(newStopSequence);
                                isNumeric = true;
                            } catch (NumberFormatException x) {
                            }
                        } while (!isNumeric && stopSequence < 0);
                        specifiedStopTime.setStopSequence(stopSequence);
                    }

                }
            } else {
                Map<String, Stop> stops = gtfsData.getStops();
                Stop specifiedStop;

                Object stopSelection = JOptionPane.showInputDialog(frame,
                        "Choose which Stop you'd like to edit by choosing a stop ID", "Choice Dialog",
                        JOptionPane.PLAIN_MESSAGE, null, stops.keySet().toArray(), stops.keySet().toArray()[0]);
                String stopChoice = stopSelection.toString();
                specifiedStop = stops.get(stopChoice);

                if (secondChoice.equals("stop_id")) {
                    String newId = "";
                    do {
                        inputFrame = new JFrame();
                        newId = JOptionPane.showInputDialog(inputFrame,
                                "Enter a valid stop_id");
                    } while (newId.equals(""));
                    specifiedStop.setStopId(newId);
                } else if (secondChoice.equals("stop_name")) {
                    String newName = "";
                    do {
                        inputFrame = new JFrame();
                        newName = JOptionPane.showInputDialog(inputFrame,
                                "Enter a valid Stop Name");
                    } while (newName.equals(""));
                    specifiedStop.setStopId(newName);
                } else if (secondChoice.equals("stop_lat")) {
                    double newLat = 0;
                    String newLatString;
                    boolean isNumeric = false;
                    do {
                        inputFrame = new JFrame();
                        newLatString = JOptionPane.showInputDialog(inputFrame,
                                "Enter a valid Stop Lattitude");
                        try {
                            newLat = Double.parseDouble(newLatString);
                            isNumeric = true;
                        } catch (NumberFormatException x) {
                        }
                    } while (!isNumeric);
                    specifiedStop.setStopLat(newLat);
                } else {
                    double newLon = 0;
                    String newLonString;
                    boolean isNumeric = false;
                    do {
                        inputFrame = new JFrame();
                        newLonString = JOptionPane.showInputDialog(inputFrame,
                                "Enter a valid Stop Longitude");
                        try {
                            newLon = Double.parseDouble(newLonString);
                            isNumeric = true;
                        } catch (NumberFormatException x) {
                        }
                    } while (!isNumeric);
                    specifiedStop.setStopLat(newLon);
                }
            }


            gtfsData.notifyObservers();
        } catch (NullPointerException e) {
        }
    }

    /**
     * Changes all trip IDs
     *
     * @param specifiedTrip the specified trip ID
     */
    private void changeAllTripIds(Trip specifiedTrip) {
        try {
            String routeId = specifiedTrip.getRouteId();
            Collection<Trip> trips = gtfsData.getTrips();

            HashMap<String, ArrayList<StopTime>> tripIds = new HashMap<>();
            for (Trip currentTrip : trips) {
                if (currentTrip.getRouteId().equals(routeId)) {
                    tripIds.put(currentTrip.getTripId(), new ArrayList<>());
                }
            }

            Collection<StopTime> stopTimes = gtfsData.getStopTimes();
            for (StopTime currentStopTime : stopTimes) {
                String currentTripId = currentStopTime.getTripId();
                if (tripIds.containsKey(currentTripId)) {
                    tripIds.get(currentTripId).add(currentStopTime);
                }
            }

            StopTime stopTime = tripIds.get(specifiedTrip.getTripId()).get(0);

            //Now the similar routes must be filtered to be all contain the same
            //stop ID's for the same stop sequences
            List<StopTime> mainStopTimeTrip = tripIds.get(specifiedTrip.getTripId());

            String sequenceFirstID = stopTime.getStopId();
            String sequenceLastID = stopTime.getStopId();
            int smallestSequence = stopTime.getStopSequence();
            int largestSequence = stopTime.getStopSequence();


            //Setting values for the "control" trip
            for (StopTime stopTime1 : mainStopTimeTrip) {
                if (stopTime1.getStopSequence() > largestSequence) {
                    sequenceLastID = stopTime1.getStopId();
                    largestSequence = stopTime1.getStopSequence();
                } else if (stopTime1.getStopSequence() < smallestSequence) {
                    sequenceFirstID = stopTime1.getStopId();
                    smallestSequence = stopTime1.getStopSequence();
                }
            }


            HashMap<String, ArrayList<StopTime>> newSet = new HashMap<>(tripIds);
            for (Map.Entry<String, ArrayList<StopTime>> set : newSet.entrySet()) {

                String testSequenceFirstID = set.getValue().get(0).getStopId();
                String testSequenceLastID = set.getValue().get(0).getStopId();
                int testSmallestSequence = set.getValue().get(0).getStopSequence();
                int testLargestSequence = set.getValue().get(0).getStopSequence();
                boolean isSimilar = false;

                for (StopTime stopTime2 : set.getValue()) {
                    if (stopTime2.getStopSequence() >= largestSequence) {
                        testSequenceLastID = stopTime2.getStopId();
                        testLargestSequence = stopTime2.getStopSequence();
                    } else if (stopTime2.getStopSequence() <= smallestSequence) {
                        testSequenceFirstID = stopTime2.getStopId();
                        testSmallestSequence = stopTime2.getStopSequence();
                    }
                }

                if (testSmallestSequence == smallestSequence && testLargestSequence == largestSequence) {
                    if (testSequenceFirstID.equals(sequenceFirstID) && testSequenceLastID.equals(sequenceLastID)) {
                        isSimilar = true;
                    }
                }
                if (!isSimilar) {
                    tripIds.remove(set.getKey());
                }

            }

            String newTripId = "";
            do {
                JFrame inputFrame = new JFrame();
                newTripId = JOptionPane.showInputDialog(inputFrame,
                        "Enter a new valid Trip_Id");
            } while (newTripId.equals(""));

            ArrayList<String> searchResults = new ArrayList<>();
            searchResults.add("Result Number. RouteID : Trip Head Sign : tripId");


            int i = 0;
            for (Trip trip1 : gtfsData.getTrips()) {
                if (tripIds.containsKey(trip1.getTripId())) {
                    trip1.setTripId(newTripId);
                    i++;
                    searchResults.add(i + ". " + trip1.getRouteId() + " : " + trip1.getTripHeadSign() + " : " + trip1.getTripId());
                }
            }
            specifiedTrip.setTripId(newTripId);
            gtfsData.setSearchResults(searchResults);
            gtfsData.notifyObservers();
            successfulUpdate();
        } catch (IndexOutOfBoundsException e) {
            errorTripIdUpdate();
        } catch (NullPointerException e){
        }
    }

    /**
     * Changes all related stop sequences
     * @param stopTime the desired stopTime
     */
    private void changeAllStopSequences(StopTime stopTime){
        try {
            String tripId = stopTime.getTripId();
            String routeId = "";
            Collection<Trip> trips = gtfsData.getTrips();
            for (Trip trip : trips) {
                if (trip.getTripId().equals(tripId)) {
                    routeId = trip.getRouteId();
                    break;
                }
            }

            HashMap<String, ArrayList<StopTime>> tripIds = new HashMap<>();
            for (Trip currentTrip : trips) {
                if (currentTrip.getRouteId().equals(routeId)) {
                    tripIds.put(currentTrip.getTripId(), new ArrayList<>());
                }
            }

            Collection<StopTime> stopTimes = gtfsData.getStopTimes();
            for (StopTime currentStopTime : stopTimes) {
                String currentTripId = currentStopTime.getTripId();
                if (tripIds.containsKey(currentTripId)) {
                    tripIds.get(currentTripId).add(currentStopTime);
                }
            }

            //Now the similar routes must be filtered to be all contain the same
            //stop ID's for the same stop sequences
            List<StopTime> mainStopTimeTrip = tripIds.get(tripId);

            String sequenceFirstID = stopTime.getStopId();
            String sequenceLastID = stopTime.getStopId();
            int smallestSequence = stopTime.getStopSequence();
            int largestSequence = stopTime.getStopSequence();


            //Setting values for the "control" trip
            for (StopTime stopTime1: mainStopTimeTrip){
                if (stopTime1.getStopSequence() > largestSequence){
                    sequenceLastID = stopTime1.getStopId();
                    largestSequence = stopTime1.getStopSequence();
                } else if(stopTime1.getStopSequence() < smallestSequence){
                    sequenceFirstID = stopTime1.getStopId();
                    smallestSequence = stopTime1.getStopSequence();
                }
            }


            HashMap<String, ArrayList<StopTime>> newSet = new HashMap<>(tripIds);
            for (Map.Entry<String, ArrayList<StopTime>> set: newSet.entrySet()){

                String testSequenceFirstID = set.getValue().get(0).getStopId();
                String testSequenceLastID = set.getValue().get(0).getStopId();
                int testSmallestSequence = set.getValue().get(0).getStopSequence();
                int testLargestSequence = set.getValue().get(0).getStopSequence();
                boolean isSimilar = false;

                for (StopTime stopTime2: set.getValue()){
                    if (stopTime2.getStopSequence() >= largestSequence){
                        testSequenceLastID = stopTime2.getStopId();
                        testLargestSequence = stopTime2.getStopSequence();
                    } else if(stopTime2.getStopSequence() <= smallestSequence){
                        testSequenceFirstID = stopTime2.getStopId();
                        testSmallestSequence = stopTime2.getStopSequence();
                    }
                }

                if (testSmallestSequence == smallestSequence && testLargestSequence == largestSequence){
                    if (testSequenceFirstID.equals(sequenceFirstID) && testSequenceLastID.equals(sequenceLastID)){
                        isSimilar = true;
                    }
                }
                if (!isSimilar){
                    tripIds.remove(set.getKey());
                }

            }



            int specifiedStopTimeSequence = stopTime.getStopSequence();

            String newSequence;
            int sequence;
            do {
                try {
                    JFrame inputFrame = new JFrame();
                    newSequence = JOptionPane.showInputDialog(inputFrame,
                            "Enter a new valid stop sequence assignment");
                    sequence = Integer.parseInt(newSequence);
                } catch (NumberFormatException e){
                    sequence = -1;
                }
            } while (sequence <= 0);

            ArrayList<String> searchResults = new ArrayList<>();
            searchResults.add("Result Number. StopID : tripId : stop sequence");

            int i = 0;
            for (ArrayList<StopTime> times: tripIds.values()){
                for (StopTime stopTime3: times){
                    if (stopTime3.getStopSequence() == specifiedStopTimeSequence){
                        i++;
                        stopTime3.setStopSequence(sequence);
                        searchResults.add(i + ". " + stopTime3.getStopId() + " : " + stopTime3.getTripId() + " : " + stopTime3.getStopSequence());
                    }
                }
            }
            stopTime.setStopSequence(sequence);


            gtfsData.setSearchResults(searchResults);
            gtfsData.notifyObservers();
            successfulUpdate();
        }catch (NullPointerException e){
        }



    }

    /**
     * Method that updates all related stop Times
     * @param stopTime the provided stop time
     * @param isArrivalTime whether or not it is an arrival time
     */
    private void changeAllStopTimes(StopTime stopTime, boolean isArrivalTime){

        try {
            String tripId = stopTime.getTripId();
            String routeId = "";
            Collection<Trip> trips = gtfsData.getTrips();
            for (Trip trip : trips) {
                if (trip.getTripId().equals(tripId)) {
                    routeId = trip.getRouteId();
                    break;
                }
            }

            HashMap<String, ArrayList<StopTime>> tripIds = new HashMap<>();
            for (Trip currentTrip : trips) {
                if (currentTrip.getRouteId().equals(routeId)) {
                    tripIds.put(currentTrip.getTripId(), new ArrayList<>());
                }
            }

            Collection<StopTime> stopTimes = gtfsData.getStopTimes();
            for (StopTime currentStopTime : stopTimes) {
                String currentTripId = currentStopTime.getTripId();
                if (tripIds.containsKey(currentTripId)) {
                    tripIds.get(currentTripId).add(currentStopTime);
                }
            }

            //Now the similar routes must be filtered to be all contain the same
            //stop ID's for the same stop sequences
            List<StopTime> mainStopTimeTrip = tripIds.get(tripId);

            String sequenceFirstID = stopTime.getStopId();
            String sequenceLastID = stopTime.getStopId();
            int smallestSequence = stopTime.getStopSequence();
            int largestSequence = stopTime.getStopSequence();


            //Setting values for the "control" trip
            for (StopTime stopTime1: mainStopTimeTrip){
                if (stopTime1.getStopSequence() > largestSequence){
                    sequenceLastID = stopTime1.getStopId();
                    largestSequence = stopTime1.getStopSequence();
                } else if(stopTime1.getStopSequence() < smallestSequence){
                    sequenceFirstID = stopTime1.getStopId();
                    smallestSequence = stopTime1.getStopSequence();
                }
            }


            HashMap<String, ArrayList<StopTime>> newSet = new HashMap<>(tripIds);
            for (Map.Entry<String, ArrayList<StopTime>> set: newSet.entrySet()){

                String testSequenceFirstID = set.getValue().get(0).getStopId();
                String testSequenceLastID = set.getValue().get(0).getStopId();
                int testSmallestSequence = set.getValue().get(0).getStopSequence();
                int testLargestSequence = set.getValue().get(0).getStopSequence();
                boolean isSimilar = false;

                for (StopTime stopTime2: set.getValue()){
                    if (stopTime2.getStopSequence() >= largestSequence){
                        testSequenceLastID = stopTime2.getStopId();
                        testLargestSequence = stopTime2.getStopSequence();
                    } else if(stopTime2.getStopSequence() <= smallestSequence){
                        testSequenceFirstID = stopTime2.getStopId();
                        testSmallestSequence = stopTime2.getStopSequence();
                    }
                }

                if (testSmallestSequence == smallestSequence && testLargestSequence == largestSequence){
                    if (testSequenceFirstID.equals(sequenceFirstID) && testSequenceLastID.equals(sequenceLastID)){
                        isSimilar = true;
                    }
                }
                if (!isSimilar){
                    tripIds.remove(set.getKey());
                }

            }



            int specifiedStopTimeSequence = stopTime.getStopSequence();

            String newTime;
            do {
                JFrame inputFrame = new JFrame();
                newTime = JOptionPane.showInputDialog(inputFrame,
                        "Enter a new valid time");
            } while (!newTime.matches("^(\\d\\d:\\d\\d:\\d\\d)"));

            ArrayList<String> searchResults = new ArrayList<>();
            searchResults.add("Result Number. Stop ID : Trip ID : arrival/departure time");
            int i = 0;

            for (ArrayList<StopTime> times: tripIds.values()){
                for (StopTime stopTime3: times){
                    if (stopTime3.getStopSequence() == specifiedStopTimeSequence){
                        i++;
                        if (isArrivalTime){
                            stopTime3.setArrivalTime(newTime);
                            searchResults.add(i + ". " + stopTime3.getStopId() + " : " + stopTime3.getTripId() + " : " + stopTime3.getArrivalTime());
                        } else {
                            stopTime3.setDepartureTime(newTime);
                            searchResults.add(i + ". " + stopTime3.getStopId() + " : " + stopTime3.getTripId() + " : " + stopTime3.getDepartureTime());
                        }

                    }
                }
            }
            if (isArrivalTime){
                stopTime.setArrivalTime(newTime);
            } else {
                stopTime.setDepartureTime(newTime);
            }

            gtfsData.setSearchResults(searchResults);
            gtfsData.notifyObservers();
            successfulUpdate();
        }catch (NullPointerException e){
        }
    }

    /**
     * Prompts the user to input a stop_id
     * @return The wanted stop_id
     */
    private java.lang.String promptStopId() {
        TextInputDialog searchStopId = new TextInputDialog("stop_id");
        searchStopId.setContentText("Please enter a stop_id");
        Optional<java.lang.String> stopResult = searchStopId.showAndWait();
        if (stopResult.isPresent()) {
            String wantedStop = stopResult.get();
            if (!gtfsData.getStops().containsKey(wantedStop)) {
                throw new InputMismatchException("\"" + wantedStop + "\" is an invalid stop_id");
            }
            return wantedStop;
        } else {
            throw new InputMismatchException("No stop_id entered");
        }
    }

    /**
     * Prompts the user to input a route_id
     * @return The wanted route_id
     */
    private java.lang.String promptRouteId() {
        TextInputDialog searchRouteId = new TextInputDialog("route_id");
        searchRouteId.setContentText("Please enter a route_id");
        Optional<String> routeResult = searchRouteId.showAndWait();
        if (routeResult.isPresent()) {
            String wantedRoute = routeResult.get();
            Boolean contains = false;
            for (Route route : gtfsData.getRoutes()) {
                if (route.getRouteId().equals(wantedRoute)) {
                    contains = true;
                }
            }
            if (!contains) {
                throw new InputMismatchException("\"" + wantedRoute + "\" is an invalid route_id");
            }
            return wantedRoute;
        } else {
            throw new InputMismatchException("No route_id entered");
        }
    }

    /**
     * Popup for general errors
     *
     * @param e the error to present to the user
     */
    private void catchE(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("An Error Has Occurred");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Method to show that GTFS data has been successfully
     * downloaded
     */
    private void successfulLoad() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("GTFS file successfully downloaded");
        alert.showAndWait();
    }

    /**
     * Method to show that GTFS data has been successfully
     * downloaded
     */
    private void successfulExport() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("GTFS files successfully exported");
        alert.showAndWait();
    }

    /**
     * Method to show that GTFS data has been successfully
     * downloaded
     */
    private void successfulUpdate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("GTFS files successfully updated");
        alert.showAndWait();
    }

    /**
     * Error encountered due to missing trip to
     * stop_time relation
     */
    private void errorTripIdUpdate() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Unable to change all related trips.\nThe selected trip" +
                " does not have any\ntrips that meet the definition of \"similar\""
        );
        alert.showAndWait();
    }


}