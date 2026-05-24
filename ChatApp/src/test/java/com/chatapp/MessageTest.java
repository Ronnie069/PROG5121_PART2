package com.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    Message msg1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
    Message msg2 = new Message("0857597889", "Hi Keegan, did you receive the payment?", 2);

    @Test
    public void testMessageLengthValid() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }

    @Test
    public void testMessageLengthTooLong() {
        Message longMsg = new Message("+27718693002", "A".repeat(260), 1);
        String result = longMsg.checkMessageLength();
        assertTrue(result.startsWith("Message exceeds 250 characters by"));
    }

    @Test
    public void testRecipientCellValid() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }

    @Test
    public void testRecipientCellInvalid() {
        assertTrue(msg2.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    public void testMessageHashCorrect() {
        String hash = msg1.createMessageHash();
        assertTrue(hash.endsWith(":HITONIGHT"), "Hash should end with :HITONIGHT but was: " + hash);
    }

    @Test
    public void testMessageIDGenerated() {
        assertTrue(msg1.checkMessageID());
        System.out.println("Message ID generated: " + msg1.getMessageID());
    }

    @Test
    public void testSentMessageSend() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Message successfully sent.", m.sentMessage(1));
    }

    @Test
    public void testSentMessageDisregard() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Press 0 to delete the message.", m.sentMessage(2));
    }

    @Test
    public void testSentMessageStore() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Message successfully stored.", m.sentMessage(3));
    }
}