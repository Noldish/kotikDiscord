package kotik.simple.client.warcraftlogs;

import kotik.simple.client.warcraftlogs.objects.Rank;
import kotik.simple.client.warcraftlogs.objects.Shot;
import kotik.simple.configuration.RestClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by Roman_Kuznetcov on 18.11.2016.
 */
@ActiveProfiles("Warcraftlogs-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestClientConfig.class, loader = AnnotationConfigContextLoader.class)
public class WarcraftlogsClientTest {

    @Autowired
    private WarcraftlogsClient client;

    @Test
    public void getSomeInfo() throws Exception {

//        Rank rank = client.getSomeInfo();
//        System.out.println(rank);
    }

    @Test
    public void getShots() throws Exception {

//        List<Shot> shots = client.getShotsForPlayer("Нолд","ткач-смерти",null, "10");
//
//        final Comparator<Shot> comp = (p1, p2) -> Long.compare( p1.getRank(), p2.getRank());
//        //Shot shot = shots.stream().min(Comparator.comparing(Shot::getRank)).get();
//        Map<Long,List<Shot>> fights = shots.stream().collect(Collectors.groupingBy(Shot::getEncounter));
//        for (Map.Entry<Long,List<Shot>> entry:fights.entrySet()){
//            System.out.println(entry.getValue().stream().min(Comparator.comparing(Shot::getRank)).get());
//        }
    }


}