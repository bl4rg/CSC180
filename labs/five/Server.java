package labs.five;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 12/7/2014.
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket socket = new Socket();
    private Scanner scanner = new Scanner(System.in);

    FileBasedDatasource fbd;
    {
        try {
            fbd = new FileBasedDatasource("auction.dat");
        } catch ( IOException e ) {
            throw new IllegalStateException("Could not create needed datasource to run program: " + e);
        }
    }

    private AuctionService as = new RemoteClientAuctionService(fbd);

    private Client passedClient;
    public Server() {}

    public Server( Client passedClient){
        this.passedClient = passedClient;
    }

    public void connect() {
        try {
            serverSocket = new ServerSocket(3000);
            socket = serverSocket.accept();
            // put code here that either writes out to client or waits for response from the client
            socket.setKeepAlive(true);
            sendReply(new Response(Protocol.SUCCESS));
        } catch (IOException e) {
            System.out.println("connection failed");
            e.printStackTrace();
        }

        System.out.println(readRequest());
    }

    public void sendReply(Response response) {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            writer.writeObject(response);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request readRequest() {
        ObjectInputStream reader = null;
        Request request = null;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            request = (Request) reader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            return request;
    }

    public State extractRequest(String...command) {
        State temp = null;
        Pattern request = Pattern.compile("([CcSsLlEeDdUu])(reate|earch|ogin|ogout|dit|elete|serhome)");
        String method = command[0];
        String parameters = command[1];
        String body = command[2];
        Matcher match = request.matcher(method);
        match.find();

        if(match.group(1).toLowerCase().startsWith("s")) {
            if(parameters == null || parameters.isEmpty()) {
                temp = new AuctionSearchState(parameters, scanner, as, passedClient, this);
            }else {
                String[] brokenDown = parameters.split(",");
                Auction[] results = as.search(brokenDown[1]);
                temp = new SearchResultState(parameters, brokenDown[0], results, scanner, as, passedClient, this);
            }
        }else if(match.group(1).toLowerCase().startsWith("c")){
            if(body == null || body.isEmpty()) {
                temp = new AuctionCreateState(parameters, scanner, as, passedClient, this);
            }else {
                String[] properties = body.split(",");
                Auction a = new Auction(Long.parseLong(properties[0]), properties[1], properties[2], Double.parseDouble(properties[3]), properties[4], Integer.parseInt(properties[5]), Long.parseLong(properties[6]));
                as.create(a);
            }
        }else if(match.group(1).toLowerCase().startsWith("u")){
            temp = new UserHomeState(parameters, scanner, as, passedClient, this);
        }else if(match.group(1).toLowerCase().startsWith("l")){
            if(match.group(1).toLowerCase().endsWith("login")) {
                temp = new UserHomeState(parameters, scanner, as, passedClient, this);
            }else if(match.group(1).toLowerCase().endsWith("login")) {
                temp = null;
            }
        }else if(match.group(1).toLowerCase().startsWith("b")){
            String[] brokenDown = parameters.split(",");
            as.bid(parameters, Long.parseLong(brokenDown[0]));
            temp = new SearchResultState(brokenDown[1], brokenDown[2], as.search(brokenDown[2]), scanner, as, passedClient, this);
        }else if(match.group(1).toLowerCase().startsWith("e")){

        }else if(match.group(1).toLowerCase().startsWith("d")){

        }else {
            sendReply(new Response(Protocol.UNKNOWN));
        }

        return temp;
    }

    public static void main(String[] args) {
        Server server = new Server(new Client());
        server.connect();
    }
}
