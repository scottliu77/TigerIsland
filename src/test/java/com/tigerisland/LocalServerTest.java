package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LocalServerTest {

    private LocalServer localServer;

    @Before
    public void createLocalServer() {
        this.localServer = new LocalServer(new GlobalSettings());
    }

    @Test
    public void testCanCreateLocalServer() {
        assertTrue(localServer != null);
    }
}
