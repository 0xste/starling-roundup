package karate;

import com.intuit.karate.junit5.Karate;
import com.stefanomantini.starlingroundup.StarlingRoundupApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StarlingRoundupApplication.class)
class TestRunner {

  private static final String featureProbe = "probe";
  private static final String featureRoundup = "roundup";

  @Karate.Test
  Karate testProbes() {
    return Karate.run(featureProbe).relativeTo(getClass());
  }

  @Karate.Test
  Karate testGetApi() {
    return Karate.run(featureRoundup).relativeTo(getClass());
  }
}
