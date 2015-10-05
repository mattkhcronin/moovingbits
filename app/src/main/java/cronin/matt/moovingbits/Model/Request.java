package cronin.matt.moovingbits.Model;

/**
 * Created by mattcronin on 10/1/15.
 */
public class Request {

    private int id;
    private String route;
    private int groupId;
    private String type;

    public Request(int id, String type, String route, int groupId) {
        this.id = id;
        this.type = type;
        this.route = route;
        this.groupId = groupId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return route + " " + type;
    }
}
