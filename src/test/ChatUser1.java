package test;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.chat.ChatUser;

public class ChatUser1
{
   public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, ConnectException {
      ChatUser user1 = new ChatUser("user1");
      user1.init();
   }
}