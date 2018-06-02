package com.spnlangagent.langagent;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 * Test suite for RequestLog class.
 */
public class TestRequestLog {

    //------------------------------------------------------------------------//
    // Setup
    //------------------------------------------------------------------------//

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {}

    //------------------------------------------------------------------------//
    // Tests
    //------------------------------------------------------------------------//

    @Test
    public void testStandard() { //tests standard/default constructor
        RequestLog l1 = new RequestLog("/api/test", 4.0, 20, 400);

        assertEquals("/api/test", l1.getRequestURL());
        assertEquals(4.0, l1.getRequestTime(), 0.05);
        assertEquals(20, l1.getStringsCreated());
        assertEquals(400, l1.getMemoryAllocated());

        l1.setRequestURL("/api/test1");
        l1.setRequestTime(3.0);
        l1.setStringsCreated(15);
        l1.setMemoryAllocated(500);

        assertEquals("/api/test1", l1.getRequestURL());
        assertEquals(3.0, l1.getRequestTime(), 0.05);
        assertEquals(15, l1.getStringsCreated());
        assertEquals(500, l1.getMemoryAllocated());
    }

    @Test
    public void testEmpty() { //tests empty constructor
        RequestLog l1 = new RequestLog();

        assertEquals("", l1.getRequestURL());
        assertEquals(0.0, l1.getRequestTime(), 0.05);
        assertEquals(0, l1.getStringsCreated());
        assertEquals(0, l1.getMemoryAllocated());

        l1.setRequestURL("/api/test1");
        l1.setRequestTime(3.0);
        l1.setStringsCreated(15);
        l1.setMemoryAllocated(500);

        assertEquals("/api/test1", l1.getRequestURL());
        assertEquals(3.0, l1.getRequestTime(), 0.05);
        assertEquals(15, l1.getStringsCreated());
        assertEquals(500, l1.getMemoryAllocated());
    }

    @Test
    public void testNotEquals() {
        RequestLog l1 = new RequestLog("/api/test", 4.0, 20, 400);
        RequestLog l2 = new RequestLog("/api/test1", 4.0, 20, 400);
        assertNotEquals(l1, null);
        assertNotEquals(l2, null);
        assertNotEquals(l1, l2);
    }
}