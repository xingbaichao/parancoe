/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Core.
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
package org.parancoe.test;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Inspectable mock implementation of JavaMailSender for testing porpouses.
 *
 * @author michele franzin <michele at franzin.net>
 */
public class InspectableTestMailSender extends JavaMailSenderImpl {

    private MimeMessage[] messages = new MimeMessage[0];

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages)
            throws MailException {
        if (mimeMessages != null) {
            messages = new MimeMessage[mimeMessages.length];
            System.arraycopy(mimeMessages, 0, messages, 0, mimeMessages.length);
        }
    }

    public void clearMessages() {
        messages = new MimeMessage[0];
    }

    public MimeMessage[] getMessages() {
        return messages;
    }
}
