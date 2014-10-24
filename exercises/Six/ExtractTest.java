package exercisesSix;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtractTest {

	@Test
	public void test() {
		Extract test = new Extract();
		String equation = "3+2/40*1^17/34";
		String body = "sjohnson@student.neumont.edu email this email in order to reach me, or this email: b4k4.7326@gmail.com";
		String html = "<p>/nthis is a paragraph with a list"
				+ "<li>list item one</li>\n"
				+ "<li>list item two</li>\n"
				+ "<li>list item three</li>/n"
				+ "</p>";
		
		System.out.println(test.extractNumbers(equation).toString());
		System.out.println(test.extractOperators(equation));
		System.out.println(test.extractEmails(body));
		test.extractListBody(html);
	}
}
