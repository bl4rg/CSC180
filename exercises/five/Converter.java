package exercisesFive;

public interface Converter<T> {
	public T pasre(String fromString);
	public String format(T fromObject);
}
