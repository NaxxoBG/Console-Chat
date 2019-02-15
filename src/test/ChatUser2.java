package test;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.chat.ChatUser;

public class ChatUser2
{
   public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, ConnectException {
      ChatUser user2 = new ChatUser("user2");
      user2.init();
   }
}