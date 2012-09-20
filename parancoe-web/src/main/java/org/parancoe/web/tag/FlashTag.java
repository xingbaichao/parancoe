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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class FlashTag extends RequestContextAwareTag {

    protected String type = null;

    public String getType() {
        return (this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected final int doStartTagInternal() throws JspException, IOException {
        try {
            Map<String, String> flash = new HashMap<String, String>();
            Map<String, String> flashRequest = (Map<String, String>) pageContext.getRequest().
                    getAttribute("flash");
            if (flashRequest != null) {
                flash.putAll(flashRequest);
            }
            Map<String, String> flashSession = (Map<String, String>) pageContext.getSession().
                    getAttribute("flash");
            if (flashSession != null) {
                flash.putAll(flashSession);
                // And clean the session
                flashSession.remove(type);
            }
            if (flash != null && flash.get(type) != null) {
                // Resolve the message.
                MessageSource messageSource = getMessageSource();
                if (messageSource == null) {
                    throw new JspTagException("No corresponding MessageSource found");
                }
                String msg = "";
                try {
                    Object[] argumentsArray = {};
                    msg = messageSource.getMessage(flash.get(type), argumentsArray,
                            getRequestContext().getLocale());
                } catch (Exception ex) {
                    // If the message is unresolved, use the key as message
                    msg = flash.get(type);
                }

                // Write the message
                writeMessage(msg);

            }
            return EVAL_BODY_INCLUDE;
        } catch (NoSuchMessageException ex) {
            throw new JspTagException(getNoSuchMessageExceptionDescription(ex));
        }
    }

    /**
     * Write the message to the page.
     *
     * @param msg the message to write
     * @throws IOException if writing failed
     */
    protected void writeMessage(String msg) throws IOException {
        pageContext.getOut().write("<div class=\"" + type + "_flash\"><p>" + msg + "</p></div>");
    }

    /**
     * Use the application context itself for default message resolution.
     */
    protected MessageSource getMessageSource() {
        return getRequestContext().getWebApplicationContext();
    }

    /**
     * Return default exception message.
     */
    protected String getNoSuchMessageExceptionDescription(NoSuchMessageException ex) {
        return ex.getMessage();
    }
}
