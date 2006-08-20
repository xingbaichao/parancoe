package org.parancoe.utility.init;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import javax.servlet.ServletContextEvent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * <p>InitLog</p>
 * <p>Inizializza i log</p>
 * @author NEC
 * @version 1.0
 */
public class InitLog {

  private static final long delay_in_msecs=300000;

  /**
   * <p>Inizializza i log</p>
   * @param sce ServletContextEvent evento del contesto della servlet
   * @throws ServletException eccezione della servlet
   */
  public void init(ServletContextEvent sce) {

    //commons-logging
    Log log = LogFactory.getLog(this.getClass().getName());

    String prefix = sce.getServletContext().getRealPath("/");
    // path del log
    String logPath=prefix+"WEB-INF/log/";
    System.setProperty("webapp.logpath",logPath);
    // file di properties
    String file = sce.getServletContext().getInitParameter("log4j-init-file");
    if(prefix+file != null) {
      log.debug("[init] cerco in: "+prefix+file);
      PropertyConfigurator.configureAndWatch(prefix+file, delay_in_msecs);
    }
    //log4j
    Logger logger = Logger.getLogger(this.getClass().getName());
    logger.debug("[init] init log4j from file: " + file);

  }
}//{c} InitLog
