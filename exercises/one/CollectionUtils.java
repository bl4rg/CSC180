package one;

import java.util.ArrayList;

public class CollectionUtils extends java.lang.Object{
	public static int cardinality(java.lang.Object obj, ArrayList<String> coll) {
		int count = 0;
		for(int index = 0; index < coll.size(); index++){
			if(coll.get(index).toString().trim().equals((String) obj)){
				count++;
			}
		}
		return count;
	}
	
	public static void main(String[] args) {
		ArrayList<String> test = new ArrayList<String>();
		test.add("This ");
		test.add("is ");
		test.add("a ");
		test.add("TEST");
		
		System.out.println("Number of matches: " + cardinality("is", test));
		System.out.println("Number of matches: " + cardinality("a", test));
		System.out.println("Number of matches: " + cardinality("TEST", test));
		System.out.println("Number of matches: " + cardinality("", test));
		System.out.println("Number of matches: " + cardinality("test", test));
	}
}
