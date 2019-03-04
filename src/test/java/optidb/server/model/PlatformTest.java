package optidb.server.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTest {
    private Platform platformTest;
    @Before
    public void setUp() throws Exception {
        platformTest = new Platform("Test","1.0");
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Test", platformTest.getName());
    }

    @Test
    public void getCurrentVersion() throws Exception {
        assertEquals("1.0", platformTest.getCurrentVersion());
    }

}