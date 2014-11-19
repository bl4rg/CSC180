package labs.three;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EventLoop {
	private Scanner input = new Scanner(System.in);
	private AuctionService as = new InMemoryAuctionService("src.html");
	private final Event DEFAULT_STATE = new DefaultState(input, as);

	private Queue<Event> toDo = new LinkedList<Event>();
	private Queue<Event> done = new LinkedList<Event>();
	
	public void begin() {
		while(true) {
			done.add(DEFAULT_STATE);
			while(!toDo.isEmpty()) {
				Event event = (Event)toDo.poll();
				event.show();
				Event next = event.next();
				if(next != null) {
					done.offer(next);
				}
			}
			Queue<Event> temp = toDo;
			toDo = done;
			done = temp;
		}	
	}
}
