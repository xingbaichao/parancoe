package org.parancoe.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Utils {
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

    public static byte[] loadBinary(String classpathResource) {
        try {
            return IOUtils.toByteArray(new ClassPathResource(classpathResource).getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la risorsa '" + classpathResource
                    + "' dal classpath.");
        }
    }

    public static String toUTF8(String isoString) {
        String utf8String = null;
        if (null != isoString && !isoString.equals("")) {
            try {
                byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
                utf8String = new String(stringBytesISO, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // As we can't translate just send back the best guess.
                System.out.println("UnsupportedEncodingException is: " + e.getMessage());
                utf8String = isoString;
            }
        } else {
            utf8String = isoString;
        }
        return utf8String;
    }
}
