package clientserver;

import java.net.Socket;
import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author eniomagalhaes
 */

public class Client {
    public static void main(String[] args) {
        
        try
        {
        System.out.println("Client started");
        Socket s = new Socket("localhost", 8080);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a String");
        String str = userInput.readLine();
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(str);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println(in.readLine());
        }
        catch(IOException e)
        {
        }
        
    }
     private static final Random random = new Random(); // Random number generator
    private Session session; // WebSocket session

    // Called when the client connects to the server
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }

    // Called when a message is received from the server
    public void onMessage(String message) {
        if (message.equals("start")) { // Start generating numbers if "start" message is received
            startGeneratingNumbers();
        } else if (message.startsWith("stop")) { // Stop generating numbers if "stop" message is received
            String[] parts = message.split(",", 3);
            int total = Integer.parseInt(parts[1]);
            String numList = parts[2];
            System.out.println("Final Total: " + total);
            System.out.println("numList: " + numList);
            try {
                session.close(); // Close the WebSocket session
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Start a new thread to generate and send random numbers to the server every 10ms
    private void startGeneratingNumbers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        TimeUnit.MILLISECONDS.sleep(10); // Sleep for 10 milliseconds
                        int num = random.nextInt(13); // Generate a random number between 0 and 12
                        session.getBasicRemote().sendText(String.valueOf(num)); // Send the number to the server
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
