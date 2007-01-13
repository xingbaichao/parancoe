package org.parancoe.util;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;

import java.io.StringWriter;

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

    public static String getLastNLines(int n){
        String[] lines = StringUtils.split(getFullLog(),'\n');
        if (lines.length<=n) return getFullLog();
        String[] interestingLines = (String[]) ArrayUtils.subarray(lines,lines.length -n, lines.length);
        return StringUtils.join(interestingLines,'\n');
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