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
		driver.get("https://game.granbluefantasy.jp/#event/treasureraid172"); 
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
		if (ePresent.isElementPresent(driver, By.cssSelector("div[class='pop-usual pop-daily-bonus pop-show']"))) {
			WebElement close = driver.findElement(By.className("btn-usual-close"));
			close.click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.stalenessOf(close));
		}		
		int currLoop = 2;
		int maxAttempts = 100; // Optional: To prevent infinite loops
		int raidMatNum = Integer.valueOf(driver.findElement(By.cssSelector("div[class='txt-possessed-item']")).getText());
		if (ePresent.isElementPresent(driver, By.cssSelector("span[class='txt-red']"))) { 
			currLoop = 1; 
			maxAttempts = Integer.valueOf(driver.findElement(By.cssSelector("span[class='txt-red']")).getText());
		}
		
		while (currLoop < 3) {
			if (currLoop == 2 ) { maxAttempts = raidMatNum / 5; }
			System.out.println("Loop " + currLoop + ": " + maxAttempts + " IMP runs");
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-raid group']")));
			driver.findElement(By.cssSelector("div[class^='btn-event-raid group']")).click();
			if (logLevel >= 1) { System.out.println("Raid Battle"); }
			Thread.sleep(1000);
			if (ePresent.isElementPresent(driver, By.className("pop-select-part-raid"))) {
				System.out.println("Event boss selection");
				driver.findElement(By.cssSelector("div[data-part='1']")).click();
				Thread.sleep(1000);
			}
			driver.findElement(By.cssSelector("div[class^='btn-quest-start ico-'][data-quest-id*='943341']")).click();
			if (logLevel >= 1) { System.out.println("Impossible"); }
			playAgain.playAgain(driver, wait, maxAttempts, false);
			currLoop++;
		}
		System.out.println("Script Complete");
	}

}
