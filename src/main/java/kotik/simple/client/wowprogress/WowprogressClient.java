package kotik.simple.client.wowprogress;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kotik.simple.client.wowprogress.objects.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by Roman_Kuznetcov on 21.11.2016.
 */
@Service
public class WowprogressClient {


    private RestTemplate restTemplate;

    @Autowired
    public WowprogressClient(RestTemplate rest) {
        this.restTemplate = rest;
    }

    public List<Guild> getGuildRanking (){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Guild> ranking = new ArrayList<>();
        try {
            Resource resource = new UrlResource("http://www.wowprogress.com/export/ranks/eu_connected-"+ URLEncoder.encode("пиратская-бухта", "UTF-8")+"_tier19.json.gz");
            ranking = objectMapper.readValue(new GZIPInputStream(new java.io.BufferedInputStream(resource.getInputStream())),new TypeReference<List<Guild>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ranking;
    }


}
