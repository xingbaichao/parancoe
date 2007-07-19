package org.parancoe.web.test;

import org.parancoe.util.BaseConf;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerMapping;
import javax.sql.DataSource;
import org.parancoe.test.DBTest;
import org.springframework.context.ApplicationContext;

/**
 * E' la classe base per tutti i test dei plugin.
 * Carica all'avvio tutti i bean configurati in modo da renderli
 * disponibili ai test specifici senza la necessità di tirarli su di volta in volta con
 * getCtx().getBean()
 */
public abstract class PluginTest extends DBTest {

    private static final Logger log = Logger.getLogger(PluginTest.class);

    protected BaseConf conf;

    protected DataSource dataSource;

    protected HandlerMapping handlerMapping;

    @Override
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
    protected ApplicationContext getTestContext() {
        return DefaultPluginTestContextHolder.getTestContext();
    }
}
