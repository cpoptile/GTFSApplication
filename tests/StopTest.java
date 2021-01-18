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
import teamd.Stop;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Class containing tests for the Stop class
 */
class StopTest{
    Stop stop;

    /**
     * Executed before each test
     */
    @BeforeEach
    void setUp() {
        stop = new Stop("6712", "STATE & 5101 #6712",
                "", 43.0444475, -87.9779369);
    }

    /**
     * Executed after each test
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Intended to test that the constructor is working as it should
     *
     * @author Nigel Nelson
     */
    @Test
    public void testValidConstructor(){
        boolean isConstructorWorking = true;
        if (stop.getStopId().equals(6712)) {
            isConstructorWorking = false;
        } else if(!stop.getStopName().equals("STATE & 5101 #6712")){
            isConstructorWorking = false;
        } else if(!stop.getStopDesc().equals("")){
            isConstructorWorking = false;
        } else if(stop.getStopLat() != 43.0444475){
            isConstructorWorking = false;
        } else if(stop.getStopLon() != -87.9779369){
            isConstructorWorking = false;
        }
        if (!isConstructorWorking){
            fail("Stop Constructor is not working properly");
        }
    }

}