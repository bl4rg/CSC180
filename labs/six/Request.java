package labs.six;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by sean on 12/7/2014.
 */
public class Request implements Serializable{
    private String methodName;
    private String simpleMethodParams;
    private byte[] javaSerializedBody;

    public Request(String methodName, String simpleMethodParams, String javaSerializedBody) {
        this.methodName = methodName;
        this.simpleMethodParams = simpleMethodParams;
        this.javaSerializedBody = javaSerializedBody.getBytes();
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSimpleMethodParams() {
        return simpleMethodParams;
    }

    public byte[] getJavaSerializedBody() {
        return javaSerializedBody;
    }

    public String getJavaBody() {
        String body = null;
        try {
            body =  new String(javaSerializedBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return body;
    }

    @Override
    public String toString() {
        return "" + methodName + "," + simpleMethodParams + "," + getJavaBody();
    }
}
