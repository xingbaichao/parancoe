// Copyright 2006-2007 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
        //changed add order in the list
        expected.add("E=F");
        expected.add("A=B");
        expected.add("C=D");
        
        assertTrue(expected.containsAll(Utils.convertToNameValueList(input)));
    }
}
