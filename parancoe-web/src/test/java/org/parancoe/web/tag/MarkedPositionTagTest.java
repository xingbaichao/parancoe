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
import org.springframework.mock.web.MockHttpServletResponse;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Lucio Benfante <lucio@benfante.com>
 * @author michele franzin <michele at franzin.net>
 */
public class MarkedPositionTagTest extends BaseTagTest {

    /**
     * Test of doStartTagInternal method, of class MarkedPositionTag.
     */
    @Test
    public void doStartTagInternal() throws Exception {
        String pathId = "testPathId";
        markPosition(pathId);
        MarkedPositionTag tag = new MarkedPositionTag();
        tag.setPageContext(pageContext);
        tag.setPathId(pathId);
        int result = tag.doStartTag();
        assertThat(result, equalTo(Tag.EVAL_BODY_INCLUDE));
        String output = ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString();
        assertThat(output, equalTo("/test/forward/request/uri?p1=v1&p2=v2&p3=v3"));
    }

    private int markPosition(String pathId) throws JspException {
        MarkPositionTag tag = new MarkPositionTag();
        tag.setPageContext(pageContext);
        tag.setPathId(pathId);
        return tag.doStartTag();
    }
}
