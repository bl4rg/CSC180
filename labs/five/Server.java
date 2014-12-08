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
    ServerSocket server;
    Socket client;
    Scanner scanner;
    AuctionService as;
    Client passedClient;

    public static void main(String[] args) {
        FileBasedDatasource fbd;
        {
            try {
                fbd = new FileBasedDatasource("auction.dat");
            } catch ( IOException e ) {
                throw new IllegalStateException("Could not create needed datasource to run program: " + e);
            }
        }
        Server server = new Server(new Scanner(System.in), new RemoteClientAuctionService(fbd), new Client());
        server.connect();
    }

    public Server(Scanner scanner, AuctionService as, Client passedClient){
        this.scanner = scanner;
        this.as = as;
        this.passedClient = passedClient;
        try {
            server = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            client = server.accept();
        } catch (IOException e) {
            System.out.println("connection failed");
            e.printStackTrace();
        }
    }

    public void sendReply(Response response) {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(client.getOutputStream());
            writer.writeObject(response);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request readRequest() {
        ObjectInputStream reader = null;
        Request request = null;
        try {
            reader = new ObjectInputStream(client.getInputStream());
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
}
