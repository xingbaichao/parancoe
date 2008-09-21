package org.parancoe.web.plugin;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;
import java.util.ArrayList;

/**
 * Define the basic properties of a plugin
 * Ogni plugin nel suo parancoe-plugin.xml configura un'istanza di questa classe
 * impostando le properties adatte.
 * 
 * @author paolo.dona@seesaw.it
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class WebPlugin {
    private String name = "no name given";
    private List<HandlerInterceptorAdapter> interceptors = new ArrayList<HandlerInterceptorAdapter>();
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HandlerInterceptorAdapter> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HandlerInterceptorAdapter> interceptors) {
        this.interceptors = interceptors;
    }

}
