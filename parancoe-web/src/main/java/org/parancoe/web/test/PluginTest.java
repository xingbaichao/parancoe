package org.parancoe.web.test;

import javax.servlet.ServletContext;
import org.parancoe.util.BaseConf;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import org.parancoe.test.DBTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * E' la classe base per tutti i test dei plugin.
 * Carica all'avvio tutti i bean configurati in modo da renderli
 * disponibili ai test specifici senza la necessità di tirarli su di volta in volta con
 * getCtx().getBean()
 */
public abstract class PluginTest extends DBTest {

    private static final Logger log = Logger.getLogger(PluginTest.class);

    @Autowired
    protected BaseConf conf;

    @Autowired
    protected DataSource dataSource;
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:org/parancoe/persistence/dao/generic/genericDao.xml", "classpath:org/parancoe/persistence/applicationContextBase.xml","classpath:org/parancoe/web/parancoeBase.xml", "classpath:spring-test.xml", "classpath*:parancoe-plugin.xml", "classpath*:applicationContext-plugin.xml"};
    }
    
    @Override
    protected ConfigurableApplicationContext createApplicationContext(
            String[] locations) {
        FileSystemResourceLoader rl = new FileSystemResourceLoader();
        ServletContext servletContext = new MockServletContext(rl);
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setServletContext(servletContext);
        context.setConfigLocations(locations);
        context.refresh();
        return context;
    }
    
}
