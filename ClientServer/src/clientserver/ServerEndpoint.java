/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author eniomagalhaes
 */

// Define WebSocket server endpoint
public class ServerEndpoint {
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
