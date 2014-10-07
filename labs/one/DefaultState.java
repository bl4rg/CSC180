import java.util.Queue;


public class DefaultState {
	public DefaultState(Queue list, String newUser) {
		list.offer(newUser);
	}
}
