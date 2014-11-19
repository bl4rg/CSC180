package labs.three;

/**
 * Created by sean on 11/19/2014.
 */
public class Padding {
    private static final char pad = ' ';

    public Padding() {}

    public String unpad(String obj) {
        StringBuilder output = new StringBuilder();
        int charPos = 0;
        while(charPos < obj.length() && obj.charAt(charPos) != pad) {
            output.append(obj.charAt(charPos++));
        }
        return output.toString();
    }

    public String pad(Object obj, int add) {
        String original = obj.toString();
        StringBuilder output = new StringBuilder();
        original = original.substring(0, Math.min(original.length(), add));
        output.append(original);
        for(int index = output.length(); index < add; index++) {
            output.append(pad);
        }
        return output.toString();
    }
}
