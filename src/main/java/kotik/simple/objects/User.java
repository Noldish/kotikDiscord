package kotik.simple.objects;

/**
 * Created by Romique on 31.10.2016.
 */
public class User {
    private String id;
    private String name;
    private String displayName;
    private String dcp = "0";
    private final static String TABLE = "users";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDcp() {
        return dcp;
    }

    public void setDcp(String dcp) {
        this.dcp = dcp;
    }

    public void setDcp(Integer dcp) {
        this.dcp = dcp.toString();
    }

    public void addDcp(Integer dcp){
        Integer current;
        if (this.dcp != null) {
            current = Integer.parseInt(this.dcp);
        } else {
            current = 0;
        }
        current = current + dcp;
        this.dcp = current.toString();
    }
    /*
    public Integer getDcp() {
        return this.dcp.isEmpty()?0:Integer.parseInt(this.dcp);
    }
    */
}
