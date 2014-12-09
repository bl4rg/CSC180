package labs.six;

public class OrPredicate<T> implements Predicate<T> {
	private Predicate<T> left;
	private Predicate<T> right;
	
	public OrPredicate(Predicate<T> left, Predicate<T> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean evaluate(T entity) {
		return left.evaluate(entity) || right.evaluate(entity);
	}
}
