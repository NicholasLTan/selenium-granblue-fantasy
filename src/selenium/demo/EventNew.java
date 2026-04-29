package selenium.demo;

import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventNew {
	@Test
	public void eventNew() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		Battle battle = new Battle();
		PlayAgain playAgain = new PlayAgain();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(600));
		IsElementPresent ePresent = new IsElementPresent();
		driver.get("https://game.granbluefantasy.jp/#event/treasureraid171"); 
		System.out.println("Momvasion");
		//Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
		if (ePresent.isElementPresent(driver, By.cssSelector("div[class='pop-usual pop-daily-bonus pop-show']"))) {
			driver.findElement(By.className("btn-usual-close")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.stalenessOf(null));
		}		
		int currLoop = 2;
		int maxAttempts = 100; // Optional: To prevent infinite loops
		int raidMatNum = Integer.valueOf(driver.findElement(By.cssSelector("div[class='txt-possessed-item']")).getText());
		if (ePresent.isElementPresent(driver, By.cssSelector("span[class='txt-red']"))) { 
			currLoop = 1; 
			maxAttempts = Integer.valueOf(driver.findElement(By.cssSelector("span[class='txt-red']")).getText());
		}
		//System.out.println("Script calculates " + maxAttempts + " IMP runs");
		
		while (currLoop < 3) {
			int attempts = 0;
			if (currLoop == 2 ) { maxAttempts = raidMatNum / 5; }
			System.out.println("Loop " + currLoop + ": " + maxAttempts + " IMP runs");
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
			driver.findElement(By.cssSelector("div[class^='btn-event-raid group']")).click();
			System.out.println("Raid Battle");
			Thread.sleep(1000);
			if (ePresent.isElementPresent(driver, By.className("pop-select-part-raid"))) {
				System.out.println("Event boss selection");
				//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[9]/div/div[2]/div/div[2]")).click();
				driver.findElement(By.cssSelector("div[data-part='1']")).click();
				Thread.sleep(1000);
			}
			//driver.findElement(By.cssSelector("img[class='img-quest-thumb'][src*='93744']")).click();
			driver.findElement(By.cssSelector("div[class^='btn-quest-start ico-'][data-quest-id*='942941']")).click();
			System.out.println("Impossible");

			playAgain.playAgain(driver, wait, maxAttempts);
			/*
			 * if (attempts < maxAttempts) {
			 * driver.findElement(By.cssSelector("[data-group='3']")).click(); // Raid
			 * Thread.sleep(1000); driver.findElement(By.xpath(
			 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div[3]/div[2]/div[4]"
			 * )) .click(); // Hard Thread.sleep(2000); }
			 */
			/*while (attempts < maxAttempts) {
				
				
				confirmTeam.confirmTeam(wait);
				//autoBattle.autoBattle(driver, wait);
				battle.battle(driver,wait);
				Results results = new Results();
				if (attempts + 1 == maxAttempts) {
					results.results(driver, wait, false);
				} else {
					results.results(driver, wait, true);
				}

				boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close"));
				if (elementExists) {
					Thread.sleep(1000);
					driver.findElement(By.className("btn-usual-close")).click();
					System.out.println("Mission Close");
					Thread.sleep(2000);
				}
				attempts++;
				System.out.println(attempts);
			}*/
			currLoop++;
		}
		System.out.println("Script Complete");
	}

}
