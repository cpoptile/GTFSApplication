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


/**
 * Subject interface that is used by GTFS data set, has yet
 * to be implemented
 */
public interface Subject {

    /**
     * Attaches an observer to the subject
     *
     * @param observer The observer being attached
     */
    public void attach(Observer observer);

    /**
     * Detached an observe from the subject
     *
     * @param observer The observer being detached
     */
    public void detach(Observer observer);

    /**
     * Notifies the observers
     */
    public void notifyObservers();

}