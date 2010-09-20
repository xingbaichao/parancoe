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

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

    /* returns all web plugins */
    public Collection<WebPlugin> getWebPlugins() {
        Map<String, WebPlugin> pluginMap = ctx.getBeansOfType(WebPlugin.class);
        return pluginMap.values();
    }
    
    /* returns all application context plugins */
    public Collection<ApplicationContextPlugin> getApplicationContextPlugins() {
        Map<String, ApplicationContextPlugin> pluginMap = ctx.getBeansOfType(ApplicationContextPlugin.class);
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
        for (ApplicationContextPlugin plugin : getApplicationContextPlugins()) {
            for (ContextLoaderListener listener : plugin.getContextLoaderListeners()) {
                try {
                    listener.contextDestroyed(evt);
                } catch (Exception e) {
                    log.error("error in contextDestroyed for plugin '" + plugin.getName() + "'", e);
                }
            }
        }
    }

    /**
     * invokes contextInitialized for every registered plugin
     * 
     * @param evt
     */
    public boolean invokePluginPreHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        for (WebPlugin plugin : getWebPlugins()) {
            for (HandlerInterceptor interceptor : plugin.getInterceptors()) {
                try {
                    boolean result = interceptor.preHandle(req, res, handler);
                    if (result == false)
                        return false;
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
    
    public void initApplicationContextPlugins() {
        Collection<ApplicationContextPlugin> plugins = new PluginHelper(ctx).getApplicationContextPlugins();
        log.info("Loaded " + plugins.size() + " plugins");
        for (ApplicationContextPlugin plugin : plugins) {
            log.info("   - " + plugin.getName());
        }
    }

}
