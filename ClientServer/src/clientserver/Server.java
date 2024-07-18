package clientserver;

import java.io.IOException;
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
            ServerSocket ss = new ServerSocket(9806);
            Socket s = ss.accept(); 
            System.out.println("Client Connected");
        }
        catch(IOException e) {
            
        }
      
    }
    
}
