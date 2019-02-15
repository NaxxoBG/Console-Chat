package client.chat;

import java.rmi.RemoteException;

public class ClientMenu
{
   private ChatUser user;

   public ClientMenu(ChatUser user) {
      this.user = user;
   }

   public ChatUser getUser() {
      return user;
   }

   public void setUser(ChatUser user) {
      this.user = user;
   }

   private void printMenu() {
      System.out.println();
      System.out.println("1. Print online users");
      System.out.println("2. Connect and chat with a user");
      System.out.println("3. Print received messages");
      System.out.println("4. Change name");
      System.out.println("5. Disconnect");
      System.out.println();
   }

   public void runInterface() throws RemoteException {
      int input = 0;
      System.out.println("Welcome to chat, " + user.getName());
      do {
         printMenu();
         input = Integer.parseInt(user.getInput().nextLine());
         switch (input) {
            case 1:
               user.checkOnlineUsers();
               break;
            case 2:
               System.out.println("Please enter the name of the user you want to chat with");
               String username = user.getInput().nextLine();
               user.initiateChat(username);
               break;
            case 3:
               user.printChatFeed();
               break;
            case 4:
               System.out.println("Please enter your new username. Your current one is " + user.getName() + ". Write quit to cancel.");
               String newName = user.getInput().nextLine();
               if (newName.equalsIgnoreCase("quit")) {
                  break;
               } else {
                  user.setNewUsername(newName);
               }
               break;
            case 5:
               System.out.println("Program closed");
               user.unregisterUser();
               break;
            default:
               break;
         }
      } while (input != 5);
   }
}