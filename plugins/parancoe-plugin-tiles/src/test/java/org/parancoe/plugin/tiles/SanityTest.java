package org.parancoe.plugin.tiles;

import javax.annotation.Resource;
import org.parancoe.web.plugin.WebPlugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;

public class SanityTest extends PluginTest {

    @Autowired
    @Qualifier("parancoe-plugin-tilesPluginConfig")
    private WebPlugin plugin;
    @Resource
    private TilesConfigurer tilesConfigurer;
    @Resource
    private ViewResolver viewResolver;
    
    /* test everything has been loaded properly */
    public void testSanity() {
        assertNotNull(plugin);
        assertNotNull(tilesConfigurer);
        assertNotNull(viewResolver);
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }
    
}