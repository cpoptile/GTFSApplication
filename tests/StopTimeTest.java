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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamd.StopTime;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for StopTime
 */
class StopTimeTest{
    StopTime stopTime;

    /**
     * Executed before each
     */
    @BeforeEach
    void setUp() {
        stopTime = new StopTime("21736564_2535",
                "08:51:00","08:51:00",
                "9113",1,"",0,0);
    }

    /**
     * Executed after each
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Test to validate that the constructor is working
     * properly
     *
     * @author Nigel Nelson
     */
    @Test
    public void testConstructor(){
       boolean isConstructorWorking = true;
        if (!stopTime.getTripId().equals("21736564_2535")) {
            isConstructorWorking = false;
        } else if(!stopTime.getArrivalTime().equals("08:51:00")){
            isConstructorWorking = false;
        } else if(!stopTime.getDepartureTime().equals("08:51:00")){
            isConstructorWorking = false;
        } else if(stopTime.equals("9113")){
            isConstructorWorking = false;
        } else if(stopTime.getStopSequence() != 1){
            isConstructorWorking = false;
        } else if(!stopTime.getStopHeadSign().equals("")){
            isConstructorWorking = false;
        } else if(stopTime.getPickupType() != 0){
            isConstructorWorking = false;
        } else if(stopTime.getDropOffType() != 0){
            isConstructorWorking = false;
        }

        if (!isConstructorWorking){
            fail("StopTime Constructor is not working properly");
        }
    }
}