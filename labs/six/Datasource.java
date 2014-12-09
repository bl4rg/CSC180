package labs.six;
import java.io.IOException;
import java.util.Set;

public interface Datasource {

	public abstract Auction findById(Long id) throws IOException;

	public abstract Set<Auction> findAll() throws IOException;

	public abstract Auction update(Auction a) throws IOException;

	public abstract void delete(Long id) throws IOException;

}