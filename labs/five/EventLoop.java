package labs.five;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EventLoop {
	private final Scanner input = new Scanner(System.in);

	private final FileBasedDatasource fbd;
	{
		try {
			fbd = new FileBasedDatasource("auction.dat");
		} catch ( IOException e ) {
			throw new IllegalStateException("Could not create needed datasource to run program: " + e);
		}
	}
	
	private final AuctionService as = new RemoteClientAuctionService(fbd);
	private Client client;
	private Server server = new Server(input, as, client);
	private final State DEFAULT_STATE = new DefaultState(input, as, server);
	
	private Queue<State> activeQueue = new LinkedList<State>();
	private Queue<State> expiredQueue = new LinkedList<State>();

	public EventLoop() {}

	public void begin() {
		while ( true ) {
			expiredQueue.add(DEFAULT_STATE);
			while ( !activeQueue.isEmpty() ) {
				State state = (State)activeQueue.poll();
				state.show();
				State next = state.next();
				if ( next != null ) {
					expiredQueue.offer(next);
				}
			}
			Queue<State> temp = activeQueue;
			activeQueue = expiredQueue;
			expiredQueue = temp;
		}
	}
	
//	public static void main(String[] args) {
//		new EventLoop().begin();
//	}
}
