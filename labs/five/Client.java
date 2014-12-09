package labs.five;

import java.io.*;
import java.net.Socket;

/**
 * Created by sean on 12/7/2014.
 */
public class Client {
    private Socket socket;

    public Client() {}

    public void connect() {
        try {
            socket = new Socket("localhost", 3000);
            socket.setKeepAlive(true);
            System.out.println(readReply().toString());
            sendRequest(new Request("Test","",""));
            new EventLoop(this).begin();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request) {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            writer.writeObject(request);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response readReply() {
        ObjectInputStream reader = null;
        Response response = null;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            response = (Response) reader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args) {
        Client user = new Client();
        user.connect();
    }
}
