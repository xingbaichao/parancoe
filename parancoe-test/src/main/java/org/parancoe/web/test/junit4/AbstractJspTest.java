/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Test.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.parancoe.web.test.junit4;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import org.parancoe.web.test.junit4.AbstractControllerTest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * E' la classe base per tutti i test che necessitano di contesti (ad esempio i tag)
 *
 * @author michele franzin <michele at franzin.net>
 */
public abstract class AbstractJspTest extends AbstractControllerTest {

    protected PageContext pageContext;
    protected RequestContext requestContext;

    @BeforeTransaction
    public void prepareContexts() {
        resetContexts();
    }

    @AfterTransaction
    public void clearContexts() {
        pageContext = null;
        requestContext = null;
    }

    /**
     * Reset all contexts.
     */
    protected void resetContexts() {
        GenericWebApplicationContext context = (GenericWebApplicationContext) applicationContext;
        ServletContext servletContext = context.getServletContext();
        pageContext = new MockPageContext(servletContext, request, response);
        requestContext = new JspAwareRequestContext(pageContext);
        pageContext.setAttribute(RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE,
                requestContext);
    }
}
