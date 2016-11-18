package kotik.simple.client.warcraftlogs;

import kotik.simple.client.warcraftlogs.objects.Rank;
import kotik.simple.configuration.RestClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

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

        Rank rank = client.getSomeInfo();

        System.out.println(rank);
    }

}