package cronin.matt.moovingbits.Model;

/**
 * Created by mattcronin on 10/9/15.
 */
public class Header {

    public Header(long id, String key, String value, long requestId) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.requestId = requestId;
    }

    public long getId() {

        return id;
    }

    public Header() {
        this.id = 0;
        this.key = "";
        this.value = "";
        this.requestId = 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    private long id;
    private String key;
    private String value;
    private long requestId;

    @Override
    public String toString() {
        return key + value;
    }
}
