package org.parancoe.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Paolo Dona paolo.dona@seesaw.it
 */
public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class);
    private static final byte[] UTF8_PREAMBLE = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final String UTF8_UNICODE_PREAMBLE = "\ufeff";

    public static List<String> convertToNameValueList(Map map) {
        List<String> result = new ArrayList<String>();
        for (String key : (Iterable<String>) map.keySet()) {
            Object tmp = map.get(key);
            if (tmp instanceof String[]) {
                String[] values = (String[]) tmp;
                for (String value : values) result.add(key + "=" + value);
            } else if (tmp instanceof String) {
                String value = (String) tmp;
                result.add(key + "=" + value);
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
        try {
            return IOUtils.toByteArray(new ClassPathResource(classpathResource).getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la risorsa binaria '" + classpathResource
                    + "' dal classpath.");
        }
    }

    /**
     * Carica un file testuale dal classpath
     *
     * @param classpathResource
     * @return
     */
    public static String loadString(String classpathResource) {
        try {
            String result = FileUtils.readFileToString(new ClassPathResource(classpathResource).getFile(), "UTF-8");;
            return stripUTF8preamble(result);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la risorsa testuale '" + classpathResource
                    + "' dal classpath.");
        }
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
        return b[0] == UTF8_PREAMBLE[0] && b[1] == UTF8_PREAMBLE[1] && b[2] == UTF8_PREAMBLE[2];
    }

    public static boolean hasUTF8preamble(String s) {
        return s.startsWith(UTF8_UNICODE_PREAMBLE);
    }
}
