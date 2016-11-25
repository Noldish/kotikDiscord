package kotik.simple.dao.objects;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Roman_Kuznetcov on 25.11.2016.
 */
@Entity
@Table(name="sounds",schema = "public")
public class Sound {
    @Id
    @NotEmpty
    @Column(name="name", unique=true, nullable=false)
    public String name;
    @NotEmpty
    @Column(name="url", nullable=false)
    public String url;

    public Sound() {
    }

    public Sound(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
