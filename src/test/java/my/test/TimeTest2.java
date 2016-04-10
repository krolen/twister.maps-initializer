package my.test;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by kkulagin on 4/9/2016.
 */
public class TimeTest2 {

  @Test
  public void timeTest() {

    int hour = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.HOURS).getHour();
    System.out.println("hour = " + hour);

    long l1 = LocalDateTime.now().minusMinutes(55).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    System.out.println("l1 = " + l1);

    long l = Instant.now().minus(Duration.ofMinutes(55).toMillis(), ChronoUnit.MINUTES).toEpochMilli();
    System.out.println(l);
  }
}
