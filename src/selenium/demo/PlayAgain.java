package selenium.demo;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlayAgain {
	public void playAgain(WebDriver driver, WebDriverWait longWait, String itemID) throws InterruptedException {
		ConfirmTeam confirmTeam = new ConfirmTeam();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Battle battle = new Battle();
		MaterialTracker materialTracker = new MaterialTracker();
		Results results = new Results();
		boolean nextLoop = true;
		while (nextLoop) {
			wait.until(ExpectedConditions.urlContains("supporter"));
			confirmTeam.confirmTeam(driver, wait);
			wait.until(ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#raid"));
			battle.battle(driver, longWait);
			nextLoop = materialTracker.materialTracker(driver, itemID);
			results.results(driver, wait, true);
			if ( driver.getCurrentUrl().equals("https://game.granbluefantasy.jp/#quest/extra") ) { return; }
		}
		results.results(driver, wait, false);
		return;
	}
	
	public void playAgain(WebDriver driver, WebDriverWait longWait, int maxAttempts) throws InterruptedException {
		int attempts = 0;
		ConfirmTeam confirmTeam = new ConfirmTeam();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Battle battle = new Battle();
		Results results = new Results();
		while (attempts < maxAttempts) {
			wait.until(ExpectedConditions.urlContains("supporter"));
			confirmTeam.confirmTeam(driver, wait);
			wait.until(ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#raid"));
			battle.battle(driver, longWait);
			if ( attempts + 1 == maxAttempts) {
				results.results(driver, wait, false);
			} else {
				results.results(driver, wait, true);
			}
			attempts++;
			System.out.println("Run " + attempts + "/" + maxAttempts + " completed");
		}
		return;
	}
}
