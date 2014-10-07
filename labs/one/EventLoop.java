import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EventLoop implements Event{
	private Queue<String> toDo = new LinkedList<String>();
	private Queue<String> done = new LinkedList<String>();
	private Scanner scan = new Scanner(System.in);
	private State state = State.Default;
	private boolean running = true;
	private Auction[] results = new Auction[1];
	
	public EventLoop() {
		begin();
	}
	
	public void begin() {
		while(running) {
			show();
			next();
			done.offer(toDo.poll());
			if(toDo.isEmpty()) {
				switchQueues(toDo, done);
			}
		}
		scan.close();
	}
	
	public void printItems(Auction[] items) {
		if(items.equals(null)) {
			for(Auction item : items) {
				System.out.print(item.getId() + "\t" + item.getName() + "\t\t" + item.getCurrentBid());
			}
		}else {
			System.out.println("	   null");
		}
	}
	
	public void switchQueues(Queue firstList, Queue secondList) {
		Queue<String> tempQueue1 = new LinkedList<String>();
		Queue<String> tempQueue2 = new LinkedList<String>();
		tempQueue1 = firstList;
		tempQueue2 = secondList;
		firstList = tempQueue2;
		secondList = tempQueue1;
	}

	@Override
	public void show() {
		if(state == state.Default){
			System.out.println("New User, would you like to log in? (hit enter to decline)");
		}else if(state == state.Home) {
			if(!toDo.isEmpty()) {
				System.out.println(toDo.peek() + ", what would you like to search for? (hit enter to decline)");
			}
		}else if(state == state.Search) {
			System.out.println(toDo.peek() + 
					", here are your search results: " +
					"\n============================" + 
					"\n===	Search Results	===" + 
					"\n============================\n");
			printItems(results);
			System.out.println("\n============================" +
					"\nEnter the item id to increase the bid by $1. Otherwise, enter another"
					+ "\nsearch: (hit enter to go back to home page)");
		}
	}

	@Override
	public Event next() {
		String input = scan.nextLine();
		if(state == state.Default) {
			new DefaultState(toDo, input);
			show();
			state = state.Home;
			next();
		}else if(state == state.Home) {
			new UserHomeState(input, results);
			show();
			if(!input.equals("")) {
				state = state.Search;
			}
			next();
		}else if(state == state.Search) {
			new SearchResultsState();
			show();
			if(!input.equals("")) {
				state = state.Home;
			}
			next();
		}else if(input.equals("0")){
			running = false;
		}
		return null;
	}
}
