/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Web.
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
package org.parancoe.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import org.junit.Test;
import org.parancoe.web.test.TagTest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class MarkedPositionTagTest extends TagTest {

    /**
     * Test of doStartTagInternal method, of class MarkedPositionTag.
     */
    @Test
    public void testDoStartTagInternal() throws Exception {
        String pathId = "testPathId";
        markPosition(pathId);
        resetRequestAndResponse();
        MarkedPositionTag tag = new MarkedPositionTag();
        tag.setPageContext(this.pc);
        tag.setPathId(pathId);
        int result = tag.doStartTag();
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        String output = ((MockHttpServletResponse)this.pc.getResponse()).getContentAsString();        
        assertEquals("/test/forward/request/uri?p1=v1&p2=v2&p3=v3", output);
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                    "classpath:org/lambico/spring/dao/hibernate/genericDao.xml",
                    "classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml",
                    "classpath:org/parancoe/web/parancoeBase.xml",
                    "classpath:spring-test.xml"};
    }

    private void markPosition(String pathId) throws JspException {
        MarkPositionTag tag = new MarkPositionTag();
        tag.setPageContext(this.pc);
        tag.setPathId(pathId);
        int result = tag.doStartTag();
    }
    
}
