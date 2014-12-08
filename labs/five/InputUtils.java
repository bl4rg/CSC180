package labs.five;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class InputUtils {
	private static final char padChar = ' ';
	
	public static String unpad(String obj) {
		StringBuilder sb = new StringBuilder();
		int charAt = 0;
		while ( charAt < obj.length() && obj.charAt(charAt) != padChar ) {
			sb.append(obj.charAt(charAt++));
		}
		return sb.toString();
	}
	
	public static String pad(Object obj, int len) {
		String original = obj.toString();
		original = original.substring(0, Math.min(original.length(), len));
		StringBuilder sb = new StringBuilder(original);
		for ( int i = sb.length(); i < len; i++ ) {
			sb.append(padChar);
		}
		return sb.toString();
	}
	
	public static String defaultString(String original, String def) {
		
		return original == null || original.isEmpty() ? def : original;
	}
	
	public static Double promptForDouble(String message, Scanner scanner, Double def) {
		Scanner s;
		
		do {
			System.out.print("\n" + message);
			String input = defaultString(scanner.nextLine(), String.valueOf(def));
			s = new Scanner(input);
		} while (!s.hasNextDouble());
		
		return s.nextDouble();
	}
	
	public static Matcher promptForPattern(String message, Scanner scanner, Pattern pattern, String def) {
		
		System.out.print("\n" + message);
		
		Matcher m = pattern.matcher(defaultString(scanner.nextLine(), def));
		if ( m.find() ) {
			return m;
		} else {
			return promptForPattern(message, scanner, pattern, def);
		}
	}

	public static boolean isEmpty(String input) {
		return input == null || input.isEmpty();
	}
}
