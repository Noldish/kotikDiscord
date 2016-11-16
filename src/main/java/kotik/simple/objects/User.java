package kotik.simple.objects;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Romique on 31.10.2016.
 */

@Entity
@Table(name="users",schema = "public")
public class User {

    @Id
    @NotEmpty
    @Column(name="id", unique=true, nullable=false)
    private String id;

    @NotEmpty
    @Column(name="name", nullable=false)
    private String name;

    @NotEmpty
    @Column(name="displayname", nullable=false)
    private String displayName;

    @NotEmpty
    @Column(name="dcp", nullable=false)
    private String dcp = "0";



    private final static String TABLE = "users";

    public User(String id, String name, String displayName, String dcp) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.dcp = dcp;
    }

    public User() {
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", dcp='" + dcp + '\'' +
                '}';
    }


    /*
    public Integer getDcp() {
        return this.dcp.isEmpty()?0:Integer.parseInt(this.dcp);
    }
    */
}
