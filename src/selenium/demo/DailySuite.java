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
	EventNew.class,
	FarmRaids.class,
	PendingBattles.class,
	FarmVeritas.class
	})
public class DailySuite {
}

//handle event mission in pro quest 
