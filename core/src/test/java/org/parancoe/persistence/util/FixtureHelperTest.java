package org.parancoe.persistence.util;

import java.math.BigDecimal;
import java.util.Map;

import org.parancoe.test.EnhancedTestCase;
import org.parancoe.util.FixtureHelper;

/**
 * @author Michele Franzin michele.franzin@seesaw.it
 */
public class FixtureHelperTest extends EnhancedTestCase {

    private DemoBean[] expected;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        expected = new DemoBean[5];
        expected[0] = new DemoBean("first one", new Long(356));
        expected[1] = new DemoBean("Demo 1", new Long(6789));
        expected[2] = new DemoBean("àèéìòù", new Long(-980000));
        expected[3] = new DemoBean("Demo3", new Long(98000));
        expected[4] = new DemoBean("Demo#2", new Long(-9800));
    }

    public void testShouldNotFailWrongFixtureDir() throws Exception {
        Map<Class, Object[]> result = FixtureHelper.loadFixturesFromResource("nonExistant/",
                new Class[] { DemoBean.class });
        assertTrue("Le fixture non sono vuote", result.isEmpty());
    }

    public void testShouldNotFailIfMissingFixtureFile() throws Exception {
        Map<Class, Object[]> result = FixtureHelper.loadFixturesFromResource("testdata/",
                new Class[] { BigDecimal.class });
        assertTrue("Le fixture non sono vuote", result.isEmpty());
    }

    public void testShouldNotFailIfEmptyDocs() throws Exception {
        Map<Class, Object[]> result = FixtureHelper.loadFixturesFromResource("emptyDir/", new Class[] {});
        assertTrue("Le fixture non sono vuote", result.isEmpty());
    }

    public void testJarLoading() throws Exception {
        // TODO Testare il acricamento di una risorsa nei JAR
    }

    public void testBeanLoading() throws Exception {
        Map<Class, Object[]> objects = FixtureHelper.loadFixturesFromResource("testdata/",
                new Class[] { DemoBean.class });
        assertNotNull("Non ha ritornato la mappa di fixtures", objects);
        assertEquals("Non carica tutti i beans", 1, objects.size());
        assertTrue("Non crea istanze di " + DemoBean.class.getCanonicalName(), objects
                .containsKey(DemoBean.class));
        DemoBean[] result = (DemoBean[]) objects.get(DemoBean.class);
        assertEquals("Non carica tutti i beans", 5, result.length);
        for (int i = 0; i < 5; i++) {
            assertEquals("Non ha caricato correttamente il beans numero " + i, result[i], expected[i]);
        }
    }

}
