package org.parancoe.example.util;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Some utility methods
 * 
 * @author Lucio Benfante
 */
public class ApplicationContextHolder {

    private static GenericApplicationContext context;
    

    static {
        context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions(
                new String[]{
                    "classpath:org/parancoe/persistence/dao/generic/genericDao.xml",
                    "classpath:org/parancoe/persistence/applicationContextBase.xml",
                    "classpath:database.xml",
                    "classpath:applicationContext.xml"
                });
        context.refresh();
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static void autowireBeanProperties(Object o) {
        context.getBeanFactory().autowireBeanProperties(o,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }
}