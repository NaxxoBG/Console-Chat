package chat.concurrent;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class ChatHandler implements Runnable
{
   private Chat handler;

   public ChatHandler(ChatHandlerInterface handler) {
      this.handler = (Chat) handler;
   }

   @Override
   public void run() {
      try {
         String[] current = LocalDateTime.now().toString().split("T");
         System.out.println(String.format("Chat between users %s and %s started, %s %s.", handler.getUsername(), handler.getUsername2(), current[0], current[1]));
         handler.processChat();
         System.out.println("Chat ended");
      } catch (RemoteException e) {
         e.printStackTrace();
      }
   }
}