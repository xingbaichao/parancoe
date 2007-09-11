package org.parancoe.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Paolo Dona paolo.dona@seesaw.it
 * @author Michele Franzin paolo.dona@seesaw.it
 */
public class Utils {

    private static final Logger logger =
            Logger.getLogger(Utils.class);

    private static final byte[] UTF8_PREAMBLE =
            new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    private static final String UTF8_UNICODE_PREAMBLE = "﻿";

    @SuppressWarnings(value = "unchecked")
    public static List<String> convertToNameValueList(Map map) {
        return convertToNameValueList(map, false);
    }

    @SuppressWarnings(value = "unchecked")
    public static List<String> convertToNameValueList(Map map, boolean urlEncode) {
        List<String> result = new ArrayList<String>();
        for (String key : (Iterable<String>) map.keySet()) {
            Object tmp = map.get(key);
            if (tmp instanceof String[]) {
                String[] values = (String[]) tmp;
                for (String value : values) {
                    if (urlEncode) {
                        try {
                            result.add(key + "=" +
                                    java.net.URLEncoder.encode(value, "UTF-8"));
                        } catch (UnsupportedEncodingException ex) {
                            logger.warn("Your OS doesn't support UTF-8, so we can encode",
                                    ex);
                            result.add(key + "=" + value);
                        }
                    } else {
                        result.add(key + "=" + value);
                    }
                }
            } else if (tmp instanceof String) {
                String value = (String) tmp;
                if (urlEncode) {
                    try {
                        result.add(key + "=" +
                                java.net.URLEncoder.encode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException ex) {
                        logger.warn("Your OS doesn't support UTF-8, so we can encode",
                                ex);
                        result.add(key + "=" + value);
                    }
                } else {
                    result.add(key + "=" + value);
                }
            }
        }
        return result;
    }

    /**
     * Carica un file binario dal classpath
     *
     * @param classpathResource
     * @return
     */
    public static byte[] loadBinary(String classpathResource) {
        return loadBinary(new ClassPathResource(classpathResource));
    }

    public static byte[] loadBinary(Resource classpathResource) {
        InputStream stream = null;
        try {
            return unsafeLoadBinary(stream = classpathResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la risorsa binaria '" +
                    classpathResource.getDescription() + "' dal classpath.");
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    protected static byte[] unsafeLoadBinary(InputStream stream) throws IOException {
        return IOUtils.toByteArray(stream);
    }

    /**
     * Carica un file testuale dal classpath.<br/> NB: UTF-8 header are
     * removed!
     *
     * @param classpathResource
     *            file relative path
     * @return textual content of resource file
     */
    public static String loadString(String classpathResource) {
        return loadString(new ClassPathResource(classpathResource));
    }

    public static String loadString(Resource classpathResource) {
        InputStream stream = null;
        try {
            return unsafeLoadString(stream = classpathResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la risorsa testuale '" +
                    classpathResource.getDescription() + "' dal classpath.");
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    protected static String unsafeLoadString(InputStream stream) throws IOException {
        byte[] byteResult =
                IOUtils.toByteArray(new InputStreamReader(stream), "UTF-8");
        return stripUTF8preamble(new String(byteResult, "UTF-8"));
    }

    /**
     * Rimuove il preambolo UTF se presente nella stringa<br/>NB: la convesione
     * in array di byte e sucessivo strip della stringa non funziona
     * correttamente perch&agrave;
     * <code>new String(UTF8_PREAMBLE).getBytes() != UTF8_PREAMBLE</code>;
     * alcune info qui:
     * http://mail.python.org/pipermail/python-list/2004-February/250798.html
     */
    public static String stripUTF8preamble(String s) throws UnsupportedEncodingException {
        if (hasUTF8preamble(s)) {
            logger.debug("Rimosso header UTF-8 dalla stringa");
            return s.substring(1);
        }
        return s;
    }

    /**
     * rimuove i 3 bytes di preambolo dagli UTF se presenti
     */
    public static byte[] stripUTF8preamble(byte[] b) {
        if (hasUTF8preamble(b)) {
            logger.debug("Rimosso header UTF-8 dall'array");
            b = ArrayUtils.subarray(b, 3, b.length);
        }
        return b;
    }

    public static boolean hasUTF8preamble(byte[] b) {
        return b[0] == UTF8_PREAMBLE[0] && b[1] == UTF8_PREAMBLE[1] &&
                b[2] == UTF8_PREAMBLE[2];
    }

    public static boolean hasUTF8preamble(String s) {
        return s.startsWith(UTF8_UNICODE_PREAMBLE);
    }
}
