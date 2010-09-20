#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.dao;

import ${package}.core.po.Authority;
import org.lambico.dao.generic.Dao;
import org.lambico.dao.generic.GenericDao;

/**
 * The DAO interface for the User entity.
 *
 * @author <a href="mailto:enrico.giurin@gmail.com">Enrico Giurin</a>
 * @version ${symbol_dollar}Revision: 2c4d46632a31 ${symbol_dollar}
 */
@Dao(entity = Authority.class)
public interface AuthorityDao extends GenericDao<Authority, Long> {

    /**
     * Find the autority using the role name.
     *
     * @param authority The role name.
     * @return The autority for the role.
     */
    Authority findByRole(String authority);
}
