/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Core.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.util;

import org.apache.commons.configuration.Configuration;

/**
 * This is the base class for accessing the configuration
 *
 * @author <a href="mailto:paolo.dona@seesaw.it">Paolo Dona'</a>
 */
public class BaseConf {

    /**
     * Wraps the commons configuration in order to provide
     * higher level functionality.
     * Filters configuration properties by environment.
     */
    private Configuration developmentConfiguration;
    private Configuration testConfiguration;
    private Configuration productionConfiguration;

    // aggiungere qui i metodi getter per i parametri di configurazione del framework
    // es:
    //
    //  public int getMyParam() {
    //     return getConfiguration().getInt("myparam");
    //  }

    // parancoe configuration parameters

    public String[] getSupportedLanguages() {
        return getConfiguration().getStringArray("supportedLanguages");
    }

    // Injection

    public void setDevelopmentConfiguration(Configuration developmentConfiguration) {
        this.developmentConfiguration = developmentConfiguration;
    }

    public void setTestConfiguration(Configuration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }

    public void setProductionConfiguration(Configuration productionConfiguration) {
        this.productionConfiguration = productionConfiguration;
    }

    // environment utilities
    public static boolean isDevelopment() {
        return getEnv().equalsIgnoreCase("DEVELOPMENT");
    }

    public static boolean isTest() {
        return getEnv().equalsIgnoreCase("TEST");
    }

    public static boolean isProduction() {
        return getEnv().equalsIgnoreCase("PRODUCTION");
    }

    public static String getEnv() {
        return System.getProperty("ENVIRONMENT", "DEVELOPMENT");
    }

    public void forceEnvironment(String env) {
        System.setProperty("ENVIRONMENT", env);
    }

    public Configuration getConfiguration() {
        if (isProduction()) return productionConfiguration;
        if (isTest()) return testConfiguration;
        return developmentConfiguration;
    }
}
