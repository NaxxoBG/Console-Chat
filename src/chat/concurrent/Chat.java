package chat.concurrent;

import java.rmi.RemoteException;

import client.chat.ChatRemoteInterface;
import client.chat.ChatUser;

public class Chat implements ChatHandlerInterface
{
   private ChatUser user;
   private ChatUser user2;
   private boolean chatStatus;
   private boolean chatFeed;

   public Chat(ChatUser user, ChatUser user2, ChatRemoteInterface inter) {
      this.user = user;
      this.user2 = user2;
      this.chatStatus = true;
      chatFeed = true;
   }

   public synchronized void processChat() throws RemoteException {
      chatStatus = true;
      while (chatStatus) {
         chatUser();
         printChatFeed();
      }
   }

   private synchronized void chatUser() throws RemoteException {
      chatStatus = this.user.sendMessagetoClient(user2.getName());
   }

   public String getUsername() {
      return user.getName();
   }

   public String getUsername2() {
      return this.user2.getName();
   }

   public synchronized void printChatFeed() throws RemoteException {
      while (!chatFeed) {
         try {
            wait();
         } catch (InterruptedException e) {}
      }
      chatFeed = false;
      user.printChatFeed();
      notifyAll();
   }
}