package my.twister.chronicle;

import my.twister.entities.IShortProfile;
import my.twister.entities.IShortTweet;
import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.map.ChronicleMap;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * Created by kkulagin on 4/2/2016.
 */
@Service
public class CDSDelegate {

  private ChronicleDataService chronicleDataService = (ChronicleDataService.DefaultChronicleDataService) ChronicleDataService.getInstance();

  public ChronicleMap<CharSequence, LongValue> getName2IdMap() {
    return chronicleDataService.getName2IdMap();
  }

  public ChronicleMap<LongValue, IShortProfile> getId2ProfileMap() {
    return chronicleDataService.getId2ProfileMap();
  }

  public ChronicleMap<LongValue, LongValue> getId2TimeMap() {
    return chronicleDataService.getId2TimeMap();
  }

  public ChronicleMap<LongValue, IShortTweet> getTweetsDataMap(long tweetId) {
    return chronicleDataService.getTweetsDataMap(tweetId);
  }

  public void createName2IdMap(boolean forceCreate) {
    getHacked().createProfileName2IdMap(forceCreate);
  }

  public void createId2TimeMap(boolean forceCreate) {
    getHacked().createProfileId2TimeMap(forceCreate);
  }

  public void createTweetsMap(long id) {
    getHacked().createTweetsMap(id, true);
  }


  public void deleteTweetsMap(long id) {
    getHacked().removeTweetsMap(id);
  }

  @PreDestroy
  public void destroy() {
    chronicleDataService.close();
  }
  private ChronicleDataService.DefaultChronicleDataService getHacked() {
    return (ChronicleDataService.DefaultChronicleDataService) chronicleDataService;
  }
}
