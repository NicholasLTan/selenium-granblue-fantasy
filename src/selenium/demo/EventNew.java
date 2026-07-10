package selenium.demo;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventNew {
	@Test
	public void eventNew() throws InterruptedException {
		int logLevel = 0;
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login(), "WebDriver must not be null");
		PlayAgain playAgain = new PlayAgain();
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(600)));
		IsElementPresent ePresent = new IsElementPresent();
		Results results = new Results();
		driver.get("https://game.granbluefantasy.jp/#event/treasureraid174"); 		
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		if (ePresent.isElementPresent(driver, By.cssSelector("div[class='pop-usual pop-daily-bonus pop-show']"))) {
			WebElement close = driver.findElement(By.className("btn-usual-close"));
			close.click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.stalenessOf(close));
		}
		if (ePresent.isElementPresent(driver, By.className("img-hell-boss"))) {
			driver.findElement(By.className("img-hell-boss")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='pop-usual pop-start-hell pop-show']"))); //Expecting skip NM enabled
			driver.findElement(By.cssSelector("div[class='btn-usual-text hide-common-text']")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='prt-deck-select set-toggle']")));
			driver.findElement(By.cssSelector("div[class='btn-usual-ok se-quest-start']")).click();
			results.results(driver, wait, false);
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
		}
		int currLoop = 2;
		int maxAttempts = 100; // Optional: To prevent infinite loops
		int raidMatNum = Integer.valueOf(driver.findElement(By.cssSelector("div[class='txt-possessed-item']")).getText());
		if (ePresent.isElementPresent(driver, By.cssSelector("span[class='txt-red']"))) { 
			currLoop = 1; 
			maxAttempts = Integer.valueOf(driver.findElement(By.cssSelector("span[class='txt-red']")).getText());
		}
		
		while (currLoop <= 2) {
			if (currLoop == 2 ) { maxAttempts = raidMatNum / 5; }
			System.out.println("Loop " + currLoop + ": " + maxAttempts + " IMP runs");
			if (maxAttempts > 0) {
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
				driver.findElement(By.cssSelector("div[class^='btn-event-raid group']")).click();
				if (logLevel >= 1) { System.out.println("Raid Battle"); }
				Thread.sleep(1000);
				if (ePresent.isElementPresent(driver, By.className("pop-select-part-raid"))) {
					System.out.println("Event boss selection");
					driver.findElement(By.cssSelector("div[data-part='2']")).click();
					Thread.sleep(1000);
				}
				driver.findElement(By.cssSelector("div[class^='btn-quest-start ico-'][data-rank='101']")).click();
				if (logLevel >= 1) { System.out.println("Impossible"); }
				playAgain.playAgain(driver, wait, maxAttempts, false);
			}
			currLoop++;
		}
		System.out.println("Script Complete");
	}

}
