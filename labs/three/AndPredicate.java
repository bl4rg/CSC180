package labs.three;

public class AndPredicate<T> implements Predicate<T> {
	private Predicate<T> one;
	private Predicate<T> two;
	
	public AndPredicate(Predicate<T> left, Predicate<T> right) {
		this.one = left;
		this.two = right;
	}

	@Override
	public boolean evaluate(T t) {
		return one.evaluate(t) && two.evaluate(t);
	}
}
