package kotik.simple.objects;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Romique on 03.11.2016.
 */
@Entity
@Table(name="channels",schema = "public")
public class Channel {

    @Id
    @NotEmpty
    @Column(name="id", unique=true, nullable=false)
    private String id;
    @NotEmpty
    @Column(name="name", nullable=false)
    private String name;
    @NotEmpty
    @Column(name="topic", nullable=false)
    private String topic;


    private final static String TABLE = "channels";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
