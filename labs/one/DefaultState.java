import java.util.Scanner;


public class DefaultState implements Event{	
	boolean quit = false;
	String userName = "";
	Scanner scan = new Scanner(System.in);
	
	@Override
	public void show() {
		System.out.println("New user, would you like to log in? (Hit enter to decline)");
		userName = scan.nextLine();
		if(userName.isEmpty()) {
			quit = true;
		}
	}

	@Override
	public Event next() {
		Event temp = null;
		if(!quit){
			temp = new UserHomeState(userName);
		}
		return temp;
	}
}
