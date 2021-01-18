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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamd.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class that is intended to test the importation of GTFS files
 * from the perspective of the GTFSData class
 */
class GTFSDataTest {
    Path validGTFS = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS");
    Path invalidGTFS = Paths.get(System.getProperty("user.dir"));
    Path validRoute = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS", "routes.txt");
    Path validStopTime = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS", "stop_times.txt");
    Path validStop = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS", "stops.txt");
    Path validTrip = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS", "trips.txt");
    Path invalidRoute = Paths.get(System.getProperty("user.dir"), "stop_times.txt");
    Path invalidStopTime = Paths.get(System.getProperty("user.dir"), "stop_times.txt");
    Path invalidStop = Paths.get(System.getProperty("user.dir"), "stops.txt");
    Path invalidTrip = Paths.get(System.getProperty("user.dir"), "trips.txt");
    Path blankRoute = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS - Empty", "routes.txt");
    Path blankStopTime = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS - Empty", "stop_times.txt");
    Path blankStop = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS - Empty", "stops.txt");
    Path blankTrip = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS - Empty", "trips.txt");
    GTFSData gtfsData;
    DataVisualizer dataVisualizer = new DataVisualizer();
    MapView mapView = new MapView();

    /**
     * Executed before each test
     */
    @BeforeEach
    void setUp() {

        try {
            gtfsData = new GTFSData(validGTFS, dataVisualizer, mapView);
            gtfsData.setTestingRoutes(new ArrayList<>());
            gtfsData.setTestingStops(new HashMap<>());
            gtfsData.setTestingTrips(new ArrayList<>());
            gtfsData.setTestingStopTimes(new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executed after each test
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Intended to test that the constructor for the GTFSData
     * class operates smoothly when it is given a valid file,
     * and throws the correct exception when given an invalid file
     *
     * @author Nigel Nelson
     */
    @Test
    public void testConstructor() {
        try {
            gtfsData = new GTFSData(validGTFS, dataVisualizer, mapView);
        } catch (IOException e) {
            fail("Constructor is unable to handle a valid GTFS file");
        }
    }

    /**
     * Intended to test that the constructor for the GTFSData
     * throws the correct exception when given an invalid file
     *
     * @author Nigel Nelson
     */
    @Test
    public void testInvalidConstructor() {
        boolean isConstructorWorking = false;
        try {
            gtfsData = new GTFSData(invalidGTFS, dataVisualizer, mapView);
        } catch (IOException e) {
            isConstructorWorking = true;
        }
        if (!isConstructorWorking) {
            fail("Constructor is unable to identify invalid GTFS files");
        }
    }

    /**
     * Intended to test the set() method is capable of
     * loading valid GTFS .txt file components
     *
     * @author Nigel Nelson
     */
    @Test
    public void testValidSet() {
        try {
            gtfsData.set(validStop.toString());
            gtfsData.set(validStopTime.toString());
            gtfsData.set(validTrip.toString());
            gtfsData.set(validRoute.toString());
        } catch (IOException | InputMismatchException e) {
            fail("Set method unable to handle valid .txt files");
        }
    }

    /**
     * Intended to test the set() method is capable of
     * throwing the proper exception when invalid files
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testInvalidSet() {
        boolean isSetAcceptingBadFiles = true;
        try {
            gtfsData.set(invalidStop.toString());
            gtfsData.set(invalidRoute.toString());
            gtfsData.set(invalidStopTime.toString());
            gtfsData.set(invalidTrip.toString());
        } catch (IOException e) {
            isSetAcceptingBadFiles = false;
        }
        if (isSetAcceptingBadFiles) {
            fail("Set method unable to identify invalid .txt files");
        }
    }

    /**
     * Intended to test the set() method is capable of
     * throwing the proper exception when empty/invalid files
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testEmptySet() {
        boolean isSetAcceptingBlankFiles = true;
        try {
            gtfsData.set(blankRoute.toString());
            gtfsData.set(blankStop.toString());
            gtfsData.set(blankStopTime.toString());
            gtfsData.set(blankTrip.toString());
        } catch (InputMismatchException e) {
            isSetAcceptingBlankFiles = false;
        } catch (IOException e) {
        }
        if (isSetAcceptingBlankFiles) {
            fail("Set method unable to identify blank .txt files");
        }
    }

    /**
     * Intended to test the setStops() method is capable of
     * creating a proper Stop instance and comparing it to
     * a control
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */

    @Test
    public void testValidSetStops() {
        String goodInput = "6712,STATE & 5101 #6712,,43.0444475,-87.9779369";
        String[] separator = goodInput.split(",",-1);
        String[] stopVars = new String[] {"stop_id", "stop_name", "stop_desc", "stop_lat", "stop_lon"};
        try {
            gtfsData.setStops(separator, stopVars);
        } catch (IOException e) {
            fail("Unable to handle a valid stop input");
        }
        Stop testStop = gtfsData.getStops().get("6712");
        Stop controlStop = new Stop("6712", "STATE & 5101 #6712", "",
                43.0444475, -87.9779369);
        assertEquals(testStop.getStopId(), controlStop.getStopId());
        assertEquals(controlStop.getStopName(), testStop.getStopName());
        assertEquals(controlStop.getStopDesc(), testStop.getStopDesc());
        assertEquals(testStop.getStopLat(), controlStop.getStopLat());
        assertEquals(testStop.getStopLon(), controlStop.getStopLon());
    }

    /**
     * Intended to test the setStops() method is capable of
     * throwing the proper exception when invalid data is passed in
     * as an argument
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testInvalidSetStops() {
        boolean isInvalidDataPassable = true;
        String badInput = "6712,STATE & 5101 #6712,,43.04xxxx44475,-87.97xxxx79369";
        String[] separator = badInput.split(",",-1);
        String[] stopVars = new String[] {"stop_id", "stop_name", "stop_desc", "stop_lat", "stop_lon"};
        try {
            gtfsData.setStops(separator, stopVars);
        } catch (InputMismatchException e) {
            isInvalidDataPassable = false;
        } catch (IOException e) {

        }
        if (isInvalidDataPassable) {
            fail("Unable to throw an exception when invalid data is passed");
        }
    }

    /**
     * Intended to test the setRoutes() method is capable of
     * creating a proper Route instance and comparing it to
     * a control
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testValidSetRoutes() {
        String goodInput = "12,MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[] separator = goodInput.split(",",-1);
        String[] routeVars = new String[] {"route_id", "agency_id", "route_short_name",
                "route_long_name", "route_desc", "route_type", "route_url", "route_color", "route_text_color"};
        try {
            gtfsData.setRoutes(separator, routeVars);
        } catch (IOException e) {
            fail("Unable to handle a valid stop input");
        }
        Collection<Route> routes = gtfsData.getRoutes();
        Route testRoute = routes.iterator().next();
        Route controlRoute = new Route("12", "MCTS", "12",
                "Teutonia-Hampton", "", 3, "", "008345", "");
        assertEquals(testRoute.getRouteId(), controlRoute.getRouteId());
        assertEquals(testRoute.getAgencyId(), controlRoute.getAgencyId());
        assertEquals(testRoute.getRouteShortName(), controlRoute.getRouteShortName());
        assertEquals(testRoute.getRouteLongName(), controlRoute.getRouteLongName());
        assertEquals(testRoute.getRouteDesc(), controlRoute.getRouteDesc());
        assertEquals(testRoute.getRouteType(), controlRoute.getRouteType());
        assertEquals(testRoute.getRouteUrl(), controlRoute.getRouteUrl());
        assertEquals(testRoute.getRouteColor(), controlRoute.getRouteColor());
        assertEquals(testRoute.getRouteTextColor(), controlRoute.getRouteTextColor() + "000000");
    }

    /**
     * Intended to test the setRoutes() method is capable of
     * throwing the proper exception when invalid data is passed in
     * as an argument
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */

    @Test
    public void testInValidSetRoutes() {
        boolean isInvalidDataPassable = true;
        String badInput = "12xx,MCT234S,12,Teutonia-Hampton,,3xx,,008345,";
        String[] separator = badInput.split(",",-1);
        String[] routeVars = new String[] {"route_id", "agency_id", "route_short_name",
                "route_long_name", "route_desc", "route_type", "route_url", "route_color", "route_text_color"};
        try {
            gtfsData.setRoutes(separator, routeVars);
        } catch (InputMismatchException e) {
            isInvalidDataPassable = false;
        } catch (IOException e) {
        }
        if (isInvalidDataPassable) {
            fail("Unable to throw an exception when invalid data is passed");
        }

    }

    /**
     * Intended to test the setTrips() method is capable of
     * creating a proper Stop instance and comparing it to
     * a control
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */

    @Test
    public void testValidSetTrips() {
        String goodInput = "64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23";
        String[] separator = goodInput.split(",",-1);
        String[] tripVars = new String[] {"route_id", "service_id", "trip_id",
                "trip_headsign", "direction_id", "block_id" ,"shape_id"};
        try {
            gtfsData.setTrips(separator, tripVars);
        } catch (IOException e) {
            fail("Unable to handle a valid stop input");
        }
        Collection<Trip> trips = gtfsData.getTrips();
        Trip testTrip = trips.iterator().next();
        Trip controlTrip = new Trip("64", "17-SEP_SUN", "21736564_2535",
                "60TH-VLIET", 0, "64102", "17-SEP_64_0_23");
        assertEquals(testTrip.getRouteId(), controlTrip.getRouteId());
        assertEquals(testTrip.getServiceId(), controlTrip.getServiceId());
        assertEquals(testTrip.getTripId(), controlTrip.getTripId());
        assertEquals(testTrip.getTripHeadSign(), controlTrip.getTripHeadSign());
        assertEquals(testTrip.getDirectionId(), controlTrip.getDirectionId());
        assertEquals(testTrip.getBlockId(), controlTrip.getBlockId());
        assertEquals(testTrip.getShapeId(), controlTrip.getShapeId());
    }

    /**
     * Intended to test the setTrips() method is capable of
     * throwing the proper exception when invalid data is passed in
     * as an argument
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testInvalidSetTrips() {
        boolean isInvalidDataPassable = true;
        String badInput = "6xx4,17-SEP_SUN,21736564_2535,60TH-VLIET,x0,64dd102,17-SEP_64_0_23";
        String[] separator = badInput.split(",",-1);
        String[] tripVars = new String[] {"route_id", "service_id", "trip_id",
                "trip_headsign", "direction_id", "block_id,shape_id"};
        try {
            gtfsData.setTrips(separator, tripVars);
        } catch (InputMismatchException e) {
            isInvalidDataPassable = false;
        } catch (IOException e) {
        }
        if (isInvalidDataPassable) {
            fail("Unable to throw an exception when invalid data is passed");
        }
    }

    /**
     * Intended to test the setStopTimes() method is capable of
     * creating a proper StopTime instance and comparing it to
     * a control
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testValidSetStopTimes() {
        String goodInput = "21736564_2535,08:51:00,08:51:00,9113,1,,0,0";
        String[] separator = goodInput.split(",",-1);
        String[] stopTimeVars = new String[] {"trip_id", "arrival_time",
                "departure_time", "stop_id", "stop_sequence",
                "stop_headsign", "pickup_type", "drop_off_type"};
        try {
            gtfsData.setStopTimes(separator, stopTimeVars);
        } catch (IOException e) {
            fail("Unable to handle a valid stop input");
        }
        Collection<StopTime> stopTimes = gtfsData.getStopTimes();
        StopTime testStopTime = stopTimes.iterator().next();
        StopTime controlStopTime = new StopTime("21736564_2535", "08:51:00",
                "08:51:00", "9113", 1, "", 0, 0);
        assertEquals(testStopTime.getTripId(), controlStopTime.getTripId());
        assertEquals(testStopTime.getArrivalTime(), controlStopTime.getArrivalTime());
        assertEquals(testStopTime.getDepartureTime(), controlStopTime.getDepartureTime());
        assertEquals(testStopTime.getStopId(), controlStopTime.getStopId());
        assertEquals(testStopTime.getStopSequence(), controlStopTime.getStopSequence());
        assertEquals(testStopTime.getStopHeadSign(), controlStopTime.getStopHeadSign());
        assertEquals(testStopTime.getPickupType(), controlStopTime.getPickupType());
        assertEquals(testStopTime.getDropOffType(), controlStopTime.getDropOffType());
    }

    /**
     * Intended to test the setStopTimes() method is capable of
     * throwing the proper exception when invalid data is passed in
     * as an argument
     * are passed in as an argument
     *
     * @author Nigel Nelson
     */
    @Test
    public void testInvalidSetStopTimes() {
        boolean isInvalidDataPassable = true;
        String badInput = "21736564_2535,08:51::::00,08:::51:00,911ccc3,s1,,se0,e0";
        String[] separator = badInput.split(",",-1);
        String[] stopTimeVars = new String[] {"trip_id", "arrival_time",
                "departure_time", "stop_id", "stop_sequence",
                "stop_headsign", "pickup_type", "drop_off_type"};
        try {
            gtfsData.setStopTimes(separator, stopTimeVars);
        } catch (InputMismatchException e) {
            isInvalidDataPassable = false;
        } catch (IOException e) {
        }
        if (isInvalidDataPassable) {
            fail("Unable to throw an exception when invalid data is passed");
        }
    }


}
