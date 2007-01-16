package org.parancoe.persistence.util;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.parancoe.util.Utils;


public class UtilsTest extends TestCase {
 public void testConvertToNameValueList(){
        Map input = new HashMap();
        input.put("A","B");
        input.put("C","D");
        input.put("E","F");

        List<String> expected = new ArrayList();
        expected.add("A=B");
        expected.add("C=D");
        expected.add("E=F");
        assertEquals(expected, Utils.convertToNameValueList(input));
    }
}
