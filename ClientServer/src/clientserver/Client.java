package clientserver;

import java.net.Socket;
import java.io.*;

/**
 *
 * @author eniomagalhaes
 */

public class Client {
    public static void main(String[] args) {
        
        try
        {
        System.out.println("Client started");
        Socket s = new Socket("localhost", 9806);
        BufferedReader userInput = new BufferredReader(new InputStreamReader(System.in));
        System.out.println("Enter a String");
        String str = userInput.readLine();
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(str);
        }
        catch(IOException e)
        {
            }
    }
    
}
