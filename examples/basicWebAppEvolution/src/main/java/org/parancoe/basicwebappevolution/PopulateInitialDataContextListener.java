
package org.parancoe.basicwebappevolution;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.parancoe.plugins.securityevolution.Authority;
import org.parancoe.plugins.securityevolution.Group;
import org.parancoe.plugins.securityevolution.User;

/**
 *
 * @author jacopo
 */
public class PopulateInitialDataContextListener extends org.parancoe.web.PopulateInitialDataContextListener {
    
    public PopulateInitialDataContextListener() {
        clazzToPopulate.add(Authority.class);
        clazzToPopulate.add(Group.class);
        clazzToPopulate.add(User.class);
    }
    
}
