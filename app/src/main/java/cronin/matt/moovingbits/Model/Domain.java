package cronin.matt.moovingbits.Model;

/**
 * Created by mcronin on 10/5/2015.
 */
public class Domain {

    public Domain(){
        this.id = 0;
        this.URL = "";
    }
    public Domain(long id,String URL) {
        this.id = id;
        this.URL = URL;
    }

    private long id;
    private String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return URL;
    }
}
