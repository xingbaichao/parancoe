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
