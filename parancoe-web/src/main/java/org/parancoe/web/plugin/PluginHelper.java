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
package org.parancoe.web.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;
import org.parancoe.util.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

/**
 * Questa classe è solo un helper non è configurato in spring
 *
 * @author paolo.dona@seesaw.it
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class PluginHelper {

    private ApplicationContext ctx;
    private Logger log = Logger.getLogger(PluginHelper.class);

    public PluginHelper(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    /*
     * returns all web plugins
     */
    public Collection<WebPlugin> getWebPlugins() {
        Map<String, WebPlugin> pluginMap = ctx.getBeansOfType(WebPlugin.class);
        return pluginMap.values();
    }

    /*
     * returns all application context plugins
     */
    public Collection<ApplicationContextPlugin> getApplicationContextPlugins() {
        Map<String, ApplicationContextPlugin> pluginMap =
                ctx.getBeansOfType(ApplicationContextPlugin.class);
        return pluginMap.values();
    }

    /**
     * invokes contextInitialized for every registered plugin
     *
     * @param evt
     */
    public void invokePluginContextInitialized(ServletContextEvent evt) {
        for (ApplicationContextPlugin plugin : getApplicationContextPlugins()) {
            for (ContextLoaderListener listener : plugin.getContextLoaderListeners()) {
                try {
                    listener.contextInitialized(evt);
                } catch (Exception e) {
                    log.error("error in contextInitialized for plugin '" + plugin.getName() + "'", e);
                }
            }
        }
    }

    /**
     * invokes contextDestroyed for every registered plugin
     *
     * @param evt
     */
    public void invokePluginContextDestroyed(ServletContextEvent evt) {
        // Already destroyed.
//        for (ApplicationContextPlugin plugin : getApplicationContextPlugins()) {
//            for (ContextLoaderListener listener : plugin.getContextLoaderListeners()) {
//                try {
//                    listener.contextDestroyed(evt);
//                } catch (Exception e) {
//                    log.error("error in contextDestroyed for plugin '" + plugin.getName() + "'", e);
//                }
//            }
//        }
    }

    /**
     * invokes contextInitialized for every registered plugin
     *
     * @param evt
     */
    public boolean invokePluginPreHandle(HttpServletRequest req, HttpServletResponse res,
            Object handler) {
        for (WebPlugin plugin : getWebPlugins()) {
            for (HandlerInterceptor interceptor : plugin.getInterceptors()) {
                try {
                    boolean result = interceptor.preHandle(req, res, handler);
                    if (result == false) {
                        return false;
                    }
                } catch (Exception e) {
                    log.error("error in preHandle for plugin '" + plugin.getName() + "'", e);
                }
            }
        }
        return true;
    }

    public void invokePluginPostHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) {
        for (WebPlugin plugin : getWebPlugins()) {
            for (HandlerInterceptor interceptor : plugin.getInterceptors()) {
                try {
                    interceptor.postHandle(request, response, handler, modelAndView);
                } catch (Exception e) {
                    log.error("error in postHandle for plugin '" + plugin.getName() + "'", e);
                }
            }
        }
    }

    public void invokeAfterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception exception) {
        for (WebPlugin plugin : getWebPlugins()) {
            for (HandlerInterceptor interceptor : plugin.getInterceptors()) {
                try {
                    interceptor.afterCompletion(request, response, handler, exception);
                } catch (Exception e) {
                    log.error("error in afterCompletion for plugin '" + plugin.getName() + "'", e);
                }
            }
        }
    }

    public void initWebPlugins() {
        Collection<WebPlugin> plugins = new PluginHelper(ctx).getWebPlugins();
        log.info("Loaded " + plugins.size() + " plugins");
        for (WebPlugin plugin : plugins) {
            log.info("   - " + plugin.getName());
        }
    }

    public void initApplicationContextPlugins(ServletContextEvent evt) throws IOException {
        Collection<ApplicationContextPlugin> plugins = new PluginHelper(ctx).
                getApplicationContextPlugins();
        log.info("Loaded " + plugins.size() + " plugins");
        for (ApplicationContextPlugin plugin : plugins) {
            log.info("   - " + plugin.getName());
            copyJspResources(plugin, evt);
        }
    }

    protected void copyJspResources(ApplicationContextPlugin plugin, ServletContextEvent evt) throws
            IOException {
        List<String> jspResources = plugin.getJspResources();
        if (jspResources.isEmpty()) {
            log.info("No JSP resources to copy for this plugin.");
        } else {
            String jspBasePath = Constants.DEFAULT_JSP_BASE_PATH;
            if (ctx.containsBean(Constants.JSP_BASE_PATH_BEAN_NAME)) {
                jspBasePath = (String) ctx.getBean(Constants.JSP_BASE_PATH_BEAN_NAME);
            }
            String realPath = evt.getServletContext().getRealPath(jspBasePath);
            if (realPath == null) {
                log.warn("Can't copy JSP resources to " + jspBasePath
                        + ". Is the application deployed as a WAR or in an inaccessible location?");
            } else {
                log.info("Copying JSP resources to " + realPath + " ...");
                for (String jspResource : jspResources) {
                    log.info("  Copying " + jspResource + " ...");
                    Resource[] resources = ctx.getResources(jspResource);
                    int i = 0;
                    for (Resource resource : resources) {
                        String resourceURI = resource.getURI().toString();
                        String baseName = extractJspResourceBaseName(resourceURI, plugin.
                                getJspPrefix());
                        String destination = realPath + baseName;
                        File destinationFile = new File(destination);
                        if (destinationFile.exists()) {
                            log.warn("    Not copying " + resourceURI + " to " + destination
                                    + " as it already exists.");
                        } else {
                            log.info("    Copying " + resourceURI + " to " + destination + " .");
                            destinationFile.getParentFile().mkdirs();
                            InputStream in = resource.getInputStream();
                            FileOutputStream out = new FileOutputStream(destinationFile);
                            try {
                                IOUtils.copy(in, out);
                            } finally {
                                IOUtils.closeQuietly(in);
                                IOUtils.closeQuietly(out);
                            }
                        }
                    }
                }
            }
        }
    }

    protected String extractJspResourceBaseName(String resource, String prefix) {
        String result = resource;
        int begin = resource.indexOf(prefix) + prefix.length();
        if (begin >= 0 && begin < resource.length()) {
            result = resource.substring(begin);
        }
        return result;
    }
}
