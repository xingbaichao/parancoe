
package org.parancoe.basicWebApp;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.User;

/**
 *
 * @author jacopo
 */
public class PopulateInitialDataContextListener extends org.parancoe.web.PopulateInitialDataContextListener {
    
    public PopulateInitialDataContextListener() {
        clazzToPopulate.add(Authority.class);
        clazzToPopulate.add(User.class);
    }
    
}
