package org.parancoe.servlet;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parancoe.utility.init.InitCache;
import org.parancoe.utility.init.InitLog;

public class InitListener implements ServletContextListener {

  private Log log = LogFactory.getLog(this.getClass().getName());

  public void contextInitialized(ServletContextEvent sce) {
    InitLog initLog = new InitLog();
    InitCache initCache = new InitCache();
    try {
      initLog.init(sce);
      initCache.init(sce);
    }
    catch (Exception ex) {
      log.error("Error : " + ex.toString());
    }
  }

  public void contextDestroyed(ServletContextEvent sce) {

  }


}
