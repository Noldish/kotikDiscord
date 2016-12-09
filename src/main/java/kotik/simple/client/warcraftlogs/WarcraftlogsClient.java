package kotik.simple.client.warcraftlogs;

import kotik.simple.client.warcraftlogs.objects.Rank;
import kotik.simple.client.warcraftlogs.objects.Shot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Roman_Kuznetcov on 18.11.2016.
 */
@Service
public class WarcraftlogsClient {

    private RestTemplate restTemplate;
    private static final String keyAPI="5afd978c59adb5c59bcc47b68bea32a4";
    private String defaultMetric = "dps";

    @Autowired
    public WarcraftlogsClient(RestTemplate rest, Environment env) {
        this.restTemplate = rest;
    }


    public Rank getSomeInfo(){
        return restTemplate.getForObject("https://www.warcraftlogs.com:443/v1/rankings/encounter/1864?metric=dps&difficulty=4&class=10&spec=2&region=EU&limit=10&api_key=5afd978c59adb5c59bcc47b68bea32a4",Rank.class);
    }

    public List<Shot> getShotsForPlayer(String characterName, String serverName , String metric, String zone,Long difficulty){

        String url = "https://www.warcraftlogs.com:443/v1/rankings/character/{charname}/{servername}/EU";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("api_key", keyAPI)
                .queryParam("zone", zone)
                .queryParam("encounter", null)
                .queryParam("metric", (metric==null)?defaultMetric:metric);;
        UriComponents uriComponents = builder.buildAndExpand(characterName,serverName);
        Shot[] shots = restTemplate.getForObject(uriComponents.toString(),Shot[].class);
        List<Shot> list = Arrays.asList(shots);
        list = list.stream().filter((s)->(s.getDifficulty().equals(difficulty))).collect(Collectors.toList());
        Map<Long,List<Shot>> fights = list.stream().collect(Collectors.groupingBy(Shot::getEncounter));

        List<Shot> result = new ArrayList<>();
        for (Map.Entry<Long,List<Shot>> entry:fights.entrySet()){
            result.add(entry.getValue().stream().min(Comparator.comparing(Shot::getRank)).get());
        }
        return result;
    }
}
