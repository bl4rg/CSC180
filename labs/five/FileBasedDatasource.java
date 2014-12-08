package labs.five;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class FileBasedDatasource implements Datasource {
	private static Long id = 0L;
	private File values;
	private Map<Long, Integer> positionByKey = new HashMap<Long, Integer>();
	private int length = 355;

	public FileBasedDatasource(String fileName) throws IOException {
		values = new File(fileName);
		values.createNewFile();
		
		int totalBytes = 0;
		byte[] b = new byte[length];
		try ( FileInputStream fis = new FileInputStream(values) ) {
			int read = -1;
			while ( ( read = fis.read(b, 0, b.length) ) != -1 ) {
				Auction a = fromByte(b);
				if ( a != null ) {
					positionByKey.put(a.getId(), totalBytes);
					if ( id < a.getId() ) {
						id = a.getId();
					}
				}
				totalBytes += read;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see labs.five.Datasource#findById(java.lang.Long)
	 */
	@Override
	public Auction findById(Long id) throws IOException {
		try ( RandomAccessFile raf = new RandomAccessFile(values, "r") ) {
			raf.seek(positionByKey.get(id));
			
			byte[] b = new byte[length];
			raf.read(b, 0, length);
			
			return fromByte(b);
		}
	}
	
	/* (non-Javadoc)
	 * @see labs.five.Datasource#findAll()
	 */
	@Override
	public Set<Auction> findAll() throws IOException {
		Set<Auction> auctions = new HashSet<Auction>();
		byte[] b = new byte[length];
		try ( FileInputStream fis = new FileInputStream(values) ) {
			int read = -1;
			while ( ( read = fis.read(b, 0, b.length) ) != -1 ) {
				Auction a = fromByte(b);
				if ( a != null ) {
					auctions.add(a);
				}
			}
		}
		return auctions;
	}
	
	private char padChar = 23;
	
	private Auction fromByte(byte[] b) {
		String auctionAsString = new String(b);
		
		if ( auctionAsString.startsWith("$$tombstone$$") ) {
			return null;
		}
		
		Long id = Long.parseLong(unpad(auctionAsString.substring(0, 15)));
		String name = unpad(auctionAsString.substring(15, 105));
		String description = unpad(auctionAsString.substring(105, 305));
		Double currentBid = Double.parseDouble(unpad(auctionAsString.substring(305, 315)));
		String owner = unpad(auctionAsString.substring(315, 335));
		Integer numberOfBids = Integer.parseInt(unpad(auctionAsString.substring(335, 340)));
		Long endTimeMillis = Long.parseLong(unpad(auctionAsString.substring(340, 355)));
		
		return new Auction(id, name, description, currentBid, owner, numberOfBids, endTimeMillis);
	}
	
	private byte[] toByte(Auction a) {
		String s = pad(a.getId(), 15) + pad(a.getName(), 90) + pad(a.getDescription(), 200) + pad(a.getCurrentBid(), 10) + pad(a.getOwner(), 20) + pad(a.getNumberOfBids(), 5) + pad(a.getEndTimeMillis(), 15);
		return s.getBytes(); 
	}
	
	private String unpad(String obj) {
		StringBuilder sb = new StringBuilder();
		int charAt = 0;
		while ( charAt < obj.length() && obj.charAt(charAt) != padChar ) {
			sb.append(obj.charAt(charAt++));
		}
		return sb.toString();
	}
	
	private String pad(Object obj, int len) {
		String original = obj.toString();
		original = original.substring(0, Math.min(original.length(), len));
		StringBuilder sb = new StringBuilder(original);
		for ( int i = sb.length(); i < len; i++ ) {
			sb.append(padChar);
		}
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see labs.five.Datasource#update(labs.five.Auction)
	 */
	@Override	
	public Auction update(Auction a) throws IOException {
		if ( a.getId() == null ) {
			Auction copy = new Auction(++id, a.getName(), a.getDescription(), a.getCurrentBid(), a.getOwner(), a.getNumberOfBids(), a.getEndTimeMillis());
			try ( RandomAccessFile raf = new RandomAccessFile(values, "rw") ) {
				raf.seek(positionByKey.size() * length);
				byte[] b = toByte(copy);
				raf.write(b);
				positionByKey.put(copy.getId(), positionByKey.size() * length);
			}
			return copy;
		} else {
			try ( RandomAccessFile raf = new RandomAccessFile(values, "rw") ) {
				raf.seek(positionByKey.get(a.getId()));
				byte[] b = toByte(a);
				raf.write(b);
			}
			return a;
		}
	}
	
	/* (non-Javadoc)
	 * @see labs.five.Datasource#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws IOException {
		try ( RandomAccessFile raf = new RandomAccessFile(values, "rw") ) {
			raf.seek(positionByKey.get(id));
			byte[] b = "$$tombstone$$".getBytes();
			raf.write(b);
			positionByKey.remove(id);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		Datasource d = new FileBasedDatasource("auction.dat");
		Auction newAuction = new Auction(null, "Yet Another beautiful treehouse", "Will install for free in tree of your choice!", .01, "john", 0, System.currentTimeMillis());
		newAuction = d.update(newAuction);
		newAuction.setCurrentBid(newAuction.getCurrentBid() + 1);
		newAuction = d.update(newAuction);
		newAuction = d.findById(newAuction.getId());
		System.out.println(newAuction);
		d.delete(newAuction.getId());
		System.out.println(d.findAll());
	}
}
