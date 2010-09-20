#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import ${package}.core.po.Authority;
import ${package}.core.po.User;

/**
 * A context listener that initialize the database of the application (if not
 * initialized yet).
 *
 * @author Jacopo Murador
 * @author Lucio Benfante
 */
public class PopulateInitialDataContextListener extends org.parancoe.web.PopulateInitialDataContextListener {

    public PopulateInitialDataContextListener() {
        // Add here to the clazzToPopulate collection the entity classes you need to populate
        clazzToPopulate.add(Authority.class);
        clazzToPopulate.add(User.class);
    }
}
