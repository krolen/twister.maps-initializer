package my.twister.mapinitializer.service;

import my.twister.chronicle.CDSDelegate;
import my.twister.entities.IShortTweet;
import my.twister.utils.LogAware;
import my.twister.utils.Utils;
import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.map.ChronicleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NavigableMap;

/**
 * Created by kkulagin on 4/2/2016.
 */
@Service
public class ControlService implements LogAware {

  @Autowired
  private CDSDelegate cdsDelegate;

  @PostConstruct
  public void init() {
    createMissingTwitterDataMaps(LocalDateTime.now().minusHours(1));
  }

  @Scheduled(cron = "0 15/30 * * * *")
  public void execute() {
    createMissingTwitterDataMaps(LocalDateTime.now());
    deleteOldMaps(LocalDateTime.now().minusHours(4));
  }

  // makes sure twitter data map is present for specified time rounded to an hour
  private void createMissingTwitterDataMaps(LocalDateTime time) {
    LocalDateTime hourStart = time.truncatedTo(ChronoUnit.HOURS);
    NavigableMap<Long, ChronicleMap<LongValue, IShortTweet>> dataMaps = cdsDelegate.getTweetsDataMaps();
    long hourStartInMillis = Utils.toMillis(hourStart);
    createMap(dataMaps, hourStartInMillis);
    long nextHourMillis = hourStartInMillis + Duration.ofHours(1).toMillis();
    createMap(dataMaps, nextHourMillis);
    long nextNextHourMillis = hourStartInMillis + Duration.ofHours(2).toMillis();
    createMap(dataMaps, nextNextHourMillis);
  }

  private void createMap(NavigableMap<Long, ChronicleMap<LongValue, IShortTweet>> dataMaps, long hourStartInMillis) {
    if(!dataMaps.containsKey(hourStartInMillis)) {
      cdsDelegate.createTweetsMap(hourStartInMillis);
    }
  }

  private void deleteOldMaps(LocalDateTime time) {
    long timeInMillis = Utils.toMillis(time);
    NavigableMap<Long, ChronicleMap<LongValue, IShortTweet>> dataMaps = cdsDelegate.getTweetsDataMaps();
    dataMaps.navigableKeySet().stream().filter((l) -> l < timeInMillis).forEach((l) -> {
      try {
        cdsDelegate.deleteTweetsMap(l);
      } catch (Exception e) {
        log().error("Error deleting map: " + l, e);
      }
    });
  }
}
