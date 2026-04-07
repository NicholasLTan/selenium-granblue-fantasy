package selenium.demo;

import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmUnF {
	@Test
	public void farmUnF() throws InterruptedException {
		int maxAttempts = 30; // Optional: To prevent infinite loops;
		int target = 3; // Selects quest. Choose from 1-5 below
		Login login = new Login();
		WebDriver driver = login.login();
		PlayAgain playAgain = new PlayAgain();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		WebElement banner = null; //Nightmare
		WebElement quest = null; //NM95

		driver.get("https://game.granbluefantasy.jp/#event/teamraid082");
		System.out.println("UnF");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-ranking-profile")));
		
		if (target == 5) {banner = driver.findElement(By.className("btn-ex-raid4")); } //Nightmare;
		else if (target == 4) {banner = driver.findElement(By.className("btn-ex-raid4")); } //Nightmare;
		else if (target == 3) {banner = driver.findElement(By.className("btn-ex-raid2")); } //BaitFarm;
		else if (target == 2) {banner = driver.findElement(By.className("btn-raid-select")); } //Solo;
		else if (target == 1) {banner = driver.findElement(By.className("btn-raid-select")); } //Solo;
		
		
		System.out.println(banner.findElement(By.className("img-btn-raid")).getAttribute("alt"));
		banner.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='pop-raid']")));
		
		if (target == 5) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94268']")); } //NM95
		else if (target == 4) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94267']")); } //NM90
		else if (target == 3) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94265']")); } //Extreme+
		else if (target == 2) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94263']")); //Very Hard
			maxAttempts = Math.min(maxAttempts, 15);}
		else if (target == 1) {quest = driver.findElement(By.cssSelector("div[data-chapter-id='94262']")); //Hard
			maxAttempts = Math.min(maxAttempts, 15);
		}
		System.out.println(quest.getAttribute("data-chapter-name"));
		quest.click();
		playAgain.playAgain(driver, longWait, maxAttempts);
		
		
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
