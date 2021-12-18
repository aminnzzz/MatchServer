import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Runner extends Application {
    private static Person me;
    private static ArrayList<Person> list = new ArrayList<>();
    private static ArrayList<String> userIDs = new ArrayList<>();
    private static ArrayList<Person> myMatches = new ArrayList<>();
    private static Person p1;

    public static  void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("user app");
        TextArea text = new TextArea();
        text.appendText("welcome to the users app\n"+
                "\nin this program you create an account" +
                "\nit gets added to database and you can also connect"+
                "\nto the server to get users from server"+
                "\n\n                   author : Amin Nazemzadeh" +
                "\n*****************************************************");
        text.setEditable(false);
        text.setFont(Font.font(22));
        text.setStyle("-fx-text-inner-color: red;");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(text);
        StackPane pane = new StackPane();
        pane.setStyle("-fx-border-color: red;");
        Button button = new Button("START");
        button.setStyle("-fx-background-color: red;");
        HBox hbox = new HBox();
        button.setMinSize(70,50);
        hbox.getChildren().addAll(button);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        pane.getChildren().addAll(vBox,hbox);
        button.setOnAction((event) -> {
            primaryStage.close();
            mainMenu();
        });
        Scene scene = new Scene(pane,500,400);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
    public static void mainMenu(){
        Stage stage = new Stage();
        VBox vbox = new VBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();
        Button b1 = new Button("create an account");
        Button b2 = new Button("show all users");
        Button b3 = new Button("server");
        h1.getChildren().addAll(b1);
        h2.getChildren().addAll(b2);
        h3.getChildren().addAll(b3);
        vbox.getChildren().addAll(h1,h2,h3);//no h3
        h1.setAlignment(Pos.TOP_CENTER);
        h2.setAlignment(Pos.CENTER);
        h3.setAlignment(Pos.BOTTOM_CENTER);
        b1.setMinSize(60,60);
        b2.setMinSize(60,60);
        b3.setMinSize(60,60);
        b2.setStyle("-fx-background-color: red;");
        b3.setStyle("-fx-background-color: blue;");
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(20,10,10,10));
        b1.setOnAction((event) -> {
            stage.close();
            createAccount();
        });
        b2.setOnAction((event) -> {
            stage.close();
            try {
//
                showAllUsers();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        b3.setOnAction((event) -> {
            stage.close();
            server();
        });
        Scene scene = new Scene(vbox,300,300);
        stage.setScene(scene);
        stage.show();
    }
    public static void createAccount(){
        //also listen for clients from this server


        Stage stage1 = new Stage();
        GridPane pane = new GridPane();
        Text t1 = new Text("first name :");
        Text t2 = new Text("last name :");
        Text t3 = new Text("age :");
        Text t4 = new Text("sexual orientation :");
        Text t5 = new Text("userID :");
        Text t6 = new Text("phone number :");
        Text t7 = new Text("description BIO :");
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();
        TextField tf4 = new TextField();
        TextField tf5 = new TextField();
        TextField tf6 = new TextField();
        TextArea ta = new TextArea();
        RadioButton r1 = new RadioButton("male");
        RadioButton r2 = new RadioButton("female");
        Button bt1 = new Button("sign up");
        bt1.setMinSize(100,15);
        pane.setMinSize(400,400);
        pane.setVgap(5);
        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.add(t1,0,0);
        pane.add(tf1,1,0);
        pane.add(t2,0,1);
        pane.add(tf2,1,1);
        pane.add(t3,0,2);
        pane.add(tf3,1,2);
        pane.add(t4,0,3);
        pane.add(tf4,1,3);
        pane.add(t5,0,4);
        pane.add(tf5,1,4);
        pane.add(t6,0,5);
        pane.add(tf6,1,5);
        pane.add(t7,0,6);
        pane.add(ta,1,6);
        pane.add(r1,0,7);
        pane.add(r2,1,7);
        pane.add(bt1,1,8);
        bt1.setOnAction((event) -> {
            if(tf1.getText().length()==0 || tf2.getText().length()==0 || tf3.getText().length()==0 || tf4.getText().length()==0 || tf5.getText().length()==0 || tf6.getText().length()==0 || ta.getText().length()==0 ){
                if((r1.isSelected()!= true & r2.isSelected()!= true)){
                    stage1.close();
                    makeSure();
                }
            }
            else if (r1.isSelected()){
                Male m = new Male(tf1.getText(),tf2.getText(),Integer.parseInt(tf5.getText()),Long.parseLong(tf6.getText()),Integer.parseInt(tf3.getText()),tf4.getText(),ta.getText());
                list.add(m);
                try {
                    Client.sendUser(m);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    int user = Integer.parseInt(tf5.getText());
                    String name = tf1.getText();
                    int age = Integer.parseInt(tf3.getText());
                    String sexuality = tf4.getText();
                    String bio = ta.getText();
                    String male = "male";
                    Connection conn = Connect.getConnection();
                    String query = "insert into new_table VALUES('"+user+"', '"+name+"', '"+age+"', '"+sexuality+"', '"+male+"', '"+bio+"')";
                    PreparedStatement insert = conn.prepareStatement(query);
                    insert.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            else{
                Female m = new Female(tf1.getText(),tf2.getText(),Integer.parseInt(tf5.getText()),Long.parseLong(tf6.getText()),Integer.parseInt(tf3.getText()),tf4.getText(),ta.getText());
                list.add(m);
                try {
                    Client.sendUser(m);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    int user = Integer.parseInt(tf5.getText());
                    String name = tf1.getText();
                    int age = Integer.parseInt(tf3.getText());
                    String sexuality = tf4.getText();
                    String bio = ta.getText();
                    String female = "female";
                    Connection conn = Connect.getConnection();
                    String query = "insert into new_table VALUES('"+user+"', '"+name+"', '"+age+"', '"+sexuality+"','"+female+"', '"+bio+"')";
                    PreparedStatement insert = conn.prepareStatement(query);
                    insert.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            stage1.close();
            mainMenu();
        });
        Scene scene = new Scene(pane);
        stage1.setScene(scene);
        stage1.show();
    }
    public static void showAllUsers() throws IOException, ClassNotFoundException {
        // read from file the list of users
            try {
                File output = new File("UserInfo.txt");
                FileWriter fw = new FileWriter(output);
                for(Person x:list) {
                    fw.write(x.getUserID() + " " + x.getFirstName() + " " + x.getAge() + " " + x.getSexualOrientation() + " " + x.getGender() + " " + x.getDescriptionBIO() + "\n");
                }
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        String info = "";
        File file = new File("userInfo.txt");
        Scanner reader = new Scanner(file);
        Stage stage = new Stage();
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        HBox hBox1 = new HBox();
        Button b1 = new Button("show all info");
        Label label = new Label("whom you would like to know more(userID)");
        TextField t = new TextField();
        hbox.getChildren().addAll(label,t,b1);
        TextArea ta1 = new TextArea();
        TextArea ta2 = new TextArea();
        Label ll = new Label("sort by :      ");
        RadioButton r1 = new RadioButton(" age ");
        RadioButton r2 = new RadioButton(" first name ");
        Button b = new Button("sort");
        Button b2 = new Button("menu");
        hBox1.getChildren().addAll(ll,r1,r2,b,b2);
        vbox.getChildren().addAll(ta1,hbox,ta2,hBox1);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20,10,10,10));
        while(reader.hasNextLine()){
            info = reader.nextLine()+"\n";
            ta1.appendText(info);
        }
        ta1.appendText("\nalso from the server(this is to prove we can get data from server):\n");
        ArrayList<Person> x = Client.getUsersList();
        for(Person p : x){
            ta1.appendText(p.toString());
        }
        reader.close();
        b1.setOnAction((event) -> {
            ta2.clear();
            for (Person p: list){
                if(Integer.parseInt(t.getText())==p.getUserID()){
                    ta2.appendText(p.toString());
                }
            }
        });
        b2.setOnAction((event) -> {
            stage.close();
            mainMenu();
        });
        b.setOnAction((event) -> {
            if(r1.isSelected()==true){
                ta2.clear();
                sort(1, list);
                for(int i = 0; i < list.size(); i++){
                    ta2.appendText(list.get(i).toString());
                }

            }
            else {
                ta2.clear();
                sort(2, list);
                for (int i = 0; i < list.size(); i++) {
                    ta2.appendText(list.get(i).toString());
                }
            }
        });
        Scene scene = new Scene(vbox,600,300);
        stage.setScene(scene);
        stage.show();
    }

    public static void sort(int x, List<Person> list) {
        if (x==1) {
            int n = list.size();
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (list.get(j).getAge() > list.get(j + 1).getAge() ){
                        Person temp = list.get(j+1);
                        list.set(j,temp);
                    }
        }
        else{
            int n = list.size();
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (list.get(j).getFirstName().charAt(0) > list.get(j + 1).getFirstName().charAt(0) ){
                        Person temp = list.get(j+1);
                        list.set(j,temp);
                    }
        }
    }
    public static void server(){
        Stage stage = new Stage();
        VBox vBox = new VBox();
        Label label = new Label("click to connect to server and get users count");
        Button b1 = new Button("server");
        Button b2 = new Button("menu");
        TextField t = new TextField();
        TextArea ta = new TextArea();
        vBox.getChildren().addAll(label,b1,t,ta,b2);
        b1.setOnAction((event) -> {
            try {
                String x = String.valueOf(Client.getAllUsersCount());
                t.setText("list in the server has the size of: "+x);
                ArrayList<Person> z = Client.getUsersList();
                ta.appendText("the users in the server are : \n");
                for(Person p : z){
                    ta.appendText(p.toString()+"\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        b2.setOnAction((event) -> {
            stage.close();
            mainMenu();
        });
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public static void makeSure(){
        Stage stage = new Stage();
        VBox vBox = new VBox();
        Label label = new Label("make sure you complete all parts");
        Button ok = new Button("ok");
        vBox.getChildren().addAll(label,ok);
        ok.setOnAction((event) -> {

            stage.close();
            createAccount();
        });
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();

    }
}
