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

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class MarkPositionTag extends RequestContextAwareTag {

    public static final String PREFIX = "MarkPosition_";
    protected String pathId = null;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    @Override
    protected final int doStartTagInternal() throws JspException, IOException {
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final HttpSession session = pageContext.getSession();
        final RequestContext requestContext = this.getRequestContext();
        final String uri = requestContext.getUrlPathHelper().getOriginatingRequestUri(request);
        final String queryString =
                requestContext.getUrlPathHelper().getOriginatingQueryString(request);
        String url = "";
        if (StringUtils.isBlank(queryString)) {
            url = uri;
        } else {
            url = uri + "?" + queryString;
        }
        session.setAttribute(PREFIX + pathId, url);
        return SKIP_BODY;
    }
}
