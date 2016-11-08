package kotik.simple.objects;

/**
 * Created by Romique on 03.11.2016.
 */
public class Channel {
    private final static String TABLE = "channels";
    private String id;
    private String name;
    private String topic;

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
