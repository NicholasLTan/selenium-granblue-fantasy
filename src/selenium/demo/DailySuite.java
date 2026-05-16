package selenium.demo;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	CasinoChips.class, 
	DrawCrate.class, 
	Provisions.class, 
	JourneyDropsActivate.class, 
	ProQuests.class, 
	EventOld.class,
	FarmRaids.class,
	FarmVeritas.class, 	
	FreeSP.class
	})
public class DailySuite {

}
//#wrapper > div.contents > div.cnt-mypage > div.cnt-layout-v2 > div.prt-user-scene > div.prt-vyrnsampo.display-off-target.is-vyrnsampo-type3
//<div class="btn-vyrnsampo type-3 frame-0" data-href="vyrnsampo"> </div>