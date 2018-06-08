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
        RequestLog l1 = new RequestLog("/api/test", 4, 20, 400);

        assertEquals("/api/test", l1.getRequestURL());
        assertEquals(4, l1.getStartTime());
        assertEquals(20, l1.getStringsCreated());
        assertEquals(400, l1.getMemoryAllocated());

        l1.setRequestURL("/api/test1");
        l1.setEndTime(20);
        l1.setStringsCreated(15);
        l1.setMemoryAllocated(500);

        assertEquals("/api/test1", l1.getRequestURL());
        assertEquals(16, l1.getElapsedTime());
        assertEquals(15, l1.getStringsCreated());
        assertEquals(500, l1.getMemoryAllocated());
    }

    @Test
    public void testEmpty() { //tests empty constructor
        RequestLog l1 = new RequestLog();

        assertEquals("", l1.getRequestURL());
        assertEquals(-1, l1.getStartTime());
        assertEquals(0, l1.getStringsCreated());
        assertEquals(0, l1.getMemoryAllocated());

        l1.setRequestURL("/api/test1");
        l1.setStartTime(3);
        l1.setStringsCreated(15);
        l1.setMemoryAllocated(500);

        assertEquals("/api/test1", l1.getRequestURL());
        assertEquals(3, l1.getStartTime());
        assertEquals(15, l1.getStringsCreated());
        assertEquals(500, l1.getMemoryAllocated());
    }

    @Test
    public void testNotEquals() {
        RequestLog l1 = new RequestLog("/api/test", 4, 20, 400);
        RequestLog l2 = new RequestLog("/api/test1", 4, 20, 400);
        assertNotEquals(l1, null);
        assertNotEquals(l2, null);
        assertNotEquals(l1, l2);
    }

    @Test
    public void testToString() {
        RequestLog l1 = new RequestLog("/api/test", 4, 20, 400);
        Integer l1ID = l1.getRequestID();
        assertEquals("" + l1ID + ": /api/test", l1.toString());
    }

    @Test
    public void testAddStringMem() {
        RequestLog l1 = new RequestLog("/api/test", 4, 20, 400);
        l1.addMemoryAllocated(10);
        l1.addStringsCreated(10);
        assertEquals(30, l1.getStringsCreated());
        assertEquals(410, l1.getMemoryAllocated());
    }
    
}