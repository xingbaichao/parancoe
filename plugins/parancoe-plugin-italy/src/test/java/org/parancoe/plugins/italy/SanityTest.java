package org.parancoe.plugins.italy;

import org.parancoe.web.test.BaseTest;
import org.parancoe.web.test.PluginTest;
import org.parancoe.web.plugin.Plugin;

public class SanityTest extends PluginTest {

  private Plugin plugin;

  public void setUp() throws Exception {
    super.setUp();
    plugin = (Plugin) ctx.getBean("pluginItalyConfig");
  }

  public void testPlugin() throws Exception {
    assertEquals(4, plugin.getFixtureClasses().size());
    assertEquals(Regione.class, plugin.getFixtureClasses().get(0));
    assertEquals(Provincia.class, plugin.getFixtureClasses().get(1));
    assertEquals(Comune.class, plugin.getFixtureClasses().get(2));
    assertEquals(Procura.class, plugin.getFixtureClasses().get(3));
  }
}
