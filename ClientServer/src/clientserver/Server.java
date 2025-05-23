package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author eniomagalhaes
 */

public class Server {
    public static void main(String[] args){
        
        try
        {
            System.out.println("Waiting for clients...");
            
            ServerSocket ss = new ServerSocket(8080);
            Socket s = ss.accept(); 
            System.out.println("Client Connected");
            //buferedreader used to read data from the socket input string
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = in.readLine();
            //Using PrintWriter to send the string back to the client
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Server says: " + str);
             
        }
        catch(IOException e) {
            
        }
      
    }
     // Thread-safe set to store connected client sessions
    private static final Set<Session> clients = new CopyOnWriteArraySet<>();
    // Shared list to store generated integers
    private static final List<Integer> numList = new ArrayList<>();
    // Atomic integer to keep track of the total sum of generated integers
    private static final AtomicInteger total = new AtomicInteger(0);
    // Minimum number of clients required to start generating numbers
    private static final int MIN_CLIENTS = 5;

    // Called when a new client connects
    public void onOpen(Session session) {
        clients.add(session); // Add the new client session to the set
        System.out.println("Client connected: " + clients.size() + " clients total");

        // If the number of connected clients is at least MIN_CLIENTS, notify all clients to start
        if (clients.size() >= MIN_CLIENTS) {
            notifyClientsToStart();
        }
    }

     // Called when a message is received from a client
    public void onMessage(String message, Session session) {
        int num = Integer.parseInt(message); // Parse the received integer
        numList.add(num); // Add the integer to the shared list
        total.addAndGet(num); // Add the integer to the total sum

        // If the total sum is greater than or equal to 1,000,000, notify all clients to stop
        if (total.get() >= 1000000) {
            notifyClientsToStop();
            System.out.println("Total: " + total.get());
            System.out.println("numList: " + numList);
        }
    }

    // Called when a client disconnects
    public void onClose(Session session) {
        clients.remove(session); // Remove the client session from the set
    }

    // Notify all clients to start generating numbers
    private void notifyClientsToStart() {
        for (Session client : clients) {
            try {
                client.getBasicRemote().toString("start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Notify all clients to stop generating numbers and send the final total and numList
    private void notifyClientsToStop() {
        for (Session client : clients) {
            try{
            client.getBasicRemote().toString("stop," + total.get() + "," + numList);
         } catch (IOException e) {
             e.printStackTrace();
             
            }
        }
    }
    private static class Session {

        public Session() {
        }

        private Object getBasicRemote() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
}
