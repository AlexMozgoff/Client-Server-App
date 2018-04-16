/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Алексей
 */
public class TCPClient {
    
    private final Socket clientSocket;
    PrintWriter outToServer;
    
    public TCPClient() throws IOException{
        clientSocket = new Socket("localhost", 4646);
        outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
    }
    
    public void sendToServer(String pack){
        outToServer.println(pack);
    }
    
    public String getMessage()throws IOException {
        String message = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
        return message;
    }
    
    public void close() throws IOException {
        clientSocket.close();
    }
    
}
