package my.twister.chronicle;

import my.twister.entities.IShortTweet;
import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.map.ChronicleMap;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.NavigableMap;

/**
 * Created by kkulagin on 4/2/2016.
 */
@Service
public class CDSController {

  private ChronicleDataService chronicleDataService;

  @PostConstruct
  public void init() {
    chronicleDataService = ChronicleDataService.getInstance();
    chronicleDataService.connectTweetsMaps(-1);
  }

  public void deleteTweetsMap(long id) {
    chronicleDataService.removeTweetsMap(id);
  }

  public NavigableMap<Long, ChronicleMap<LongValue, IShortTweet>> getTweetsDataMaps() {
    return chronicleDataService.getTweetsDataMaps();
  }

  @PreDestroy
  public void destroy() {
    chronicleDataService.close();
  }

  public void createId2TimeMap(boolean force) {
    chronicleDataService.initId2TimeMap(force);
  }

  public void createName2IdMap(boolean force) {
    chronicleDataService.initName2IdMap(force);
  }

  public void createTweetsMap(Long time) {
    chronicleDataService.createOrConnectTweetsMap(time);
  }
}
