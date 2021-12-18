import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Client {
    public static int getAllUsersCount() throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        //establish socket connection to server
        socket = new Socket(host.getHostName(), 9876);
        //write to socket using ObjectOutputStream
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("users");
        //read the server response message
        ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("Server response: " + message);
        //close resources
        ois.close();
        oos.close();
        return Integer.parseInt(message);
    }

    public static void sendUser(Person user) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;

        //establish socket connection to server
        socket = new Socket(host.getHostName(), 9876);
        //write to socket using ObjectOutputStream
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(user.toString());
        //read the server response message
        //close resources
        oos.close();

    }
    public static ArrayList<Person> getUsersList() throws IOException, ClassNotFoundException {
        ArrayList<Person> x = new ArrayList<>();
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        //establish socket connection to server
        socket = new Socket(host.getHostName(), 9876);
        //write to socket using ObjectOutputStream
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("show");
        //read the server response message
        ois = new ObjectInputStream(socket.getInputStream());
        TreeMap<Integer, Person> message = (TreeMap<Integer, Person>) ois.readObject();
        for(int i = 0; i < message.size(); i++){
            x.add(message.get(i));
        }
        System.out.println("Server response: " + message.firstEntry());
        //close resources
        ois.close();
        oos.close();
        return x ;
    }

}