package org.parancoe.plugins.italy;

import javax.annotation.Resource;
import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.test.PluginTest;

public class SanityTest extends PluginTest {

    @Resource(name = "applicationContext-pluginItalyConfig")
    private ApplicationContextPlugin plugin;

    public void testPlugin() throws Exception {
        assertEquals(4, plugin.getFixtureClasses().size());
        assertEquals(Regione.class, plugin.getFixtureClasses().get(0));
        assertEquals(Provincia.class, plugin.getFixtureClasses().get(1));
        assertEquals(Comune.class, plugin.getFixtureClasses().get(2));
        assertEquals(Procura.class, plugin.getFixtureClasses().get(3));
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Regione.class, Provincia.class, Comune.class,
                    Procura.class};
    }
}
