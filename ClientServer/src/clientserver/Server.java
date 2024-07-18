package clientserver;

import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author eniomagalhaes
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9806);
        Socket s = ss.accept(); 
        System.out.println("Client Connected");
    }
    
}
