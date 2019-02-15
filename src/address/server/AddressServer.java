package address.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.function.Predicate;

import client.chat.ChatRemoteInterface;
import client.chat.ChatUser;

public class AddressServer implements ChatRemoteInterface
{   
   public static final String SERVER_NAME = "//localhost/Server";
   private ArrayList<ChatUser> users;

   public AddressServer() throws RemoteException {
      users = new ArrayList<>();
   }

   public void registerUser(ChatUser user) {
      users.add(user);
   }

   public void unregisterUser(ChatUser user) throws RemoteException {
      ChatUser user2 = this.getUser(user.getName()); //different object because of serialization
      users.remove(user2);
   }

   @Override
   public void sendMessage(String message, String client, String addressee) throws RemoteException {
      this.getUser(addressee)
      .getMessages()
      .put(client, message);
   }

   public ArrayList<ChatUser> getAllUsers() throws RemoteException {
      return this.users;
   }

   @Override
   public void printRegisteredUsers() throws RemoteException {
      this.users.stream().map(ChatUser::getName).reduce((e, f) -> e + ", " + f).ifPresent(System.out::println);
   }

   public String getRegisteredUsersString() throws RemoteException {
      return this.users.stream().map(ChatUser::getName).reduce((e, f) -> e + ", " + f).get();
   }
   
   @Override
   public ChatUser getUser(String username) throws RemoteException {
      Predicate<ChatUser> equalName = e -> e.getName().equals(username);
      return users.stream().filter(equalName).findAny().get();
   }

   @Override
   public void setNewUsername(String oldName, String newName) throws RemoteException {
      this.getUser(oldName).setName(newName);
   }
   
   public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
      try {
         ChatRemoteInterface server = new AddressServer();
         ChatRemoteInterface shared = (ChatRemoteInterface) UnicastRemoteObject.exportObject(server, 0);

         LocateRegistry.createRegistry(1099);
         Naming.bind(SERVER_NAME, shared);

         System.out.println("Remote object is bound");
      } catch (Exception e) {
         System.out.println("Failed to bind remote object!\n" + e.getLocalizedMessage());
      }
   }
   
}