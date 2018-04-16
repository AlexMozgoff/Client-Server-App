/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Алексей
 */
public class Server extends Thread {
    
     private final Socket clientSocket;
    
    public boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
     
    public Server(Socket client){
          this.clientSocket = client;
          start();
    }
    
    @Override
    public void run(){
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            //Thread.sleep(1000);
            BufferedReader read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String reader = read.readLine();
            char[] readerChar = reader.toCharArray();
          
            //убираем запятую
            if (reader.contains(",")){
                reader = reader.replace(",", "");
            }
            
            
            boolean isInt  = isInteger(reader);
            if ((isInt==true)&&(!reader.contains("-"))){
                
            String sort = read.readLine();
            //если по возрастанию
            if ("true".equals(sort)){
                for (int i = 0; i < readerChar.length; i++){
                    for (int j = 0; j < readerChar.length; j++){
                        if (readerChar[j] > readerChar[i]){
                            char temp = readerChar[i];
                            readerChar[i] = readerChar[j];
                            readerChar[j] = temp;
                        }
                    }
                }
                reader = Arrays.toString(readerChar);
                reader = reader.replace("[", "");
                reader = reader.replace("]", "");
                writer.println(reader);
                writer.flush();
            }
            
            //если по убыванию
            if ("false".equals(sort)){
                for (int i = 0; i < readerChar.length; i++){
                    for (int j = 0; j < readerChar.length; j++){
                        if (readerChar[j] < readerChar[i]){
                            char temp = readerChar[i];
                            readerChar[i] = readerChar[j];
                            readerChar[j] = temp;
                        }
                    }
                }
                reader = Arrays.toString(readerChar);
                reader = reader.replace("[", "");
                reader = reader.replace("]", "");
                writer.println(reader);
                writer.flush();
            }
            
            //если способ упорядочивания не выбран
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Sequence method was not chosen!");
                alert.showAndWait();
            }  
            }
            else {
                writer.println("Wrong Input!");
            }
            
        }         
        catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
       } //catch (InterruptedException ex) {
           //  Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
       //  }
    } 
    
    
}

