import java.util.LinkedList;
import java.util.Queue;

public class EventLoop {
	private Queue<Event> toDo = new LinkedList<Event>();
	private Queue<Event> done = new LinkedList<Event>();
	private boolean running = true;
	
	public EventLoop() {
		begin();
	}
	
	public void begin() {
		toDo.add(new DefaultState());
		toDo.add(new DefaultState());
		toDo.add(new DefaultState());
		
		while(true) {
			while(running) {
				if(toDo.isEmpty()) {
					running = false;
				}else {
					Event temp = (Event) toDo.poll();
					temp.show();
					Event temp2 = temp.next();
					if(!temp2.equals(null)) {
						done.offer(temp.next());
					}
				}
				done.offer(new DefaultState());
				
				while(running) {
					if(done.isEmpty()) {
						running = false;
					}else {
						Event temp = (Event) done.poll();
						temp.show();
						Event temp2 = temp.next();
						if(!temp2.equals(null)) {
							done.offer(temp.next());
						}
					}
				}
				toDo.offer(new DefaultState());
			}
		}	
	}
}
