// Copyright 2006-2007 The Parancoe Team
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
package org.parancoe.persistence.util;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.parancoe.test.DBTest;
import org.parancoe.test.EnhancedTestCase;
import org.parancoe.util.FixtureHelper;
import org.springframework.util.CollectionUtils;

/**
 * @author <a href="mailto:michele.franzin@seesaw.it">Michele Franzin</a>
 * @version $Revision$
 */
public class FixtureHelperTest extends DBTest {

	private DemoBean[] expected;

	@Override
	public void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		expected = new DemoBean[5];
		expected[0] = new DemoBean("first one", new Long(356));
		expected[1] = new DemoBean("Demo 1", new Long(6789));
		expected[2] = new DemoBean("", new Long(-980000));
		expected[3] = new DemoBean("Demo3", new Long(98000));
		expected[4] = new DemoBean("Demo#2", new Long(-9800));
	}

	@SuppressWarnings("unchecked")
	public void testShouldNotFailWrongFixtureDir() throws Exception {
		Set<Class> models = new LinkedHashSet<Class>(CollectionUtils
				.arrayToList(new Class[] { DemoBean.class }));
		Map<Class, Object[]> result = FixtureHelper.loadFixturesFromResource(
				"nonExistant/", models);
		assertTrue("Le fixture non sono vuote", result.isEmpty());
	}

	@SuppressWarnings("unchecked")
	public void testShouldNotFailIfMissingFixtureFile() throws Exception {
		Set<Class> models = new LinkedHashSet<Class>(CollectionUtils
				.arrayToList(new Class[] { BigDecimal.class }));
		Map<Class, Object[]> result = FixtureHelper.loadFixturesFromResource(
				"fixtures/", models);
		assertTrue("Le fixture non sono vuote", result.isEmpty());
	}

	public void testShouldNotFailIfEmptyDocs() throws Exception {
		Map<Class, Object[]> result = FixtureHelper.loadFixturesFromResource(
				"emptyDir/", new LinkedHashSet<Class>());
		assertTrue("Le fixture non sono vuote", result.isEmpty());
	}

	public void testJarLoading() throws Exception {
		// TODO Testare il caricamento di una risorsa nei JAR
	}

	// TODO Completare il test
	// public void testStripUTF8preamble() {
	// byte[] stringWithPreamble = Utils
	// .loadBinary("testdata/UTF8WithPreamble.txt");
	// byte[] stringWithoutPreamble = Utils
	// .loadBinary("testdata/UTF8WithoutPreamble.txt");
	//
	// assertTrue(FixtureHelper.hasUTF8preamble(stringWithPreamble));
	// assertFalse(FixtureHelper.hasUTF8preamble(stringWithoutPreamble));
	//
	// byte[] stripped = FixtureHelper.stripUTF8preamble(stringWithPreamble);
	// assertFalse(FixtureHelper.hasUTF8preamble(stripped));
	// assertEquals(stringWithPreamble.length, stripped.length + 3);
	// }

	// TODO Completare il test
	// public void testPrependUTF8preamble() {
	// byte[] stringWithoutPreamble = Utils
	// .loadBinary("testdata/UTF8WithoutPreamble.txt");
	// byte[] preambled = FixtureHelper
	// .prependUTF8preamble(stringWithoutPreamble);
	// assertTrue(FixtureHelper.hasUTF8preamble(preambled));
	// assertEquals(stringWithoutPreamble.length + 3, preambled.length);
	// }

	@SuppressWarnings("unchecked")
	public void testBeanLoading() throws Exception {
		Set<Class> models = new LinkedHashSet<Class>(CollectionUtils
				.arrayToList(new Class[] { DemoBean.class }));
		Map<Class, Object[]> objects = FixtureHelper.loadFixturesFromResource(
				"fixtures/", models);
		assertNotNull("Non ha ritornato la mappa di fixtures", objects);
		assertEquals("Non carica tutti i beans", 1, objects.size());
		assertTrue("Non crea istanze di " + DemoBean.class.getCanonicalName(),
				objects.containsKey(DemoBean.class));
		DemoBean[] result = (DemoBean[]) objects.get(DemoBean.class);
		assertEquals("Non carica tutti i beans", 5, result.length);
		for (int i = 0; i < 5; i++) {
			assertEquals("Non ha caricato correttamente il beans numero " + i, result[i], expected[i]);
		}
	}

}
