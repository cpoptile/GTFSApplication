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

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Controller class for the data visualizer
 */
public class DataVisualizer implements Observer {
    @FXML
    ListView routesListView;
    @FXML
    ListView tripsListView;
    @FXML
    ListView stopTimesListView;
    @FXML
    ListView stopsListView;
    @FXML
    ListView searchListView;
    Stage dataVisualizerStage;

    /**
     * Method to get the instance of the secondary stage
     * @param dataVisualizerStage the instance of the secondary stage
     */
    public void setStage(Stage dataVisualizerStage){
        this.dataVisualizerStage = dataVisualizerStage;
    }


    /**
     * Updates the data visualizer gui
     * @param routes the routes instance
     * @param trips the trips instance
     * @param stopTimes the stopTimes instance
     * @param stops the stops instance
     * @param searchResults the search results instance
     */
    @Override
    public void update(Collection<Route> routes, Collection<Trip> trips,
                       Collection<StopTime> stopTimes, Map<String, Stop> stops,
                       List<String> searchResults) {

        try {
            routesListView.getItems().clear();
            tripsListView.getItems().clear();
            stopTimesListView.getItems().clear();
            stopsListView.getItems().clear();
            searchListView.getItems().clear();


            List<String> routesList = new ArrayList<>();
            routesList.add("route_id, " + "route_long_name");
            for (Route route : routes) {
                routesList.add(route.getRouteId() + ", "
                        + route.getRouteLongName());
            }
            routesListView.getItems().addAll(routesList);


            List<String> tripsList = new ArrayList<>();
            tripsList.add("route_id, " + "trip_id");
            for (Trip trip : trips) {
                tripsList.add(trip.getRouteId() + ", " +
                        trip.getTripId());
            }
            tripsListView.getItems().addAll(tripsList);


            List<String> stopTimeList = new ArrayList<>();
            stopTimeList.add("trip_id, " + "arrival_time, " + "departure_time, " + "stop_id");
            for (StopTime stopTime : stopTimes) {
                stopTimeList.add(stopTime.getTripId() + ", " + stopTime.getArrivalTime() +
                        ", " + stopTime.getDepartureTime() + ", " + stopTime.getStopId());
            }
            stopTimesListView.getItems().addAll(stopTimeList);


            List<String> stopsList = new ArrayList<>();
            stopsList.add("stop_id, " + "stop_name");
            for (Map.Entry<java.lang.String, Stop> map : stops.entrySet()) {
                stopsList.add(map.getValue().getStopId() + ", " + map.getValue().getStopName());
            }
            stopsListView.getItems().addAll(stopsList);

            searchListView.getItems().addAll(searchResults);
        } catch (NullPointerException e) {
//            No stage identified, error is only thrown by testing
        }
    }

}
