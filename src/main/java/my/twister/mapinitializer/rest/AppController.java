package my.twister.mapinitializer.rest;

import my.twister.chronicle.CDSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kkulagin on 4/2/2016.
 */
@RestController
public class AppController {

  @Autowired
  private CDSController cdsController;

  @RequestMapping(value = "/profile/id2Profile", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createId2Profile(@RequestParam(value = "force", required = false, defaultValue = "false") boolean force) {
    cdsController.createId2Profile(force);
  }

  @RequestMapping(value = "/profile/id2TimeMap", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createId2Time(@RequestParam(value = "force", required = false, defaultValue = "false") boolean force) {
    cdsController.createId2TimeMap(force);
  }

  @RequestMapping(value = "/profile/name2Id", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createName2Id(@RequestParam(value = "force", required = false, defaultValue = "false") boolean force) {
    cdsController.createName2IdMap(force);
  }

  @RequestMapping(value = "/tweets/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTweets(@PathVariable(value = "id") Long id) {
    cdsController.deleteTweetsMap(id);
  }

  @RequestMapping(value = "/tweets/{id}", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createTweets(@PathVariable(value = "id") Long id) {
    cdsController.createTweetsMap(id);
  }


}
