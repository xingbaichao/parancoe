#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.List;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;

public class SampleDaoTest extends PluginTest {
    
    @Autowired
    private SampleDao sampleDao;
    
    /* test everything has been loaded properly */
    public void testFindAll() {
        List<Sample> results = sampleDao.findAll();
        assertSize(2, results);
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Sample.class};
    }
    
}