package ${package};

import org.parancoe.web.plugin.Plugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends PluginTest {

    @Autowired
    @Qualifier("${artifactId}PluginConfig")
    private Plugin plugin;
    
    @Autowired
    private SampleContextListener sampleContextListener;
    
    @Autowired
    private SampleInterceptor sampleInterceptor;
    
    @Autowired
    private SampleDao sampleDao;
    
    /* test everything has been loaded properly */
    public void testSanity() {
        assertNotNull(plugin);
        assertNotNull(sampleContextListener);
        assertNotNull(sampleInterceptor);
        assertNotNull(sampleDao);
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }
    
}