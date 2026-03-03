package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmRotBBadges {
	@Test
	public void farmRaidEvent() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		Results results = new Results();
		WebElement refresh;
		IsElementPresent ePresent = new IsElementPresent();
		/**UnF
			final String raidCss = "img[alt$='hell100']";
			final String raidStr = "hell100";
		 **/
		final String raidStr = "6063771_highlevel";
		final String raidCss = "img[alt$='" + raidStr + "']";
		final String eventUrl = "https://game.granbluefantasy.jp/#event/advent";

		int maxAttempts = 7;
		int attempts;
		String[] questIdList = {"711191","711041","711141","711091"}; 
		
		driver.get(eventUrl);		
		for (String questId : questIdList) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			attempts = 0;
			
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-select-multi'")));
			Thread.sleep(1000);
			driver.findElement(By.cssSelector("div[class='btn-select-multi']")).click();
			Thread.sleep(2000);
			String questString = "div[data-quest-id='" + questId + "']";
			driver.findElement(By.cssSelector(questString)).click();
			
			while (attempts < maxAttempts) {
				confirmTeam.confirmTeam(wait);
				autoBattle.autoBattle(driver, wait);
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
}
