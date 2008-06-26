package org.parancoe.plugin.dwr;

import org.parancoe.web.plugin.Plugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends PluginTest {

    @Autowired
    @Qualifier("parancoe-plugin-dwrPluginConfig")
    private Plugin plugin;
        
    /* test everything has been loaded properly */
    public void testSanity() {
        assertNotNull(plugin);
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }
    
}