package exercisesFive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatConverter implements Converter<Date>{
	private SimpleDateFormat defaultFormat = new SimpleDateFormat("d MMMM yyyy");
	private SimpleDateFormat sdf;
	private SimpleDateFormat sdf2;
	
	
	public SimpleDateFormatConverter() {
		this.sdf = defaultFormat;
		this.sdf2 = this.sdf;
	}
	
	public SimpleDateFormatConverter(SimpleDateFormat sdf) {
		this.sdf = sdf;
		this.sdf2 = this.sdf;
	}
	
	public SimpleDateFormatConverter(SimpleDateFormat forParsing, SimpleDateFormat forFormatting) {
		this.sdf = forParsing;
		this.sdf2 = forFormatting;
	}

	@Override
	public Date pasre(String fromString) {
		Date temp = null;
		try {
			temp = sdf.parse(fromString);
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public String format(Date fromObject) {
		String output = sdf2.format(fromObject);
		return output;
	}

}
