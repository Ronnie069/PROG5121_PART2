package com.chatapp;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Message class handling sending, storing and displaying messages.
 */
public class Message {

    private final String messageID;
    private final String recipient;
    private final String message;
    private final int messageNumber;

    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    /**
     * Constructor for Message
     *
     * @param recipient      recipient's cell number
     * @param message       message content
     * @param messageNumber  sequential number of this message
     */
    public Message(String recipient, String message, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
    }

    /**
     * Generating  random 10-digit message ID
     */
    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    /**
     * Checking if the message id is no more than 10 characters
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /**
     * Checking if  recipient cell number is correctly formatted.
     *
     * Must start with + and be no more than 10 characters
     */
    public String checkRecipientCell() {
        String regex = "^\\+[0-9]{9,12}$";
        if (recipient != null && Pattern.compile(regex).matcher(recipient).matches()) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /**
     * Validates the message length (max 250 characters)
     */
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = message.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }

    /**
     * Creates and returns the Message Hash
     * Format: first 2 digits of ID : message number : FIRSTWORD+LASTWORD in caps
     * Example: 00:0:HITONIGHT
     */
    public String createMessageHash() {
        String idPrefix = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase().replaceAll("[^A-Z]", "");
        String lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    /**
     * Handles sending, storing, or disregarding a message.
     * 1 = Send, 2 = Disregard, 3 = Store
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    /**
     * Returns all sent messages as a formatted string.
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Sent Messages ---\n");
        for (Message m : sentMessages) {
            sb.append("Message ID   : ").append(m.messageID).append("\n");
            sb.append("Message Hash : ").append(m.createMessageHash()).append("\n");
            sb.append("Recipient    : ").append(m.recipient).append("\n");
            sb.append("Message      : ").append(m.message).append("\n");
            sb.append("---------------------\n");
        }
        return sb.toString();
    }

    /**
     * Returns total number of messages sent.
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /**
     * Stores the message to a JSON file.
     * Reference: https://code.google.com/archive/p/json-simple/
     */
    @SuppressWarnings("unchecked")
    public void storeMessage() {
        JSONObject obj = new JSONObject();
        obj.put("messageID", messageID);
        obj.put("messageNumber", messageNumber);
        obj.put("recipient", recipient);
        obj.put("message", message);
        obj.put("messageHash", createMessageHash());

        JSONArray list = new JSONArray();
        list.add(obj);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(list.toJSONString());
            file.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public int getMessageNumber() { return messageNumber; }
}