package org.parancoe.web;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.apache.log4j.Logger;
import org.parancoe.util.BaseConf;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class BaseFormController extends SimpleFormController {
    protected BaseConf conf;

    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        // @TODO: sistemare il property editor di default per le date
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

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
