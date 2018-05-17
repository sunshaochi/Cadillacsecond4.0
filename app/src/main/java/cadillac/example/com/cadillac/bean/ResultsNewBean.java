package cadillac.example.com.cadillac.bean;

/**
 * Created by iris on 2017/12/15.
 */

public class ResultsNewBean<T> {
    private String code;
    private String message;
    private T obj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
