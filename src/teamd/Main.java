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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class that launches our FXML class
 */
public class Main extends Application {

    static final int MAIN_STAGE_WIDTH = 192;
    static final int MAIN_STAGE_HEIGHT = 200;
    static final int SECONDARY_STAGE_WIDTH = 700;
    static final int SECONDARY_STAGE_HEIGHT = 300;
    static final int MAIN_STAGE_X = 845;
    static final int MAIN_STAGE_Y = 160;
    static final int SECONDARY_STAGE_X = 600;
    static final int SECONDARY_STAGE_Y = 420;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader primaryLoader = new FXMLLoader();
        FXMLLoader dataVisualizerLoader = new FXMLLoader();

        Parent root = primaryLoader.load(getClass().getResource("GTFSView.fxml").openStream());
        primaryStage.setTitle("GTFS Manager");
        primaryStage.setScene(new Scene(root, MAIN_STAGE_WIDTH, MAIN_STAGE_HEIGHT));

        Stage dataVisualizerStage = new Stage();
        Parent dataVisualizerRoot = dataVisualizerLoader.load(getClass().getResource(
                "DataVisualizer.fxml").openStream());
        dataVisualizerStage.setTitle("Data Visualizer");
        dataVisualizerStage.setScene(new Scene(dataVisualizerRoot,
                SECONDARY_STAGE_WIDTH, SECONDARY_STAGE_HEIGHT));
        primaryStage.setX(MAIN_STAGE_X);
        primaryStage.setY(MAIN_STAGE_Y);
        dataVisualizerStage.setX(SECONDARY_STAGE_X);
        dataVisualizerStage.setY(SECONDARY_STAGE_Y);

        Controller controller = primaryLoader.getController();
        DataVisualizer dataVisualizer = dataVisualizerLoader.getController();
        controller.setDataVisualizer(dataVisualizer);
        dataVisualizer.setStage(dataVisualizerStage);

        primaryStage.show();
        dataVisualizerStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
