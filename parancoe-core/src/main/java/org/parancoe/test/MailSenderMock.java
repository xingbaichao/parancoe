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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Mock implementation of JavaMailSender for test porpouse.
 *
 * @author Enrico Giurin
 * @author Lucio Benfante
 *
 */
public class MailSenderMock extends JavaMailSenderImpl {

    private static final Logger logger = LoggerFactory.getLogger(
            MailSenderMock.class);

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages)
            throws MailException {
        if (mimeMessages != null) {
            for (MimeMessage mimeMessage : mimeMessages) {
                try {
                    logger.info(mimeMessage.getContent().toString());
                } catch (Exception ex) {
                    logger.error("Can't get message content", ex);
                }
            }
        }
        if (originalMessages != null) {
            for (Object o : originalMessages) {
                logger.info(o.toString());
            }
        }
    }
}
