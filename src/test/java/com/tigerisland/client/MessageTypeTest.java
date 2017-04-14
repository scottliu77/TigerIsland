package com.tigerisland.client;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessageTypeTest {

    @Test
    public void testCanCreateMessageType() {
        assertTrue(MessageType.MATCHSTARTED != null);
    }

    @Test
    public void testCanGetMessageSubtype() {
        assertTrue(MessageType.MATCHOVER.getSubtype() == "MATCH");
    }
}
