package kotik.simple.client.wowprogress.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Roman_Kuznetcov on 21.11.2016.
 */
public final class Guild {
    public final long score;
    public final long world_rank;
    public final long area_rank;
    public final long realm_rank;
    public final String name;
    public final String url;

    @JsonCreator
    public Guild(@JsonProperty("score") long score, @JsonProperty("world_rank") long world_rank, @JsonProperty("area_rank") long area_rank, @JsonProperty("realm_rank") long realm_rank, @JsonProperty("name") String name, @JsonProperty("url") String url){
        this.score = score;
        this.world_rank = world_rank;
        this.area_rank = area_rank;
        this.realm_rank = realm_rank;
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return  name +
                " score=" + score +
                ", world_rank=" + world_rank +
                ", area_rank=" + area_rank +
                ", realm_rank=" + realm_rank
                ;
    }
}
