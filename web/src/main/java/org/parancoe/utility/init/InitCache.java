// Copyright 2006 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.utility.init;

import javax.servlet.ServletContextEvent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import javax.servlet.ServletContext;

import java.util.Properties;
import java.io.FileInputStream;

import com.opensymphony.oscache.web.ServletCacheAdministrator;

public class InitCache {
  private Log log = LogFactory.getLog(this.getClass().getName());

 /**
  * <p>Inizializza la OSCache</p>
  * @param sce ServletContextEvent evento del contesto della servlet
  * @throws ServletException eccezione della servlet
  */
 public void init(ServletContextEvent sce) throws Exception {
   String configPath = sce.getServletContext().getInitParameter("cache-config");
   if (configPath != null) {
     if (!configPath.startsWith("/")) {
       configPath = "/" + configPath;
     }
     try {
       ServletContext sc = sce.getServletContext();
       String configFile = sc.getRealPath(configPath);

       Properties p = new Properties();
       FileInputStream fis = new FileInputStream(configFile);
       p.load(fis);

       ServletCacheAdministrator.getInstance(sc, p);
     }
     catch (Exception e) {
       log.error("[init] InitCache ioException:" + e.getMessage());
       System.out.println(e.getMessage());
     }
   }
   else {
     log.debug("[init] file properties oscache non definito");
   }

 }


}
