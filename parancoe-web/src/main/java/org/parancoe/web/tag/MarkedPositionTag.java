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
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * Tag implementation: retrieve a marked position from the session.
 * It's intended to be used in conjunction with the {@link MarkPositionTag}.
 *
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public class MarkedPositionTag extends RequestContextAwareTag {

    /**
     * The identifier of the path in which the position has been marked.
     */
    protected String pathId;
    /**
     * A default position, used if the position hasn't been marked.
     */
    protected String defaultUrl;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    @Override
    protected final int doStartTagInternal() throws JspException, IOException {
        final HttpSession session = pageContext.getSession();
        Object value = session.getAttribute(MarkPositionTag.PREFIX + pathId);
        if (value != null) {
            pageContext.getOut().write(value.toString());
        } else {
            if (defaultUrl != null) {
                pageContext.getOut().write(defaultUrl);
            }
        }
        return EVAL_BODY_INCLUDE;
    }
}
