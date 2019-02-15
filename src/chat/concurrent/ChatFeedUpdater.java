package chat.concurrent;

import java.rmi.RemoteException;
import java.util.Objects;

public class ChatFeedUpdater implements Runnable
{
   Chat chat;

   public ChatFeedUpdater(Chat chat) {
      this.chat = chat;
   }

   @Override
   public void run() {
      while (true) {
         try {
            this.waitUntilChatEstablished();
            this.updateFeed();
         } catch (RemoteException | NullPointerException e) {
            System.out.println("User disconnected");
         }
      }
   }

   private synchronized void updateFeed() throws RemoteException {
      chat.printChatFeed();
   }

   private synchronized void waitUntilChatEstablished() {
      while (chat == null) {
         try {
            wait();
         } catch (InterruptedException e) {
            if (!Objects.isNull(chat)) {
               System.out.println(chat.getUsername() + " has disconnected");
            } else {
               return;
            }
         }
      }
   }
}