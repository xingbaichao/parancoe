#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.core.dao;

import ${package}.core.AppBaseTest;
import ${package}.core.po.User;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests on methods of the UserDao.
 *
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public class UserDaoTest extends AppBaseTest {
    @Resource
    private UserDao userDao;

    /**
     * Test findAll.
     */
    public void testFindAll() {
        List<User> result = userDao.findAll();
        assertSize(2, result);
        assertEquals(result.get(0).getUsername(), "parancoe");
        assertEquals(result.get(1).getUsername(), "admin");
    }

}
