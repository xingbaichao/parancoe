package org.parancoe.web.plugin;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;
import java.util.ArrayList;

/**
 * Define the basic properties of a plugin
 * Ogni plugin nel suo parancoe-plugin.xml configura un'istanza di questa classe
 * impostando le properties adatte.
 * @author paolo.dona@seesaw.it
 */
public class Plugin {
    private String name = "no name given";
    private List<ContextLoaderListener> contextLoaderListeners = new ArrayList<ContextLoaderListener>();
    private List<HandlerInterceptorAdapter> interceptors = new ArrayList<HandlerInterceptorAdapter>();
    private List<String> fixtureClassNames = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContextLoaderListener> getContextLoaderListeners() {
        return contextLoaderListeners;
    }

    public void setContextLoaderListeners(List<ContextLoaderListener> contextLoaderListeners) {
        this.contextLoaderListeners = contextLoaderListeners;
    }

    public List<HandlerInterceptorAdapter> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HandlerInterceptorAdapter> interceptors) {
        this.interceptors = interceptors;
    }

    public List<String> getFixtureClassNames() {
        return fixtureClassNames;
    }

    public void setFixtureClassNames(List<String> fixtureClassNames) {
        this.fixtureClassNames = fixtureClassNames;
    }

    public List<Class> getFixtureClasses() throws Exception {
        List<Class> classes = new ArrayList<Class>(fixtureClassNames.size());
        for (String className : fixtureClassNames){
            classes.add(Class.forName(className));
        }
        return classes;
    }

}
