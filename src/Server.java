import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Application {
    static ArrayList<Person> myList = new ArrayList<Person>();
    private int numberOfClients;
    private ObjectInputStream input;
    static TextArea t;
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        t = new javafx.scene.control.TextArea();
        ScrollPane s = new ScrollPane(t);
        Button bt = new Button("next");
        Button chat = new Button("chat");
        vBox.getChildren().addAll(s,bt,chat);
        Scene scene = new Scene(vBox,400,400);
        primaryStage.setTitle("matchMaking");
        primaryStage.setScene(scene);
        primaryStage.show();
        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(4646);
                while (true){
                    Socket match1 = serverSocket.accept();
                    Socket match2 = serverSocket.accept();
                    new Thread(new HandleChat(match1,match2)).start();

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
    public class HandleChat implements Runnable{
        private Socket match1;
        private Socket match2;
        private DataInputStream fromMatch1;
        private DataInputStream fromMatch2;
        private DataOutputStream toMatch1;
        private DataOutputStream toMatch2;
        private boolean continueChatting = true;
        public HandleChat(Socket match1, Socket match2){
            this.match1 = match1;
            this.match2 = match2;
        }

        @Override
        public void run() {
            try {
                DataInputStream fromMatch1 = new DataInputStream(match1.getInputStream());
                DataInputStream fromMatch2 = new DataInputStream(match2.getInputStream());
                DataOutputStream toMatch1 = new DataOutputStream(match1.getOutputStream());
                DataOutputStream toMatch2 = new DataOutputStream(match2.getOutputStream());
                while(continueChatting==true) {
                    String messageFrom1 = fromMatch1.readUTF();
                    String messageFrom2 = fromMatch2.readUTF();
                    Server.t.appendText(messageFrom1);
                    Server.t.appendText(messageFrom2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
