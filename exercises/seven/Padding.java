package seven;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 11/4/2014.
 */
public class Padding {
    private final Object EMPTY = null;
    private final char END_OF_STRING = 0x03;
    private final String ASCII = "(.*)[\\x00\\x08\\x0B\\x0C\\x0E-\\x1F]*";

    public String pad(Object obj, int len) {
        String output;
        if(obj == EMPTY) {
            output = "";
        }else {
            output = "" + obj;
        }
        if(len == 0) {
            output = "";
        }else if(output.length() < len) {
            for(int index = 0; index < (len - output.length()); index++) {
                output += END_OF_STRING;
            }
        }else if(output.length() > len) {
            output = output.substring(0, (len - 1));
        }
        return output;
    }

    public Object unpad(String str) {
        Object output = EMPTY;
        Matcher temp = regex(str);
        output = temp.group(1);
        return output;
    }

    public <T> T unpad(String str, Class<T> clazz) {
        T output = (T) EMPTY;
        Matcher temp = regex(str);
        output = (T) temp.group(1);
        return output;
    }

    public Matcher regex(String line) {
        Pattern pattern = Pattern.compile(ASCII);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher;
    }
}
