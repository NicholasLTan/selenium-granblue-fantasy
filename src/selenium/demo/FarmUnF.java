package selenium.demo;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmUnF {
	@Test
	public void farmUnF() throws InterruptedException {
		int maxAttempts = 500; // Optional: To prevent infinite loops;
		int target = 3; // Selects quest. Choose from 1-9 below
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login());
		PlayAgain playAgain = new PlayAgain();
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(30)));
		WebDriverWait longWait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(1000)));
		WebElement banner = null; 
		WebElement quest = null;
		boolean oneTurn = false;

		driver.get("https://game.granbluefantasy.jp/#event/teamraid083");
		System.out.println("UnF");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-ranking-profile")));
		
		if (target >= 7) { //United Battle
			boolean isPresent = !driver.findElements(By.className("btn-ex-raid8")).isEmpty();
			if (isPresent) { banner = driver.findElement(By.className("btn-ex-raid8")); }
			else {
				isPresent = !driver.findElements(By.className("btn-ex-raid7")).isEmpty();
				if (isPresent) { banner = driver.findElement(By.className("btn-ex-raid7")); }
				else { 
					isPresent = !driver.findElements(By.className("btn-ex-raid6")).isEmpty();
					if (isPresent) { banner = driver.findElement(By.className("btn-ex-raid6")); }
					else { System.out.println("ERROR: United Battle class not found"); }
				}
			}
		} else if (target <= 6 && target >= 4) { //Nightmare
				boolean isPresent = !driver.findElements(By.className("btn-ex-raid5")).isEmpty();
				if (isPresent) { banner = driver.findElement(By.className("btn-ex-raid5")); }
				else {
					isPresent = !driver.findElements(By.className("btn-ex-raid4")).isEmpty();
					if (isPresent) { banner = driver.findElement(By.className("btn-ex-raid4")); }
					else { System.out.println("ERROR: Nightmare class not found"); }
				}
		} else if (target == 3) {banner = driver.findElement(By.className("btn-ex-raid2")); } //BaitFarm;
		else if (target <= 2) {banner = driver.findElement(By.className("btn-raid-select")); } //Solo;		
		
		System.out.println(Objects.requireNonNull(banner).findElement(By.className("img-btn-raid")).getAttribute("alt"));
		banner.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='pop-raid']")));
		
		if (target == 9) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94382']")); } //NM250
		else if (target == 8) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94381']")); } //NM200
		else if (target == 7) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94380']")); } //NM150
		else if (target == 6) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94379']")); } //NM100
		else if (target == 5) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94378']")); } //NM95
		else if (target == 4) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94377']")); } //NM90
		else if (target == 3) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94375']")); } //Extreme+
		else if (target == 2) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94373']")); //Very Hard
			maxAttempts = Math.min(maxAttempts, 15);}
		else if (target == 1) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94372']")); //Hard
			maxAttempts = Math.min(maxAttempts, 15);
		}
		System.out.println(Objects.requireNonNull(quest).getAttribute("data-chapter-name"));
		quest.click();
		playAgain.playAgain(driver, longWait, maxAttempts, oneTurn);
		
		
		/*
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
			Results results = new Results();
			if ( attempts + 1 == maxAttempts) {
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
		}
		*/		
		
	}
}
