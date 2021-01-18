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

import javafx.scene.control.Alert;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Opens a browser window to display a map with a stop
 */
public class MapView implements Observer {
    private static final int MAX_POINTS = 100;
    private static final int OFFSET = 28;

    private String baseUrl = "https://static-maps.yandex.ru/1.x/?lang=en_US&l=map&pt=";
    private String satUrl = "https://static-maps.yandex.ru/1.x/?lang=en_US&l=sat&pt=";
    private String url = "";

    /**
     * Opens the map in the users browser
     * Inspired by:
     * https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
     * @throws IOException If the desktop can't be opened
     * @throws URISyntaxException If the uri is not valid
     */
    private void openUrl() throws IOException, URISyntaxException{
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } else {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("xdg-open " + url);
        }
    }

    /**
     * Creates a new map url to display
     * @param routes the routes instance
     * @param trips the trips instance
     * @param stopTimes the stopTimes instance
     * @param stops the stops instance
     * @param searchResults the searchResults instance
     */
    @Override
    public void update(Collection<Route> routes, Collection<Trip> trips,
                       Collection<StopTime> stopTimes, Map<String,
            Stop> stops, List<String> searchResults) {

        StringBuilder urlBuilder = new StringBuilder();
        if (searchResults != null && searchResults.size() > 1) {
            if (searchResults.get(0).startsWith("stop_ids")) {
                urlBuilder.append(baseUrl);
                for (int i = 1; i < MAX_POINTS && i < searchResults.size(); i++) {
                    Stop stop = stops.get(searchResults.get(i));
                    if (stop != null) {
                        urlBuilder.append(stop.getStopLon());
                        urlBuilder.append(',');
                        urlBuilder.append(stop.getStopLat());
                        urlBuilder.append(",pmgns");
                        urlBuilder.append(i);
                        urlBuilder.append('~');
                    }
                }
                url = urlBuilder.substring(0, urlBuilder.length() - 1);
            } else if (searchResults.get(0).startsWith("route_ids")) {
                urlBuilder.append(satUrl);
                String stopId = searchResults.get(0).substring(OFFSET,
                        searchResults.get(0).lastIndexOf(':'));
                Stop stop = stops.get(stopId);
                urlBuilder.append(stop.getStopLon());
                urlBuilder.append(',');
                urlBuilder.append(stop.getStopLat());
                urlBuilder.append(",pmgnm");
                urlBuilder.append("&z=16");
                url = urlBuilder.toString();

            } else {
                url = "";
            }
            if (url != null && !url.equals("")) {

                try {
                    openUrl();
                } catch (IOException | URISyntaxException e) {
                    catchE(e);
                }
            }
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
}
