package org.parancoe.web;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;
import org.parancoe.util.BaseConf;

import java.util.Map;
import java.util.HashMap;

public abstract class BaseFormController extends SimpleFormController {
    protected BaseConf conf;

    public BaseConf getConf() {
        return conf;
    }

    public void setConf(BaseConf conf) {
        this.conf = conf;
    }

    public ModelAndView genericError(Exception e) {
        getLogger().error(e.getMessage(), e);
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("messageCode", e.getMessage());
        params.put("exception", e);
        return new ModelAndView("genericError", params);
    }

    public ModelAndView genericError(String messageCode) {
        getLogger().error(messageCode);
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("messageCode", messageCode);
        return new ModelAndView("genericError", params);
    }

    public abstract Logger getLogger();
}
