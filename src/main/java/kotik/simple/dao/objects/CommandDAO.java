package kotik.simple.dao.objects;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 16.11.2016.
 */
@Entity
@Table(name="commands",schema = "public")
public class CommandDAO {

    @NotEmpty
    @Column(name="class", nullable=false)
    public String className;
    @Id
    @NotEmpty
    @Column(name="name", unique=true, nullable=false)
    public String name;
    @NotEmpty
    @Column(name="description", nullable=false)
    public String description;
    @Column(name="permissions", nullable=false)
    public String permissions;

    @Override
    public String toString() {
        return "CommandDAO{" +
                "className='" + className + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }

    public Map<String,String> getAsMapForDBData(){
        Map<String,String> result = new HashMap<>();
        result.put("class",className);
        result.put("name",name);
        result.put("description",className);
        if (!"".equals(permissions)) {
            result.put("permissions", className);
        }
        return result;
    }
}
