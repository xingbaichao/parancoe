/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.parancoe.basicWebApp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.basicWebApp.Conf;
import org.parancoe.basicWebApp.blo.PersonBo;
import org.parancoe.basicWebApp.dao.PersonBusinessDao;
import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.web.BaseMultiActionController;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jacopo
 */
@Controller
@UrlMapping
public class NewPeopleController {
    
    private static Logger logger = Logger.getLogger(NewPeopleController.class);
    
    @Autowired
    @Qualifier("conf")
    Conf conf;
    
    @Autowired
    private PersonBusinessDao pbdao;    
    
    @Autowired
    private PersonBo personBo;
    
    
    @RequestMapping
    @Transactional(readOnly = true)
    public ModelMap list(HttpServletRequest req, HttpServletResponse res){
        ModelMap params = new ModelMap();
        params.put("people", pbdao.findAll());
        return params;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
}
