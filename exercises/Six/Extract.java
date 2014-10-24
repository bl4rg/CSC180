package exercisesSix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
	
	public List<Number> extractNumbers(String equation) {
		List<Number> numbers = new ArrayList<Number>();
		for(String item : equation.split("\\W")) {
			numbers.add(Integer.parseInt(item));
		}
		return numbers;
	}
	
	public List<String> extractOperators(String equation) {
		List<String> operators = new ArrayList<String>();
		for(String item : equation.split("\\d")) {
			operators.add(item);
		}
		return operators;
	}
	
	public List<String> extractEmails(String body) {
		List<String> emails = new ArrayList<String>();
		for(String item : body.split(" ")) {
			if(item.matches("(.+[\"@\"].+[(.com)(.edu)])")) {
				emails.add(item);
			}
		}
		return emails;
	}
	
	public void extractListBody(String list) {
		Pattern pattern = Pattern.compile("<li>(.+)</li>", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(list);
		while(matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
