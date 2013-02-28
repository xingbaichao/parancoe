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

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Fake sender that logs and discard emails
 *
 * @author michele franzin <michele at franzin.net>
 */
public class LoggingMailSender extends JavaMailSenderImpl {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMailSender.class);

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) {
        if (mimeMessages != null) {
            for (MimeMessage mimeMessage : mimeMessages) {
                try {
                    logger.info("from: {}", (Object[]) mimeMessage.getFrom());
                    logger.info("to: {}", (Object[]) mimeMessage.getRecipients(
                            Message.RecipientType.TO));
                    logger.info("cc: {}", (Object[]) mimeMessage.getRecipients(
                            Message.RecipientType.CC));
                    logger.info("bcc: {}", (Object[]) mimeMessage.getRecipients(
                            Message.RecipientType.BCC));
                    logger.info("subject: {}", mimeMessage.getSubject());
                    logger.info("content: {}", mimeMessage.getContent().toString());
                    logger.info("content type: {}", mimeMessage.getContentType());
                } catch (Exception ex) {
                    logger.error("Can't get message content", ex);
                }
            }
        }
    }
}
