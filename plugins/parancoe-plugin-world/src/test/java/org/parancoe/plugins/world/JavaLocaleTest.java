/*
 * JavaLocaleTest.java
 * JUnit based test
 *
 * Created on 18 luglio 2007, 17.57
 */

package org.parancoe.plugins.world;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import junit.framework.TestCase;

/**
 *
 * @author lucio
 */
public class JavaLocaleTest extends TestCase {

    public JavaLocaleTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLocaleToYML() {
/*
        Locale[] locales = Locale.getAvailableLocales();
        Arrays.sort(locales, new Comparator<Locale>() {
        public int compare(Locale arg0, Locale arg1) {
        return arg0.getCountry().compareTo(arg1.getCountry());
        }
        });
        for (Locale l : locales) {
        if (!"".equals(l.getCountry())) {
        System.out.println("- &Country-" + l.getCountry());
        System.out.println("  isoCode: " + l.getCountry());
        System.out.println("  languageIsoCode: " + l.getLanguage());
        System.out.println("  languageVariant: " + ("".equals(l.getVariant())?"null":l.getVariant()));
        System.out.println("  localName: " + l.getDisplayCountry(l));
        System.out.println("  englishName: " + l.getDisplayCountry(Locale.ENGLISH));
        System.out.println("  continent: null");
        }
        }
         */
    }
}
