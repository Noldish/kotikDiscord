package kotik.simple.client.wowprogress;

import kotik.simple.client.wowprogress.objects.Guild;
import kotik.simple.configuration.RestClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Roman_Kuznetcov on 21.11.2016.
 */
@ActiveProfiles("Wowprogress-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestClientConfig.class, loader = AnnotationConfigContextLoader.class)
public class WowprogressClientTest {

    @Autowired
    private WowprogressClient client;

    @Test
    public void getGuildRanking() throws Exception {
        List<Guild> guilds = client.getGuildRanking();
        System.out.println(guilds);
    }

}