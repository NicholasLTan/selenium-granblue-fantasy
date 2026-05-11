package selenium.demo;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
//@SelectClasses({JourneyDropsActivate.class, FarmRaids.class})
//@SelectClasses({JourneyDropsActivate.class, FarmVeritas.class, FarmRaids.class})
@SelectClasses({JourneyDropsActivate.class, ProQuests.class, FarmVeritas.class, FarmRaids.class})
public class DailySuite {

}
//#wrapper > div.contents > div.cnt-mypage > div.cnt-layout-v2 > div.prt-user-scene > div.prt-vyrnsampo.display-off-target.is-vyrnsampo-type3
//<div class="btn-vyrnsampo type-3 frame-0" data-href="vyrnsampo"> </div>