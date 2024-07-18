package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = in.readLine();
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Server says: " + str);
             
        }
        catch(IOException e) {
            
        }
      
    }
    
}
