/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml.
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

package org.parancoe.yaml;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import junit.framework.TestCase;

import org.ho.util.BiDirectionalMap;

public class IntegrationTests extends TestCase {

    // inner class java beans for testing
    static public class Entry {
        public Entry() {
        }

        String date;

        Receipt[] receipts;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Receipt[] getReceipts() {
            return receipts;
        }

        public void setReceipts(Receipt[] receipts) {
            this.receipts = receipts;
        }
    }

    static public class Entry2 {
        public Entry2() {
        }

        Date date;

        Receipt[] receipts;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Receipt[] getReceipts() {
            return receipts;
        }

        public void setReceipts(Receipt[] receipts) {
            this.receipts = receipts;
        }
    }

    static public class Receipt {
        public Receipt() {
        }

        String store;

        String category;

        String description;

        Double total;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }
    }

    // ==== Utility Functions
    <T> T thereAndBack(T o, String filename, YamlConfig config) {
        try {
            config.dump(o, new File(filename));
            if (config.isMinimalOutput())
                return (T) config.loadStreamOfType(new File(filename), o.getClass()).next();
            return (T) config.loadStream(new File(filename)).next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    interface Validator<T> {
        public void validate(T obj);
    }

    <T> void integrationTest(T obj, String name, Validator<T> validator, YamlConfig config) {
        config.setMinimalOutput(true);
        T result = (T) thereAndBack(obj, "yml/" + name + "_minimal.yml", config);
        validator.validate(result);
        config.setMinimalOutput(false);
        result = (T) thereAndBack(obj, "yml/" + name + ".yml", config);
        validator.validate(result);

    }

    <T> void integrationTest(T obj, String name, Validator<T> validator) {
        integrationTest(obj, name, validator, new YamlConfig());

    }

    // ==== Test Cases

    // public void testJFrame() {
    // final JFrame f = new JFrame("hey");
    // integrationTest(f, "testJFrame",
    // new Validator<JFrame>(){
    // public void validate(JFrame obj){
    // assertEquals("hey", obj.getTitle());
    // }
    // }
    // );
    // }

    public void testCollections() {
        HashSet<Integer> s = new HashSet<Integer>();
        s.add(1);
        s.add(45);
        integrationTest(s, "testCollections", new Validator<HashSet>() {
            public void validate(HashSet s) {
                assertTrue(s.contains(1));
                assertTrue(s.contains(45));
                assertEquals(s.size(), 2);
            }
        });

    }

    public void testCollectionsLong() {
        HashSet<Long> s = new HashSet<Long>();
        s.add(1L);
        s.add(45L);
        integrationTest(s, "testCollectionsLong", new Validator<HashSet>() {
            public void validate(HashSet s) {
                assertTrue(s.contains(new Long(1)));
                assertTrue(s.contains(new Long(45)));
                assertEquals(s.size(), 2);
            }
        });
    }

    public void testCollectionsBoolean() {
        HashSet<Boolean> s = new HashSet<Boolean>();
        s.add(true);
        s.add(false);
        integrationTest(s, "testCollectionsBoolean", new Validator<HashSet>() {
            public void validate(HashSet s) {
                assertTrue(s.contains(true));
                assertTrue(s.contains(false));
                assertEquals(s.size(), 2);
            }
        });

    }

    public void testCollectionString() {
        HashSet<String> s = new HashSet<String>();
        s.add("hello");
        s.add("world");
        integrationTest(s, "testCollectionString", new Validator<HashSet>() {
            public void validate(HashSet s) {
                assertTrue(s.contains("hello"));
                assertTrue(s.contains("world"));
                assertEquals(s.size(), 2);
            }
        });

    }

    public void testCollectionNull() {
        ArrayList s = new ArrayList();
        s.add(null);
        s.add(null);
        integrationTest(s, "testCollectionNull", new Validator<ArrayList>() {
            public void validate(ArrayList s) {
                assertTrue(s.contains(null));
                assertTrue(s.contains(null));
                assertEquals(2, s.size());
            }
        });

    }

    public void testMap() {
        Map map = new HashMap();
        map.put("one", 1);
        map.put("two", 3);
        map.put("three", 4);
        integrationTest(map, "testMap", new Validator<Map>() {
            public void validate(Map map) {
                assertTrue(map.containsKey("one"));
                assertTrue(map.containsKey("two"));
                assertTrue(map.containsKey("three"));
                assertEquals(map.get("one"), 1);
                assertEquals(map.get("two"), 3);
                assertEquals(map.get("three"), 4);
                assertEquals(map.entrySet().size(), 3);
            }
        });
    }

    public void testDimension() {
        final Dimension d = new Dimension(1, 2);
        integrationTest(d, "testDimension", new Validator<Dimension>() {
            public void validate(Dimension d2) {
                assertEquals(d, d2);
            }
        });

    }

    public void testPoint() {
        final Point p = new Point(1, 2);
        integrationTest(p, "testPoint", new Validator<Point>() {
            public void validate(Point p2) {
                assertEquals(p, p2);
            }
        });

    }

    public void testVector() {
        Vector v = new Vector();
        v.add(1);
        v.add(2);
        integrationTest(v, "testVector", new Validator<Vector>() {
            public void validate(Vector v) {
                assertEquals(1, v.get(0));
                assertEquals(2, v.get(1));
                assertEquals(2, v.size());
            }
        });
    }

    public void testArrayList() {
        ArrayList v = new ArrayList();
        v.add(1);
        v.add(2);
        integrationTest(v, "testVector", new Validator<ArrayList>() {
            public void validate(ArrayList v) {
                assertEquals(1, v.get(0));
                assertEquals(2, v.get(1));
                assertEquals(2, v.size());
            }
        });
    }

    public void testReferences() {
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;
        List l1 = new ArrayList(Arrays.asList(new Object[] { one, two, three }));
        List l2 = new ArrayList(Arrays.asList(new Object[] { l1, two, three }));
        integrationTest(l2, "testReferences", new Validator<List>() {
            public void validate(List l2) {
                assertSame(l2.get(1), ((List) l2.get(0)).get(1));
                assertSame(l2.get(2), ((List) l2.get(0)).get(2));
            }
        });

    }

    public void testReferencesLists() {
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;
        List l1 = new ArrayList(Arrays.asList(new Object[] { one, two, three }));
        List l2 = new ArrayList(Arrays.asList(new Object[] { l1, two, three, l1 }));
        integrationTest(l2, "testReferencesLists", new Validator<List>() {
            public void validate(List l2) {
                assertSame(l2.get(0), l2.get(3));
            }
        });
    }

    public void testReferencesMaps() {
        Integer one = 1;
        Map m1 = new HashMap();
        m1.put("one", one);
        Map m2 = new HashMap();
        m2.put("one", one);
        m2.put("m1", m1);
        m2.put("m1again", m1);
        integrationTest(m2, "testReferencesMaps", new Validator<Map>() {
            public void validate(Map m2) {
                assertNotNull(m2);
                assertSame(m2.get("m1"), m2.get("m1again"));
                assertSame(m2.get("one"), ((Map) m2.get("m1")).get("one"));
            }
        });

    }

    public void testArrays() {
        Integer[] a1 = new Integer[] { 1, 2, 3, 4 };
        integrationTest(a1, "testArrays", new Validator<Integer[]>() {
            public void validate(Integer[] a1) {
                assertEquals(a1[0].intValue(), 1);
                assertEquals(a1.length, 4);
                assertEquals(a1[1].intValue(), 2);
                assertEquals(a1[2].intValue(), 3);
                assertEquals(a1[3].intValue(), 4);
            }
        });

    }

    public void testPrimitiveArrays() {
        int[] a1 = new int[] { 1, 2, 3, 4 };
        integrationTest(a1, "testPrimitiveArrays", new Validator<int[]>() {
            public void validate(int[] a1) {
                assertEquals(a1[0], 1);
                assertEquals(a1.length, 4);
                assertEquals(a1[1], 2);
                assertEquals(a1[2], 3);
                assertEquals(a1[3], 4);
            }
        });

    }

    public void testArrayWithinArray() {
        // also tests arrays with various types of objects inside
        String s = "a";
        Person p = new Person();
        p.setName("Mike Dell");
        Object[] a1 = new Object[] { 1, s, 3, 4, new Object[] { s }, p };
        integrationTest(a1, "testArrayWithinArray", new Validator<Object[]>() {
            public void validate(Object[] a1) {
                assertEquals(a1[0], 1);
                assertEquals(((Object[]) a1[4])[0], a1[1]);
            }
        });

    }

    public void testColor() {
        Color c = new Color(2, 3, 4);
        integrationTest(c, "testColor", new Validator<Color>() {
            public void validate(Color c) {
                assertEquals(c.getRed(), 2);
                assertEquals(c.getGreen(), 3);
                assertEquals(c.getBlue(), 4);
            }
        });
    }

    public void testDate() {
        final Date d = new Date(483837474);
        integrationTest(d, "testDate", new Validator<Date>() {
            public void validate(Date d2) {
                assertEquals(d2, d);
            }
        });
    }

    public void testBigDecimal() {
        BigDecimal b = new BigDecimal("4548389958393003940.50409392882737273");
        ArrayList<BigDecimal> l = new ArrayList<BigDecimal>();
        l.add(b);
        integrationTest(l, "testBigDecimal", new Validator<ArrayList<BigDecimal>>() {
            public void validate(ArrayList<BigDecimal> l) {
                assertEquals(l.get(0).toString(), "4548389958393003940.50409392882737273");
            }
        });
    }

    public void testBigInteger() {
        BigInteger b = new BigInteger("454839282837348493932934938438");
        ArrayList<BigInteger> l = new ArrayList<BigInteger>();
        l.add(b);
        integrationTest(l, "testBigInteger", new Validator<ArrayList<BigInteger>>() {
            public void validate(ArrayList<BigInteger> l) {
                assertEquals(l.get(0).toString(), "454839282837348493932934938438");
            }
        });
    }

    public void testJavaBeans() {
        Company c = new Company();
        c.setAssets(new BigDecimal("2000045.40"));
        c.setName("Microsoft Corporation");
        final Person president = new Person();
        president.setName("Tony Hawk");
        president.setAge(40);
        president.setSalary(120000.00);
        president.setNetWorth(new BigDecimal("238943443.00"));
        Person mspresident = new Person();
        mspresident.setName("Mrs. Hawk");
        president.setSpouse(mspresident);
        c.setPresident(president);
        c.setYearsInOperation(12);
        ArrayList<Department> deps = new ArrayList<Department>();
        deps.add(new Department("Engineering"));
        deps.add(new Department("Sales"));
        deps.add(new Department("Marketing"));
        c.setDepartments(deps);
        integrationTest(c, "testJavaBeans", new Validator<Company>() {
            public void validate(Company c) {
                assertEquals(c.getAssets(), new BigDecimal("2000045.40"));
                assertEquals(c.getName(), "Microsoft Corporation");
                assertEquals(c.getPresident().getName(), "Tony Hawk");
                assertEquals(c.getPresident().getAge(), new Integer(40));
                assertEquals(c.getPresident().getSalary(), 120000.00);
                assertEquals(c.getPresident().getNetWorth(), new BigDecimal("238943443.00"));
                assertEquals(c.getPresident().getSpouse().getName(), "Mrs. Hawk");
                assertEquals(c.getYearsInOperation(), 12);
                assertEquals(c.getDepartments().get(0).getName(), "Engineering");
                assertEquals(c.getDepartments().get(1).getName(), "Sales");
                assertEquals(c.getDepartments().get(2).getName(), "Marketing");

            }
        });
    }

    public void testArrayInJavaBean() {
        Company c = new Company();
        c.setAssets(new BigDecimal("2000045.40"));
        c.setName("Microsoft Corporation");
        final Person president = new Person();
        president.setName("Tony Hawk");
        president.setAge(40);
        president.setSalary(120000.00);
        president.setNetWorth(new BigDecimal("238943443.00"));
        Person mspresident = new Person();
        mspresident.setName("Mrs. Hawk");
        president.setSpouse(mspresident);
        c.setPresident(president);
        c.setYearsInOperation(12);
        Department[] deps = new Department[3];
        deps[0] = new Department("Engineering");
        deps[1] = new Department("Sales");
        deps[2] = new Department("Marketing");
        c.setDepartmentArray(deps);
        integrationTest(c, "testArrayInJavaBean", new Validator<Company>() {
            public void validate(Company c) {
                assertEquals(c.getAssets(), new BigDecimal("2000045.40"));
                assertEquals(c.getName(), "Microsoft Corporation");
                assertEquals(c.getPresident().getName(), "Tony Hawk");
                assertEquals(c.getPresident().getAge(), new Integer(40));
                assertEquals(c.getPresident().getSalary(), 120000.00);
                assertEquals(c.getPresident().getNetWorth(), new BigDecimal("238943443.00"));
                assertEquals(c.getPresident().getSpouse().getName(), "Mrs. Hawk");
                assertEquals(c.getYearsInOperation(), 12);
                assertEquals(c.getDepartmentArray()[0].getName(), "Engineering");
                assertEquals(c.getDepartmentArray()[1].getName(), "Sales");
                assertEquals(c.getDepartmentArray()[2].getName(), "Marketing");
                assertEquals(c.getDepartmentArray().length, 3);
            }
        });
    }

    public void testArrayInMap() {
        HashMap map = new HashMap();
        map.put("string", "hello world");
        Department[] deps = new Department[3];
        deps[0] = new Department("Engineering");
        deps[1] = new Department("Sales");
        deps[2] = new Department("Marketing");
        map.put("array", deps);
        integrationTest(map, "testArrayInMap", new Validator<HashMap>() {
            public void validate(HashMap map) {
                assertEquals("hello world", map.get("string"));
                assertEquals("Engineering", ((Department[]) map.get("array"))[0].getName());
                assertEquals("Sales", ((Department[]) map.get("array"))[1].getName());
                assertEquals("Marketing", ((Department[]) map.get("array"))[2].getName());
                assertEquals(3, ((Department[]) map.get("array")).length);

            }
        });
    }

    public void testSimpleValue() {
        Integer i = 1;
        integrationTest(i, "testSimpleValue", new Validator<Integer>() {
            public void validate(Integer i) {
                assertEquals((Integer) 1, i);
            }

        });
    }

    public void testSimpleValueLong() {
        Long l = 1L;
        integrationTest(l, "testSimpleValueLong", new Validator<Long>() {
            public void validate(Long l) {
                assertEquals((Long) 1L, l);
            }

        });
    }

    public void testSimpleValueString() {
        String s = "hello world";
        integrationTest(s, "testSimpleValueString", new Validator<String>() {
            public void validate(String s) {
                assertEquals(s, "hello world");
            }
        });
    }

    public void testStream() throws Exception {
        YamlEncoder enc = new YamlEncoder("yml/testStream.yml");
        enc.writeObject(1);
        enc.writeObject(2);
        enc.writeObject(3);
        enc.close();
        YamlDecoder dec = new YamlDecoder("yml/testStream.yml");
        int one = (Integer) dec.readObject();
        int two = (Integer) dec.readObject();
        int three = (Integer) dec.readObject();
        dec.close();
        assertEquals(1, one);
        assertEquals(2, two);
        assertEquals(3, three);
        try {
            Object thing = dec.readObject();
        } catch (EOFException e) {
        }
    }

    public void testStream2() throws Exception {
        Company c = new Company();
        c.setAssets(new BigDecimal("2000045.40"));
        c.setName("Microsoft Corporation");
        YamlEncoder enc = new YamlEncoder("yml/testStream2.yml");
        enc.setMinimalOutput(false);
        enc.writeObject(c);
        enc.writeObject(c);
        enc.close();
        YamlDecoder dec = new YamlDecoder("yml/testStream2.yml");
        Company one = (Company) dec.readObject();
        Company two = (Company) dec.readObject();
        dec.close();
        assertEquals(one.getName(), "Microsoft Corporation");
        assertEquals(two.getName(), "Microsoft Corporation");
        try {
            Object thing = dec.readObject();
        } catch (EOFException e) {
        }
    }

    public void testMultiLineString() {
        String s = "hello\nworld";
        integrationTest(s, "testMultiLineString", new Validator<String>() {
            public void validate(String s) {
                assertEquals("hello\nworld", s);

            }
        });
    }

    public void testMultiLineStringNested() {
        Map map = new HashMap();
        String s = "hello\nworld";
        map.put("string", s);
        map.put("he", "llo");
        integrationTest(map, "testMultiLineStringNested", new Validator<Map>() {
            public void validate(Map map) {
                assertEquals("hello\nworld", map.get("string"));

            }
        });
    }

    public void testEmptyJavaBeans() {
        ArrayList list = new ArrayList();
        list.add(new Object());
        list.add(new Entry());
        integrationTest(list, "testEmptyJavaBeans", new Validator<ArrayList>() {
            public void validate(ArrayList list) {
                assertEquals(2, list.size());
                assertEquals(Object.class, list.get(0).getClass());
                assertEquals(Entry.class, list.get(1).getClass());
            }
        });

    }

    public void testWebSiteExample() throws Exception {
        String yamlText = "---\n" + "date: 11/30/2005\n" + "receipts:\n" + "    -\n"
                + "        store: la madeleine\n" + "        category: \"dining out: lunch\"\n"
                + "        total: 13.14\n" + "---\n" + "date: 12/1/2005\n" + "receipts:\n" + "    -\n"
                + "        store: sushi me\n" + "        category: \"dining out: lunch\"\n"
                + "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Map map = (Map) dec.readObject();
        assertEquals("11/30/2005", map.get("date"));
        List list = (List) map.get("receipts");
        assertEquals(1, list.size());
        Map rect = (Map) list.get(0);
        assertEquals("la madeleine", rect.get("store"));
        assertEquals("dining out: lunch", rect.get("category"));
        assertEquals(13.14, rect.get("total"));
        map = (Map) dec.readObject();
        list = (List) map.get("receipts");
        assertEquals(1, list.size());
        rect = (Map) list.get(0);
        assertEquals("sushi me", rect.get("store"));
        assertEquals("dining out: lunch", rect.get("category"));
        assertEquals(5.60, rect.get("total"));
        dec.close();

    }

    public void testWebSiteExampleJavaBeans() throws Exception {
        String yamlText = "---\n" + "date: 11/30/2005\n" + "receipts:\n" + "    -\n"
                + "        store: la madeleine\n" + "        category: \"dining out: lunch\"\n"
                + "        total: 13.14\n" + "---\n" + "date: 12/1/2005\n" + "receipts:\n" + "    -\n"
                + "        store: sushi me\n" + "        category: \"dining out: lunch\"\n"
                + "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Entry entry = dec.readObjectOfType(Entry.class);
        assertEquals("11/30/2005", entry.getDate());
        Receipt[] receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("la madeleine", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(13.14, receipts[0].getTotal());
        entry = dec.readObjectOfType(Entry.class);
        assertEquals("12/1/2005", entry.getDate());
        receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("sushi me", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(5.60, receipts[0].getTotal());
        dec.close();
    }

    public void testWebSiteExampleSameLineOpenMap() throws Exception {
        String yamlText = "---\n" + "date: 11/30/2005\n" + "receipts:\n" + "    -   store: la madeleine\n"
                + "        category: \"dining out: lunch\"\n" + "        total: 13.14\n" + "---\n"
                + "date: 12/1/2005\n" + "receipts:\n" + "    -   store: sushi me\n"
                + "        category: \"dining out: lunch\"\n" + "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Entry entry = dec.readObjectOfType(Entry.class);
        assertEquals("11/30/2005", entry.getDate());
        Receipt[] receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("la madeleine", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(13.14, receipts[0].getTotal());
        entry = dec.readObjectOfType(Entry.class);
        assertEquals("12/1/2005", entry.getDate());
        receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("sushi me", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(5.60, receipts[0].getTotal());
        dec.close();
    }

    public void testWebSiteExampleFreeStrings() throws Exception {
        String yamlText = "---\n" + "date: 11/30/2005\n" + "receipts:\n" + "    -   store: la madeleine\n"
                + "        category: dining out: lunch\n" + "        total: 13.14\n" + "---\n"
                + "date: 12/1/2005\n" + "receipts:\n" + "    -   store: sushi me\n"
                + "        category: dining out: lunch\n" + "        total: 5.60";
        YamlDecoder dec = new YamlDecoder(new StringBufferInputStream(yamlText));
        Entry entry = dec.readObjectOfType(Entry.class);
        assertEquals("11/30/2005", entry.getDate());
        Receipt[] receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("la madeleine", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(13.14, receipts[0].getTotal());
        entry = dec.readObjectOfType(Entry.class);
        assertEquals("12/1/2005", entry.getDate());
        receipts = entry.getReceipts();
        assertEquals(1, receipts.length);
        assertEquals("sushi me", receipts[0].getStore());
        assertEquals("dining out: lunch", receipts[0].getCategory());
        assertEquals(5.60, receipts[0].getTotal());
        dec.close();
    }

    public void testWebSiteExample2() throws Exception {
        String yamlText = "--- \n" + "date: 11/29/2005\n" + "receipts:\n"
                + "    -   store: ken stanton music\n" + "        category: entertainment\n"
                + "        description: saxophone repair\n" + "        total: 382.00\n"
                + "    -   store: walmart\n" + "        category: groceries\n" + "        total: 14.26";
        Entry entry = Yaml.loadType(yamlText, Entry.class);
        assertEquals("11/29/2005", entry.getDate());
        assertEquals(2, entry.getReceipts().length);
        assertEquals("ken stanton music", entry.getReceipts()[0].getStore());
        assertEquals("groceries", entry.getReceipts()[1].getCategory());
        assertEquals(382.00, entry.getReceipts()[0].getTotal());

    }

    public void testWebSiteExample2WithRealDate() throws Exception {
        String yamlText = "--- \n" + "date: 11/29/2005\n" + "receipts:\n"
                + "    -   store: ken stanton music\n" + "        category: entertainment\n"
                + "        description: saxophone repair\n" + "        total: 382.00\n"
                + "    -   store: walmart\n" + "        category: groceries\n" + "        total: 14.26";
        Entry2 entry = Yaml.loadType(yamlText, Entry2.class);
        assertEquals(new Date("11/29/2005"), entry.getDate());
        assertEquals(2, entry.getReceipts().length);
        assertEquals("ken stanton music", entry.getReceipts()[0].getStore());
        assertEquals("groceries", entry.getReceipts()[1].getCategory());
        assertEquals(382.00, entry.getReceipts()[0].getTotal());

    }

    public void testSkimoEx1() {
        String yamlText = "---\n" + "cacheline: -1";
        Map m = (Map) Yaml.load(yamlText);
        assertEquals(m.get("cacheline"), -1);
    }

    public void testSkimoEx2() {
        String yamlText = "---\n" + "cacheline: 1";
        Map m = (Map) Yaml.load(yamlText);
        assertEquals(m.get("cacheline"), 1);
    }

    public void testSkimoEx3() {
        String yamlText = "---\n" + "cacheline: -1.5";
        Map m = (Map) Yaml.load(yamlText);
        assertEquals(m.get("cacheline"), -1.5);
    }

    public void testNegativenumberAsKey() {
        String yamlText = "---\n" + "-1: -1.5";
        Map m = (Map) Yaml.load(yamlText);
        System.out.println(m);
        assertEquals(m.get(-1), -1.5);
    }

    public void testNnumberAsKey() {
        String yamlText = "---\n" + "1: -1.5";
        Map m = (Map) Yaml.load(yamlText);
        System.out.println(m);
        assertEquals(-1.5, m.get(1));
    }

    public void testIterable() {
        String yamlText = "--- 1\n" + "--- 2\n" + "--- 3";
        int i = 1;
        YamlStream col = Yaml.loadStream(yamlText);
        for (Object o : col) {
            assertEquals(i, o);
            i++;
        }
        i = 1;
        for (Object o : col) {
            throw new RuntimeException("should not get here");
            // assertEquals(i, o);
            // i++;
        }
    }

    public void testIterator() {
        String yamlText = "--- 1\n" + "--- 2\n" + "--- 3";
        int i = 1;
        Iterator iterable = Yaml.loadStream(yamlText);
        while (iterable.hasNext()) {
            assertEquals(i, iterable.next());
            i++;
        }
    }

    public void testMultiDimensionalArrays() {
        int[][] a = new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        integrationTest(a, "testMultiDimensionalArrays", new Validator<int[][]>() {
            public void validate(int[][] a) {
                assertEquals(1, a[0][0]);
                assertEquals(16, a[3][3]);
                assertEquals(4, a.length);
                assertEquals(4, a[2].length);
            }
        });
    }

    public void testMultiDimensionalArrays2() {
        int[][][] a = new int[][][] { { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } } };
        integrationTest(a, "testMultiDimensionalArrays", new Validator<int[][][]>() {
            public void validate(int[][][] a) {
                assertEquals(1, a[0][0][0]);
                assertEquals(16, a[0][3][3]);
                assertEquals(4, a[0].length);
                assertEquals(4, a[0][2].length);
            }
        });
    }

    public static class Record {
        public String name;

        public Integer age;

        public Record next;
    }

    public void testPublicFields() {
        Record r = new Record();
        r.name = "John Smith";
        r.age = 4;
        integrationTest(r, "testPublicFields", new Validator<Record>() {
            public void validate(Record r) {
                assertEquals("John Smith", r.name);
                assertEquals(new Integer(4), r.age);
            }
        });
    }

    public void testNestedPublicFields() {
        Record r = new Record();
        r.name = "John Smith";
        r.age = 4;
        Record r2 = new Record();
        r2.name = "Jane Smith";
        r.next = r2;
        integrationTest(r, "testNestedPublicFields", new Validator<Record>() {
            public void validate(Record r) {
                assertEquals("John Smith", r.name);
                assertEquals(new Integer(4), r.age);
                assertEquals("Jane Smith", r.next.name);
            }
        });
    }

    public void testMapInList() {
        String yamlText = "- one\n" + "- two\n" + "- three: four\n" + "  five: six";
        List list = (List) Yaml.load(yamlText);
        assertEquals("four", ((Map) list.get(2)).get("three"));
        assertEquals("six", ((Map) list.get(2)).get("five"));
    }

    public void testNonExistentField() {
        String yamlText = "name: John Smith\n" + "age: 4\n" + "area: 44";
        Record r = Yaml.loadType(yamlText, Record.class);
        assertEquals("John Smith", r.name);
        assertEquals(new Integer(4), r.age);
    }

    public void testNonExistentProperty() {
        String yamlText = "date: 11/12/03\n" + "age: 4\n" + "area: 44";
        Entry r = Yaml.loadType(yamlText, Entry.class);
        assertEquals("11/12/03", r.getDate());
    }

    public void testNonExistentListProperty() {
        String yamlText = "date: 11/12/03\n" + "age: 4\n" + "children:\n" + "  - john\n" + "  - joe\n";
        Entry r = Yaml.loadType(yamlText, Entry.class);
        assertEquals("11/12/03", r.getDate());
    }

    public void testSkimoInlinedList() {
        String yamlText = "---\n" + "arrays:\n" + " - &1\n" + "   dims:\n" + "     - 10\n"
                + "   id: 1610612737\n" + "   name: a\n" + "cacheline: -1\n" + "context:\n"
                + " constraints:\n" + "   -\n" + "     - [1, 1, 0]\n" + "     - [1, -1, 2]\n" + " dim: 0\n"
                + " params:\n" + "   - &2\n" + "     name: n\n";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertEquals("-1", pdg.cacheline);
    }

    public void testSkimoIgnoreTransfers() {
        String yamlText = "--- !perl/PDG\n" + "arrays:\n" + " - &1\n" + "   dims:\n" + "     - 10\n"
                + "   id: 1610612737\n" + "   name: a\n" + "cacheline: -1\n"
                + "context: !perl/PDG::UnionSet\n" + " constraints:\n" + "   - !perl/PDL::Matrix\n"
                + "     - [1, 1, 0]\n" + "     - [1, -1, 2]\n" + " dim: 0\n" + " params:\n" + "   - &2\n"
                + "     name: n\n";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertEquals("-1", pdg.cacheline);
    }

    public void testSkimoNegativeInList() {
        String yamlText = "---\n" + "prefix:\n" + " - 1\n" + " - -1\n" + " - 1";
        Map map = (Map) Yaml.load(yamlText);
        assertEquals(-1, ((List) map.get("prefix")).get(1));
    }

    public void testArrayInArray() {
        String yamlText = "---\n" + "nodes:\n" + " -\n" + "   nr: -1\n" + "   prefix: [1, -1, 1]";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertNotNull(pdg);
        assertEquals(1, pdg.nodes.length);
        assertEquals(3, pdg.nodes[0].prefix.length);
        assertEquals(-1, pdg.nodes[0].prefix[1]);

    }

    public void testSkimoArray() {
        String yamlText = "---\n" + "context:\n" + " constraints:\n" + "   -\n" + "     - [1, 1, 0]\n"
                + "     - [1, -1, 2]\n" + " dim: 0";
        PDG pdg = Yaml.loadType(yamlText, PDG.class);
        assertEquals(pdg.context.dim, 0);
        assertEquals(pdg.context.constraints[0][0][0], 1);
        assertEquals(pdg.context.constraints[0][0][1], 1);
        assertEquals(pdg.context.constraints[0][0][2], 0);
        assertEquals(pdg.context.constraints[0][1][0], 1);
        assertEquals(pdg.context.constraints[0][1][1], -1);
        assertEquals(pdg.context.constraints[0][1][2], 2);
    }

    public void testEmptyInlinedLists() {
        String yamlText = "---\n" + "- []";
        List list = Yaml.loadType(yamlText, ArrayList.class);
        assertEquals(0, ((List) list.get(0)).size());
    }

    public void testAnchorIgnored() {
        String yamlText = "---\n" + "ignored:\n" + " - &1\n" + "   dims: []\n" + "   id: 1610612737\n"
                + "   name: a\n" + "arrays:\n" + " - *1";
        try {
            PDG pdg = Yaml.loadType(yamlText, PDG.class);
            assertEquals(pdg.arrays[0].name, "a");
        } catch (YamlParserException e) {

        }
    }

    public void testCircular() {
        Circular c = new Circular();
        c.setI(3);
        c.other = c;
        integrationTest(c, "testCircular", new Validator<Circular>() {
            public void validate(Circular c) {
                assertEquals(3, c.getI());
                assertSame(c, c.getOther());
            }
        });
    }

    public void testArrayInList() {
        String yamlText = "- !int[] [1,2,3]\n" + "- 2";
        List list = (List) Yaml.load(yamlText);
        assertEquals(list.size(), 2);
        assertEquals(((int[]) list.get(0))[0], 1);
        assertEquals(((int[]) list.get(0))[1], 2);
        assertEquals(((int[]) list.get(0))[2], 3);
    }

    public enum Suit {
        SPADE, HEART, CLUB, DIAMOND
    };

    public static class Card {
        public Card() {
        }

        public Card(Suit s, int points) {
            this.suit = s;
            this.points = points;
        }

        public Suit suit;

        public int points;
    }

    public void testEnum() {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.DIAMOND, 12));
        cards.add(new Card(Suit.CLUB, 1));
        cards.add(new Card(Suit.HEART, 5));
        cards.add(new Card(Suit.SPADE, 9));
        integrationTest(cards, "testEnum", new Validator<ArrayList<Card>>() {
            public void validate(ArrayList<Card> cards) {
                assertEquals(5, cards.size());
                assertEquals(Suit.SPADE, cards.get(0).suit);
                assertEquals(Suit.HEART, cards.get(3).suit);
            }
        });

    }

    public enum Planet {
        MERCURY(3.303e+23, 2.4397e6), VENUS(4.869e+24, 6.0518e6), EARTH(5.976e+24, 6.37814e6), MARS(
                6.421e+23, 3.3972e6), JUPITER(1.9e+27, 7.1492e7), SATURN(5.688e+26, 6.0268e7), URANUS(
                8.686e+25, 2.5559e7), NEPTUNE(1.024e+26, 2.4746e7), PLUTO(1.27e+22, 1.137e6);

        private final double mass; // in kilograms

        private final double radius; // in meters

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double mass() {
            return mass;
        }

        public double radius() {
            return radius;
        }

        // universal gravitational constant (m3 kg-1 s-2)
        public static final double G = 6.67300E-11;

        public double surfaceGravity() {
            return G * mass / (radius * radius);
        }

        public double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }
    }

    public void testComplexEnum() {
        ArrayList<Planet> planets = new ArrayList<Planet>(Arrays.asList(Planet.values()));
        integrationTest(planets, "testComplexEnum", new Validator<ArrayList<Planet>>() {
            public void validate(ArrayList<Planet> planets) {
                assertEquals(Planet.MERCURY, planets.get(0));
                assertEquals(Planet.VENUS, planets.get(1));
                assertEquals(Planet.EARTH, planets.get(2));
                assertEquals(Planet.MARS, planets.get(3));
                assertEquals(Planet.JUPITER, planets.get(4));
                assertEquals(Planet.SATURN, planets.get(5));
                assertEquals(Planet.URANUS, planets.get(6));
                assertEquals(Planet.NEPTUNE, planets.get(7));
                assertEquals(Planet.PLUTO, planets.get(8));
                assertEquals(9, planets.size());

            }
        });
    }

    public void testComplexEnumArray() {
        // bug with inter-classes in arrays
        Planet[] planets = Planet.values();
        integrationTest(planets, "testComplexEnumArray", new Validator<Planet[]>() {
            public void validate(Planet[] planets) {
                assertEquals(Planet.MERCURY, planets[0]);
                assertEquals(Planet.VENUS, planets[1]);
                assertEquals(Planet.EARTH, planets[2]);
                assertEquals(Planet.MARS, planets[3]);
                assertEquals(Planet.JUPITER, planets[4]);
                assertEquals(Planet.SATURN, planets[5]);
                assertEquals(Planet.URANUS, planets[6]);
                assertEquals(Planet.NEPTUNE, planets[7]);
                assertEquals(Planet.PLUTO, planets[8]);
                assertEquals(9, planets.length);

            }
        });
    }

    public void testEmptyStringWithAnchorBug() {
        HashMap map = new HashMap();
        String test = "";
        map.put("key1", test);
        map.put("key2", test);
        integrationTest(map, "testEmptyStringWithAnchorBug", new Validator<HashMap>() {
            public void validate(HashMap map) {
                assertEquals(map.get("key1"), "");
                assertEquals(map.get("key2"), "");
                assertSame(map.get("key1"), map.get("key2"));

            }
        });
    }

    public void testEmptyStringWithAnchorBug2() {
        HashMap map = new HashMap();
        String test = "abc";
        map.put("key1", test);
        map.put("key2", test);
        integrationTest(map, "testEmptyStringWithAnchorBug2", new Validator<HashMap>() {
            public void validate(HashMap map) {
                assertEquals(map.get("key1"), "abc");
                assertEquals(map.get("key2"), "abc");
                assertNotSame(map.get("key1"), map.get("key2"));

            }
        });
    }

    public void testStringsWithSpecialChars() {
        HashMap map = new HashMap();
        String test = "\"";
        map.put("key1", test);
        integrationTest(map, "testStringsWithSpecialChars", new Validator<HashMap>() {
            public void validate(HashMap map) {
                map.get("key1").equals("\"");
            }
        });
    }

    public void testStringsWithSpecialChars2() {
        HashMap map = new HashMap();
        String test = "\"}:";
        map.put("key1", test);
        integrationTest(map, "testStringsWithSpecialChars2", new Validator<HashMap>() {
            public void validate(HashMap map) {
                map.get("key1").equals("\"}:");
            }
        });
    }

    public void testDanielsBug() {
        String yamlText = "-\n" + "  msg: \"\\\\ foo\"\n" + "  handle: firefox\n" + "  id: 4";
        System.out.println(yamlText);
        List list = (List) Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map) list.get(0);
        assertEquals("\\ foo", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }

    public void testDanielsBug2() {
        String yamlText = "-\n" + "  msg: '\\\\ foo'\n" + "  handle: firefox\n" + "  id: 4";
        System.out.println(yamlText);
        List list = (List) Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map) list.get(0);
        assertEquals("\\\\ foo", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }

    public void testDanielsBug3() {
        String yamlText = "-\n" + "  msg: \\\\ foo\n" + "  handle: firefox\n" + "  id: 4";
        System.out.println(yamlText);
        List list = (List) Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map) list.get(0);
        assertEquals("\\\\ foo", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }

    public void testSingleQuotes() {
        String yamlText = "-\n" + "  msg: 'hello world'\n" + "  handle: firefox\n" + "  id: 4";
        System.out.println(yamlText);
        List list = (List) Yaml.load(yamlText);
        assertEquals(list.size(), 1);
        Map map = (Map) list.get(0);
        assertEquals("hello world", map.get("msg"));
        assertEquals(map.get("handle"), "firefox");
        assertEquals(map.get("id"), 4);
    }

    public void testBackslashes() {
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("msg", "\\ foo");
        list.add(map);
        integrationTest(list, "testBackslashes", new Validator<List>() {
            public void validate(List list) {
                Map m = (Map) list.get(0);
                assertEquals("\\ foo", m.get("msg"));
            }
        });
    }

    public void testTransferMapping() throws Exception {
        YamlConfig config = new YamlConfig();
        BiDirectionalMap<String, String> m = new BiDirectionalMap<String, String>();
        m.put("Company", Company.class.getName());
        config.setTransfers(m);

        Company c = new Company();
        String dump = config.dump(c);
        System.out.println(dump);
        assertTrue(dump.startsWith("--- !Company"));
        c = (Company) config.load(dump);

    }

    public void testZeroLengthArrayBug() {
        Company[] c = new Company[0];
        integrationTest(c, "testZeroLengthArrayBug", new Validator<Company[]>() {
            public void validate(Company[] c) {
                assertEquals(0, c.length);
            }
        });
    }

    public void testNullInArrayBug() {
        Company[] c = new Company[1];
        integrationTest(c, "testNullInArrayBug", new Validator<Company[]>() {
            public void validate(Company[] c) {
                assertEquals(1, c.length);
                assertNull(c[0]);
            }
        });
    }

    public void testTransferMappingArray() {
        YamlConfig config = new YamlConfig();
        BiDirectionalMap<String, String> m = new BiDirectionalMap<String, String>();
        m.put("Company", Company.class.getName());
        config.setTransfers(m);
        Company[] c = new Company[1];
        c[0] = new Company();
        String dump = config.dump(c);
        System.out.println(dump);
        assertTrue(dump.startsWith("--- !Company[]"));
        c = (Company[]) config.load(dump);
    }

    public void testFileSupport() {
        final File file = new File("myfile");
        integrationTest(file, "testFileSupport", new Validator<File>() {
            public void validate(File obj) {
                assertEquals(file, obj);
                assertEquals("myfile", obj.getName());
            }
        });
    }
}
