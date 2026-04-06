package selenium.demo;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlayAgain {
	public void playAgain(WebDriver driver, WebDriverWait longWait, String itemID) throws InterruptedException {
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Battle battle = new Battle();
		MaterialTracker materialTracker = new MaterialTracker();
		Results results = new Results();
		boolean nextLoop = true;
		
		while (nextLoop) {
			confirmTeam.confirmTeam(wait);
			//autoBattle.autoBattle(driver, wait);
			battle.battle(driver, longWait);
			nextLoop = materialTracker.materialTracker(driver, itemID);
			results.results(driver, wait, true);
		}
		results.results(driver, wait, false);
	}
	
	public void playAgain(WebDriver driver, WebDriverWait longWait, int maxAttempts) throws InterruptedException {
		int attempts = 0;
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Battle battle = new Battle();
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			//autoBattle.autoBattle(driver, wait);
			battle.battle(driver, longWait);

			Results results = new Results();
			if ( attempts + 1 == maxAttempts) {
				results.results(driver, wait, false);
			} else {
				results.results(driver, wait, true);
			}
			attempts++;
			System.out.println(attempts);
		}
	}
}
