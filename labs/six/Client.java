package labs.six;

import java.io.*;
import java.net.Socket;

/**
 * Created by sean on 12/7/2014.
 */
public class Client {
    private Socket client;

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }

    public Client() {
    }

    public void connect() {
        try {
            client = new Socket("localhost", 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new EventLoop().begin();
    }

    public void disconnect() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request) {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(client.getOutputStream());
            writer.writeObject(request);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response readReply() {
        ObjectInputStream reader = null;
        Response response = null;
        try {
            reader = new ObjectInputStream(client.getInputStream());
            response = (Response) reader.readObject();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
