package cadillac.example.com.cadillac.bean;

/**
 * Created by iris on 2017/12/27.
 */

public class MessageEvent {
    private String type;
    private String message;

    public MessageEvent(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
