package client.chat;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import address.server.AddressServer;

public class ChatUser implements Serializable, Remote
{
   private static final long serialVersionUID = 1L;
   private String name;
   private ChatRemoteInterface inter;
   private Scanner scanner;
   private HashMap<String, String> messages;
   private ClientMenu menu;

   public ChatUser(String name) throws MalformedURLException, RemoteException, NotBoundException, java.net.ConnectException {
      this.name = name;
      this.messages = new HashMap<>();

      try {
         inter = (ChatRemoteInterface) Naming.lookup(AddressServer.SERVER_NAME);
      } catch (java.rmi.ConnectException e) {
         System.out.println("Could not connect to object!\n" + e.getMessage());
      }

      Optional<ChatUser> userWithName = inter.getAllUsers().stream().filter(e -> e.getName().equals(name)).findAny();
      if (userWithName.isPresent()) {
         System.out.println("A User with this name is already registered");
         System.out.println("Program closed");
         System.exit(0);
      }

      inter.registerUser(this);
      System.out.println("Stub received");

      scanner = new Scanner(System.in);
   }

   public boolean sendMessagetoClient(String addressee) throws RemoteException {
      System.out.println(String.format("Please enter your message to user: %s. Write \"quit\" to quit.", addressee));
      String message = scanner.nextLine();
      if (message.equalsIgnoreCase("quit")) {
         return false;
      } else {
         try {
            String[] current = LocalDateTime.now().toString().split("T");
            inter.sendMessage(current[0] + " " + current[1] + " | " +  message, this.name, addressee);
            return true;
         } catch (java.util.NoSuchElementException e) {
            System.out.println("Message not sent");
            System.out.println("User with ID: \"" + addressee + "\" is not registered in the system!\n" + e.toString());
            System.out.println(Arrays.asList(inter.getAllUsers()));
            return false;
         }
      }
   }

   public void printChatFeed() throws RemoteException {
      messages = inter.getUser(this.name).getMessages();
      if (messages.isEmpty()) {
         System.out.println("No messages");
      } else {
         messages.entrySet().stream()
         .map(a -> "| From " + a.getKey() + " : " + a.getValue())
         .forEach(System.out::println);
         messages.clear();
      }
   }

   public void initiateChat(String addressee) throws RemoteException {
      this.sendMessagetoClient(addressee);
   }

   public void setNewUsername(String newUsername) throws RemoteException {
      inter.setNewUsername(name, newUsername);
      this.setName(newUsername);
   }
   
   public void printAllUsers() throws RemoteException {
      System.out.println(inter.getAllUsers());
   }

   public void checkOnlineUsers() throws RemoteException {
      System.out.println(inter.getRegisteredUsersString());
   }

   public void unregisterUser() throws RemoteException {
      this.menu = null;
      this.scanner = null;
      inter.unregisterUser(this);
      this.inter = null;
      System.gc();
      System.runFinalization();
      System.exit(0);
   }


   public void init() throws RemoteException {
      menu = new ClientMenu(this);
      menu.runInterface();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public HashMap<String, String> getMessages() {
      return messages;
   }

   public ChatRemoteInterface getSharedObject() {
      return this.inter;
   }

   public Scanner getInput() {
      return this.scanner;
   }

   @Override
   public String toString() {
      return "ChatUser Name: " + name + ", Messages: " + messages;
   }
}