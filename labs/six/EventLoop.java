package labs.six;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EventLoop {
	private GUI gui = new GUI();
	private final Scanner input = new Scanner(System.in);

	private final FileBasedDatasource fbd;
	
	{
		try {
			fbd = new FileBasedDatasource("auctions.dat");
		} catch ( IOException e ) {
			throw new IllegalStateException("Could not create needed datasource to run program: " + e);
		}
	}
	
	private final AuctionService as = new DatasourceAuctionService(fbd);
	
	private final State DEFAULT_STATE = new DefaultState(input, as, gui);
	
	private Queue<State> activeQueue = new LinkedList<State>();
	private Queue<State> expiredQueue = new LinkedList<State>();
	
	public void begin() {
		gui.init();
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
	
	public static void main(String[] args) {
		new EventLoop().begin();
	}
}
