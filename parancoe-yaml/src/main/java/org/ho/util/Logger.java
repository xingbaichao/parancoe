/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml - DISCONTINUED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ho.util;

// TODO SOSTITUIRE CON LOG4J
public class Logger {

    public enum Level {
        INFO, WARN, NONE
    }

    Level level = Level.WARN;

    public Logger() {
    }

    public Logger(Level level) {
        this.level = level;
    }

    public void info(Object message) {
        if (level == Level.INFO)
            System.out.println("INFO: " + message);
    }

    public void warn(Object message) {
        if (level != Level.NONE)
            System.err.println("WARNING: " + message);
    }

}
