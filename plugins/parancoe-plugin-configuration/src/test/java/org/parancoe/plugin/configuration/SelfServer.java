/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Configuration.
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
package org.parancoe.plugin.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Self Server to Start & stop
 *
 * Code help taken from: * <a
 * href="http://ptrthomas.wordpress.com/2009/01/24/how-to-start-and-stop-jetty-revisited/">How to
 * start and stop Jetty – revisited</a>
 *
 *
 * <a
 * href="http://www.codeproject.com/Articles/128145/Run-Jetty-Web-Server-Within-Your-Application">Run
 * Jetty Web Server Within Your Application</a>
 *
 * @author Arjun Dhar
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public class SelfServer {

    private static Logger log = LoggerFactory.getLogger(SelfServer.class);
    private Server server;
    private int port = 9091;
    private int stopPort = 9092;
    private String contextPath = "/testContext";
    private String webPath = "src/test/webapp/WEB-INF";
    private String host = "127.0.0.1";
    private WebAppContext context;

    public SelfServer() {
    }

    public SelfServer(String host, int port, String contextPath, String webPath) {
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
        this.webPath = webPath;
    }

    /**
     * This is a Blocking call and will wait till the server is Started
     *
     * @throws Exception
     */
    public void start() throws Exception {
        Thread t = new Thread() {
            public void run() {
                server = new Server();
                SocketConnector connector = new SocketConnector();
                connector.setPort(port);
                server.setConnectors(new Connector[]{connector});

                context = new WebAppContext();
                context.setServer(server);
                context.setContextPath(contextPath);
                //context.setWar(warFilePath);

                //Note: Set WAR assumes all resources etc in place like genuine WAR, 
                //in our case resources scattered across so use the following instead:
                context.setResourceBase("src/main/resources");
                context.setDescriptor(webPath + "/web.xml");
                server.addHandler(context);

                Thread monitor = new MonitorThread(host, stopPort, server);
                monitor.setDaemon(true);
                monitor.start();

                try {
                    server.start();
                    server.setStopAtShutdown(true);
                    server.join();
//                    while (context.isStarting()) {
//                        Thread.sleep(1000);
//                    };
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        t.setDaemon(true);
        t.start();

        while (server == null || !server.isStarted()) {/* Block till started */
            log.info("Waiting server starting.");
            Thread.sleep(1000);
        }

        log.info("[start] Started Server @ " + host + ":" + port);
        log.info("[start] Server Ready & Running - " + server.isRunning());
    }

    public void stop() throws Exception {
        Socket s = new Socket(InetAddress.getByName(host), stopPort);
        OutputStream out = s.getOutputStream();
        log.info("[stop] sending jetty stop request @ " + host + ":" + stopPort);
        out.write(("\r\n").getBytes());
        out.flush();
        s.close();

        if (server != null && server.isStarted()) {
            server.stop();
        }
    }

    private static final class MonitorThread extends Thread {

        private ServerSocket socket;
        private Server server;
        private int stopPort;
        private String host;

        public MonitorThread(String host, int stopPort, Server server) {
            this.server = server;
            this.stopPort = stopPort;
            this.host = host;

            setDaemon(true);
            setName("StopMonitor");
            try {
                socket = new ServerSocket(stopPort, 1, InetAddress.getByName(host));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            log.info("[run] Running Jetty Stop Thread");
            Socket accept;
            try {
                accept = socket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(accept.
                        getInputStream()));
                reader.readLine();
                log.info("[run] Stopping embedded Jetty Server");
                server.stop();
                accept.close();
                socket.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
