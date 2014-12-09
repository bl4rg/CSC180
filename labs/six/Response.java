package labs.six;

import java.io.Serializable;

/**
 * Created by sean on 12/7/2014.
 */
enum Protocol {
    SUCCESS, FAILURE, UNKNOWN;}

public class Response  implements Serializable{
    private String response;
    private Protocol protocol;
    private Integer code;

    public Response(Protocol protocol) {
        setProtocol(protocol);
        this.response = "" + protocol + code;
    }

    public String getResponse() {
        return response;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
        setCode();
    }

    public void setCode() {
        if(protocol == Protocol.SUCCESS) {
            this.code = 200;
        }else if(protocol == Protocol.FAILURE) {
            this.code = 400;
        }else if(protocol == Protocol.UNKNOWN) {
            this.code = 404;
        }
    }
}
