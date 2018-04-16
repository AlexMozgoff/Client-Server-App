/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Алексей
 */
public class TCPServer {

    
    private static Logger log1 = Logger.getLogger(TCPServer.class.getName()); 
    private static FileHandler fh;
    public static final int PORT = 4646;
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket welcomeSocket = new ServerSocket(TCPServer.PORT);
        fh = new FileHandler("logger.log1");
        SimpleFormatter format = new SimpleFormatter();
        fh.setFormatter(format);
        log1.addHandler(fh);
        log1.info("- - -Start ServerSocket (multi thread) accept on host: " + welcomeSocket.getInetAddress() + "; Port: " + welcomeSocket.getLocalPort() + " - - -\n");
        try {
            while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            log1.info("- - - ServerSocket (multi thread) accept connect from host: " + connectionSocket.getInetAddress() + "; Port: " + connectionSocket.getPort() + " - - -\n");
            try {
                Server server = new Server(connectionSocket);
                } catch (Exception e) {
                        
                }
            }
        } finally {
            welcomeSocket.close();
        }
        
    }
    
}
