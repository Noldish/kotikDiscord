package kotik.simple.client.warcraftlogs;

import kotik.simple.client.warcraftlogs.objects.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Roman_Kuznetcov on 18.11.2016.
 */
@Service
public class WarcraftlogsClient {

    private RestTemplate restTemplate;
    private String keyAPI;

    @Autowired
    public WarcraftlogsClient(RestTemplate rest, Environment env) {
        this.restTemplate = rest;
        this.keyAPI = env.getProperty("warcraftlogs.key");
    }


    public Rank getSomeInfo(){
        return restTemplate.getForObject("https://www.warcraftlogs.com:443/v1/rankings/encounter/1864?metric=dps&difficulty=4&class=10&spec=2&region=EU&limit=10&api_key=5afd978c59adb5c59bcc47b68bea32a4",Rank.class);
    }

}
