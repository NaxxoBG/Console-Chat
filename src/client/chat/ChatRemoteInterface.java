package client.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatRemoteInterface extends Remote
{
   /**Method used to transmit messages across users.
    * @param message The message to be sent.
    * @param client The user that is sending the message
    * @param addressee The user that should receive the message
    * @throws RemoteException
    */
   public void sendMessage(String message, String client, String addressee) throws RemoteException;

   /**Add the parameter <code>user</code> to the user's list in the address server.
    * @param user
    * @throws RemoteException
    */
   public void registerUser(ChatUser user) throws RemoteException;

   /**Remove the first instance of <code>user</code> from the list of users in the address.
    * @param user
    * @throws RemoteException
    */
   public void unregisterUser(ChatUser user) throws RemoteException;

   /**Return an arraylist of all registered users.
    * @return <code>ArrayList&ltChatUser&gt</code>
    * @throws RemoteException
    */
   public ArrayList<ChatUser> getAllUsers() throws RemoteException;

   /**Print the list of all registered users.
    * @throws RemoteException
    */
   public void printRegisteredUsers() throws RemoteException;

   /**Get a formatted string (foo, foo) of all registered users.
    * @return <code>String</code>
    * @throws RemoteException
    */
   public String getRegisteredUsersString() throws RemoteException;

   /**Return a reference to the <code>ChatUser</code> object with the name <code>username</code>.
    * @param username
    * @return <code>ChatUser</code>
    * @throws RemoteException
    */
   public ChatUser getUser(String username) throws RemoteException;
   
   /**Set a new username to the user with name <code>oldName</code>
    * @param oldName
    * @param newName
    * @throws RemoteException
    */
   public void setNewUsername(String oldName, String newName) throws RemoteException;
}