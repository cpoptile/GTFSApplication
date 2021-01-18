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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamd.Controller;
import teamd.DataVisualizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class that is intended to test the importation of GTFS files
 * from the perspective of the Controller class
 */
class ControllerTest {
    Controller controller;
    Path validGTFS;
    Path invalidGTFS;
    Path emptyGTFS;

    /**
     * Executed before each test
     */
    @BeforeEach
    void setUp() {
        controller = new Controller();


//        DataVisualizer dataVisualizer = controller.getDataVisualizer();
//
//        FXMLLoader dataVisualizerLoader = new FXMLLoader();
//
//        Parent dataVisualizerRoot = null;
//        try {
//            dataVisualizerRoot = dataVisualizerLoader.load(getClass().getResource(
//                    "DataVisualizer.fxml").openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        dataVisualizerStage.setTitle("Data Visualizer");
//        dataVisualizerStage.setScene(new Scene(dataVisualizerRoot,
//                0, 0));
//        dataVisualizer.setStage(dataVisualizerStage);

        validGTFS = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS");
        invalidGTFS = Paths.get(System.getProperty("user.dir"));
        emptyGTFS = Paths.get(System.getProperty("user.dir"), "GTFS_MCTS - Empty");



    }

    /**
     * Intended to test that the importGTFS() method
     * is capable of handling a valid GTFS file
     *
     * @author Nigel Nelson
     */
    @Test
    public void testValidImportGTFS(){
        try {
            controller.importGTFS(new File(String.valueOf(validGTFS.toFile())));
        } catch (IOException e){
            fail("controller is incapable of importing a valid GTFS file");
        }
    }

    /**
     * Intended to test that the importGTFS() method
     * is capable of throwing the correct exception
     * when an invalid GTFS file is passed in
     *
     * @author Nigel Nelson
     */
    @Test
    public void testInvalidImportGTFS(){
        boolean isTestWorking = false;
        try {
            controller.importGTFS(new File(String.valueOf(invalidGTFS.toFile())));
        } catch (IOException e){
            isTestWorking = true;
        }
        if (!isTestWorking){
            fail("controller didn't acknowledge that an invalid GTFS file was passed in");
        }
    }

    /**
     * Intended to test that the importGTFS() method
     * is capable of throwing the correct exception
     * when an empty GTFS file is passed in
     *
     * @author Nigel Nelson
     */
    @Test
    public void testEmptyImportGTFS(){
        boolean isTestWorking = false;
        try {
            controller.importGTFS(new File(String.valueOf(emptyGTFS.toFile())));
        } catch (IOException e){
        } catch (InputMismatchException e){
            isTestWorking = true;
        }
        if (!isTestWorking){
            fail("controller didn't acknowledge that an empty GTFS file was passed in");
        }
    }
}