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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamd.Trip;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Trips
 */
class TripTest {
    Trip invalidTrip;
    Trip validTrip;
    String testString = "^#%#_65Ah";


    /**
     * Executed before each test
     */
    @BeforeEach
    void setUp() {
        validTrip = new Trip("64", "17-SEP-SUN",
                "21736564", "60TH-VLIET", 0, "64102", "17-SEP" +
                "_64_0_23");
    }

    /**
     * Tests to see if valid data is correctly written to a Trip
     * through its constructor
     */
    @Test
    void ConstructorTest() {
        assertEquals("64", validTrip.getRouteId());
        assertEquals("17-SEP-SUN", validTrip.getServiceId());
        assertEquals("21736564", validTrip.getTripId());
        assertEquals("60TH-VLIET", validTrip.getTripHeadSign());
        assertEquals(0, validTrip.getDirectionId());
        assertEquals("64102", validTrip.getBlockId());
        assertEquals("17-SEP_64_0_23", validTrip.getShapeId());
    }


    /**
     * Test that validates the constructor is able to operate
     * with correctly formatted data
     *
     * @author Johnathan Paulick
     */
    @Test
    void testInvalidConstructor(){
        boolean isConstructorWorking = true;
        invalidTrip = new Trip(testString,testString,testString,testString, 0, testString, testString);
        if (!invalidTrip.getRouteId().equals(testString)) {
            isConstructorWorking = false;
        } else if(!invalidTrip.getServiceId().equals(testString)){
            isConstructorWorking = false;
        } else if(!invalidTrip.getTripId().equals(testString)){
            isConstructorWorking = false;
        } else if(!invalidTrip.getTripHeadSign().equals(testString)){
            isConstructorWorking = false;
        } else if(invalidTrip.getDirectionId() != 0){
            isConstructorWorking = false;
        } else if(!invalidTrip.getBlockId().equals(testString)){
            isConstructorWorking = false;
        } else if(!invalidTrip.getShapeId().equals(testString)){
            isConstructorWorking = false;
        }
        if (!isConstructorWorking){
            fail("Trip Constructor is not working properly");
        }
    }

}