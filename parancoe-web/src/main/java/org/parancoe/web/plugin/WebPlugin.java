package org.parancoe.web.plugin;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.servlet.HandlerInterceptor;

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
    private List<HandlerInterceptor> interceptors = new ArrayList<HandlerInterceptor>();
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

}
