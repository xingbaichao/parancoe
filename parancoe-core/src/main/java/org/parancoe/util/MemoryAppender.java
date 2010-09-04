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
package org.parancoe.util;

import java.io.StringWriter;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

public class MemoryAppender extends WriterAppender {
    private static StringWriter buffer = new StringWriter();
    private static MemoryAppender instance;
    private static long _maxBufferSize = 1000000; // numero di caratteri ammessi nel buffer 2 Mbyte

    public MemoryAppender() {
        super(new PatternLayout(), buffer);
        setImmediateFlush(true);
        instance = this;
    }

    public MemoryAppender(Layout layout) {
        super(layout, buffer);
        instance = this;
    }

    public static String getFullLog() {
        return buffer.toString();
    }

    public static String getLastNLines(int n) {
        String[] lines = StringUtils.split(getFullLog(), '\n');
        if (lines.length <= n) return getFullLog();
        String[] interestingLines = (String[]) ArrayUtils.subarray(lines, lines.length - n, lines.length);
        return StringUtils.join(interestingLines, '\n');
    }

    public static void clean() {
        if (instance != null) {
            buffer = new StringWriter();
            instance.setWriter(buffer);
            instance.writeHeader();
        }
    }

    public static long getMaxBufferSize() {
        return _maxBufferSize;
    }

    public static void setMaxBufferSize(long maxBufferSize) {
        if (maxBufferSize < 100)
            throw new RuntimeException("MemoryAppender.setMaxBufferSize(): La dimensione del buffer non puo essere minore di 100 (trovato " + maxBufferSize + ")");
        _maxBufferSize = maxBufferSize;
    }

    /**
     * svuoto il log se è troppo grande
     * in modo da non avere OutOfMemoryError
     */
    public void append(LoggingEvent loggingEvent) {
        if (getFullLog().length() >= getMaxBufferSize()) {
            clean();
        }
        super.append(loggingEvent);
    }
}