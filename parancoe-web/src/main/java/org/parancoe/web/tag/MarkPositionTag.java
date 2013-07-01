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
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang.StringUtils;
import org.parancoe.web.util.MarkPositionHelper;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * Tag implementation: mark the position (URL) of a page in the session.
 * It's intended to be used in conjunction with the {@link MarkedPositionTag}.
 *
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public class MarkPositionTag extends RequestContextAwareTag {

    public static final String PREFIX = "MarkPosition_";
    /**
     * The identifier of the path in which the position has been marked.
     */
    protected String pathId = null;
    /**
     * If true, the tag generate full URIs (http://host/..etc.)
     */
    protected boolean useFullUri;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public boolean isUseFullUri() {
        return useFullUri;
    }

    public void setUseFullUri(boolean useFullUri) {
        this.useFullUri = useFullUri;
    }

    @Override
    protected final int doStartTagInternal() throws JspException, IOException {
        final HttpSession session = pageContext.getSession();
        ServletRequest request = pageContext.getRequest();
        final RequestContext requestContext = this.getRequestContext();
        final String uri = requestContext.getRequestUri();
        String contextPath = requestContext.getContextPath();
        final String queryString =
                requestContext.getQueryString();
        String url = "";
        if (uri.startsWith(contextPath)) {
            url = uri.substring(contextPath.length());
        } else {
            url = uri;
        }
        if (!StringUtils.isBlank(queryString)) {
            url = url + "?" + queryString;
        }
        if (useFullUri) {
            String port = "";
            if (request.getServerPort() != 80) {
                port = ":"+request.getServerPort();
            }
            url = request.getScheme()+"://"+request.getServerName()+port+requestContext.getContextPath()+url;
        }
        MarkPositionHelper.markPosition(session, pathId, url);
        return SKIP_BODY;
    }
}
