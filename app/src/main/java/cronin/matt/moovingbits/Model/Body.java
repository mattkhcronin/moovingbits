package cronin.matt.moovingbits.Model;

/**
 * Created by mattcronin on 10/9/15.
 */
public class Body {

    public Body() {
        this.id = 0;
        this.key = "";
        this.value = "";
        this.rawData = "";
        this.encodingType = "";
        this.requestId = 0;
    }

    public Body(long id, String key, String value, String rawData, String encodingType, long requestId) {

        this.id = id;
        this.key = key;
        this.value = value;
        this.rawData = rawData;
        this.encodingType = encodingType;
        this.requestId = requestId;
    }

    public long getId() {

        return id;
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

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
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
    private String rawData;
    private String encodingType;
    private long requestId;

    @Override
    public String toString() {
        return key + value;
    }
}
