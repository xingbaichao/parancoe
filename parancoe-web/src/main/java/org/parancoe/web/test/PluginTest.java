package org.parancoe.web.test;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import org.lambico.test.spring.hibernate.DBTest;
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

    @Resource
    protected DataSource dataSource;
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] {
            "classpath:org/lambico/spring/dao/hibernate/genericDao.xml",
            "classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml",
            "classpath:org/parancoe/web/parancoeBase.xml",
            "classpath:spring-test.xml",
            "classpath*:parancoe-plugin.xml",
            "classpath*:applicationContext-plugin.xml"};
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
