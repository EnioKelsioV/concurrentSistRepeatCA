package clientserver;

import java.net.*;
import java.io.*;

/**
 *
 * @author eniomagalhaes
 */
public class Client {
    public static void main (String[] args) {
        Socket s = new Socket("localhost", 4999);
    }
    
}
