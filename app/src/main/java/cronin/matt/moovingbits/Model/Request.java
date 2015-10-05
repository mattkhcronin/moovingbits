package cronin.matt.moovingbits.Model;

/**
 * Created by mattcronin on 10/1/15.
 */
public class Request {

    private long id;
    private String route;
    private long domainId;
    private String type;

    public Request(){
        this.id = 0;
        this.type = "GET";
        this.route = "";
        this.domainId = 0;
    }

    public Request(long id, String type, String route, long domainId) {
        this.id = id;
        this.type = type;
        this.route = route;
        this.domainId = domainId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    @Override
    public String toString() {
        return route + " " + type;
    }
}
