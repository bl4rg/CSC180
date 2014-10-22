package exercisesFive;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class SimpleDateFormatConverterTest {

	@Test
	public void test() {
		Converter<Date> dates = new SimpleDateFormatConverter();
		Converter<Date> dates2 = new SimpleDateFormatConverter(new SimpleDateFormat("dd MM yy"));
		Converter<Date> dates3 = new SimpleDateFormatConverter(new SimpleDateFormat("d M y"), new SimpleDateFormat("d M y"));
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DATE, 21);
		Date d = cal.getTime();

		String formatted = dates.format(d);
		System.out.println(formatted);
		Assert.assertNotNull("21 October 2015", formatted);
		
		String formatted2 = dates2.format(d);
		System.out.println(formatted2);
		Assert.assertNotNull("02 10 15", formatted2);
		
		String formatted3 = dates3.format(d);
		System.out.println(formatted3);
		Assert.assertNotNull("2 10 2015", formatted3);

		cal.set(Calendar.DATE, 2);
		d = cal.getTime();

		Assert.assertEquals("2 October 2015", dates.format(d));
		Assert.assertEquals("02 10 15", dates2.format(d));
		Assert.assertEquals("2 10 2015", dates3.format(d));
	}

}
