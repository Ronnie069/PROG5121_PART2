package com.chatapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginObj = null;
        boolean isLoggedIn = false;
        boolean runProgram = true;

        System.out.println("Welcome to QuickChat.");

        while (runProgram) {

            if (!isLoggedIn) {
                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter First Name: ");
                        String fName = scanner.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lName = scanner.nextLine();
                        System.out.print("Enter Username: ");
                        String user = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String pass = scanner.nextLine();
                        System.out.print("Enter Phone Number: ");
                        String phone = scanner.nextLine();

                        loginObj = new Login(user, pass, fName, lName, phone);
                        System.out.println("\n" + loginObj.registerUser());
                        break;

                    case "2":
                        if (loginObj == null) {
                            System.out.println("No user registered yet! Please register first.");
                        } else {
                            System.out.print("Enter Username: ");
                            String loginUser = scanner.nextLine();
                            System.out.print("Enter Password: ");
                            String loginPass = scanner.nextLine();

                            isLoggedIn = loginObj.loginUser(loginUser, loginPass);
                            System.out.println(loginObj.returnLoginStatus(isLoggedIn));
                        }
                        break;

                    case "3":
                        System.out.println("Exiting... Goodbye!");
                        runProgram = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please enter 1, 2, or 3.");
                }

            } else {
                System.out.println("\nWelcome to QuickChat.");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");
                System.out.print("Choose an option: ");
                String menuChoice = scanner.nextLine();

                switch (menuChoice) {
                    case "1":
                        System.out.print("How many messages would you like to send? ");
                        int numMessages;
                        try {
                            numMessages = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Returning to menu.");
                            break;
                        }

                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Message " + (i + 1) + " ---");

                            System.out.print("Enter recipient cell number: ");
                            String recipient = scanner.nextLine();

                            System.out.print("Enter your message: ");
                            String messageText = scanner.nextLine();

                            Message msg = new Message(recipient, messageText, Message.returnTotalMessages() + 1);

                            System.out.println(msg.checkRecipientCell());

                            String lengthCheck = msg.checkMessageLength();
                            System.out.println(lengthCheck);

                            if (!lengthCheck.equals("Message ready to send.")) {
                                System.out.println("Please shorten your message and try again.");
                                i--;
                                continue;
                            }

                            System.out.println("Message ID  : " + msg.getMessageID());
                            System.out.println("Message Hash: " + msg.createMessageHash());

                            System.out.println("\nWhat would you like to do?");
                            System.out.println("1. Send Message");
                            System.out.println("2. Disregard Message");
                            System.out.println("3. Store Message to send later");
                            System.out.print("Choose: ");
                            int sendChoice;
                            try {
                                sendChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                sendChoice = 2;
                            }

                            System.out.println(msg.sentMessage(sendChoice));
                        }

                        System.out.println(Message.printMessages());
                        System.out.println("Total messages sent: " + Message.returnTotalMessages());
                        break;

                    case "2":
                        System.out.println("Coming Soon.");
                        break;

                    case "3":
                        System.out.println("Exiting QuickChat. Goodbye!");
                        runProgram = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please enter 1, 2, or 3.");
                }
            }
        }
        scanner.close();
    }
}