package org.parancoe.web.test;

import org.parancoe.test.EnhancedTestCase;
import org.parancoe.util.BaseConf;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;

import javax.sql.DataSource;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.ArrayList;

/**
 * E' la classe base per tutti i test dei plugin.
 * Carica all'avvio tutti i bean configurati in modo da renderli
 * disponibili ai test specifici senza la necessità di tirarli su di volta in volta con
 * getCtx().getBean()
 */
public abstract class PluginTest extends EnhancedTestCase {

    private static final Logger log = Logger.getLogger(PluginTest.class);

    protected static WebApplicationContext ctx = getTestContext();

    protected BaseConf conf;

    protected DataSource dataSource;

    protected HandlerMapping handlerMapping;

    public void setUp() throws Exception {
        super.setUp();
        conf = (BaseConf) ctx.getBean("conf");
        dataSource = (DataSource) ctx.getBean("dataSource");
        handlerMapping = (HandlerMapping) ctx.getBean("handlerMapping");
    }

    /**
     * Creo un webApplicationContext finto
     *
     * @return
     */
    private static WebApplicationContext getTestContext() {
        List<String> config = new ArrayList<String>();
        //generici
        config.add("classpath:org/parancoe/persistence/dao/generic/genericDao.xml");
        config.add("classpath:org/parancoe/web/parancoeBase.xml");

        // test specific
        config.add("classpath:spring-test.xml");

         // load plugin configurations
        config.add("classpath*:parancoe-plugin.xml");

        FileSystemResourceLoader rl = new FileSystemResourceLoader();
        ServletContext servletContext = new MockServletContext(rl);
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setServletContext(servletContext);

        ctx.setConfigLocations(config.toArray(new String[config.size()]));
        ctx.refresh();
        return ctx;
    }
}
