/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Core.
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
package ch.qos.logback.core.read;

import java.util.List;

/**
 * inspectable circular buffer appender
 *
 * @author michele franzin <michele at franzin.net>
 */
public class InspectableCyclicBufferAppender<E> extends CyclicBufferAppender<E> {

    public List<E> getLogLines() {
        return cb.asList();
    }

    public List<E> getLogLines(final int max) {
        List<E> lines = cb.asList();
        return lines.size() <= max ? lines : lines.subList(lines.size() - max, lines.size());
    }

    public void clearLogs() {
        cb.clear();
    }
}
