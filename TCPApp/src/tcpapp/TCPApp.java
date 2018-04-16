/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpapp;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author Алексей
 */
public class TCPApp extends Application {
    
    private static Logger log = Logger.getLogger(TCPApp.class.getName());
    private static FileHandler fh;
    TCPClient client;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        fh = new FileHandler("logger.log");
        SimpleFormatter format = new SimpleFormatter();
        fh.setFormatter(format);
        log.addHandler(fh);
        
        //оформление окна
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(20);
        root.setVgap(8);
        root.setOrientation(Orientation.VERTICAL);
        
        //оформление текстового поля
        TextField textField = new TextField();
        textField.setText("Enter value...");
        textField.setMinHeight(30);
        textField.setMinWidth(280);
        root.getChildren().add(textField);
        
        //оформление radiobutton-ов
        ToggleGroup tg = new ToggleGroup();
        RadioButton rb1 = new RadioButton();
        RadioButton rb2 = new RadioButton();
        rb1.setText("Ascending");
        rb2.setText("Descending");
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        root.getChildren().add(rb1);
        root.getChildren().add(rb2);
        
        //оформление кнопки
        Button btn = new Button();
        btn.setText("Send!");
        root.getChildren().add(btn);
        
        //оформление вывода результата
        Label result = new Label();
        result.setVisible(false);
        root.getChildren().add(result);
        
        //действия при нажатии на кнопку
        btn.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                try{
                    //result.setText("");
                    client = new TCPClient();
                    log.info("- - - Connection has been successfully established. - - -\n");
                    client.sendToServer(textField.getText());
                    if (rb1.isSelected())
                    client.sendToServer("true"); 
                    else client.sendToServer("false");
                    log.info("- - - Data has been sent to server. - - -\n");
                    result.setText(client.getMessage());
                    result.setVisible(true);
                }
                catch(IOException IO){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Exception: " + IO);
                    alert.showAndWait();
                }
            }
                
    });
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("TCPApplication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
