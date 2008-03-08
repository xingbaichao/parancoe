
package org.parancoe.basicWebApp;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.ServletContextEvent;
import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.security.UserAuthority;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 *
 * @author jacopo
 */
public class PopulateInitialDataContextListener extends org.parancoe.web.PopulateInitialDataContextListener {
    
    public PopulateInitialDataContextListener() {
        clazzToPopulate.add(Authority.class);
        clazzToPopulate.add(User.class);
        clazzToPopulate.add(UserAuthority.class);
        
        
    }
    
}
