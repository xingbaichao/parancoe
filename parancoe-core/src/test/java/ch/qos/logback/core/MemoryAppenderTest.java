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
package ch.qos.logback.core;

import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author michele franzin <michele at franzin.net>
 */
public class MemoryAppenderTest {

    Logger testLogger;
    MemoryAppender inMemoryLogger;

    @Before
    public void setUp() {
        testLogger = LoggerFactory.getLogger(MemoryAppenderTest.class);
        inMemoryLogger = MemoryAppender.getInstance();
        inMemoryLogger.clearLogs();
    }

    /**
     * Test configuration.
     */
    @Test
    public void environment() {
        assertThat(inMemoryLogger, is(notNullValue()));
    }

    /**
     * Test configuration.
     */
    @Test
    public void clearLogs() {
        testLogger.error("1");
        testLogger.error("2");
        testLogger.error("3");
        testLogger.error("4");
        assertThat(inMemoryLogger.getLogLines(), hasSize(greaterThan(0)));
        inMemoryLogger.clearLogs();
        assertThat(inMemoryLogger.getLogLines(), hasSize(0));
    }

    /**
     * Test of getLogLines method, of class InMemoryLogger.
     */
    @Test
    public void getLogLines() {
        testLogger.error("debug line");
        testLogger.error("error line");
        List<ILoggingEvent> logLines = inMemoryLogger.getLogLines();
        assertThat(logLines, hasSize(2));
        assertThat(logLines.get(1).getMessage(), equalTo("error line"));
    }

    /**
     * Test of getLogLines(int) method, of class InMemoryLogger.
     */
    @Test
    public void getMaxLogLines() {
        testLogger.error("line 1");
        testLogger.error("line 2");
        testLogger.error("line 3");
        List<ILoggingEvent> logLines = inMemoryLogger.getLogLines(2);
        assertThat(logLines, hasSize(2));
        assertThat(logLines.get(0).getMessage(), equalTo("line 2"));
    }
}