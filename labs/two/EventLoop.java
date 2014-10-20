package labs.two;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EventLoop {
	private Queue<Event> toDo = new LinkedList<Event>();
	private Queue<Event> done = new LinkedList<Event>();
	
	public EventLoop() {
		begin();
	}
	
	public void begin() {
		AuctionService as = new InMemoryAuctionService();
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			done.add(new DefaultState(scanner, as));
			while(!toDo.isEmpty()) {
				Event event = (Event)toDo.poll();
				event.show();
				Event next = event.next();
				done.offer(next);
			}
			Queue<Event> temp = toDo;
			toDo = done;
			done = temp;
		}	
	}
}
