package selenium.demo;

import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventNew {
	@Test
	public void eventNew() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(600));
		IsElementPresent ePresent = new IsElementPresent();
		driver.get("https://game.granbluefantasy.jp/#event/treasureraid169"); 
		System.out.println("PS,the Astrals...");
		Thread.sleep(5000);
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[5]/div[1]/div[1]/div[2]/div/div[1]/div[5]/div[1]/div[2]/div/img")).click();
		driver.findElement(By.cssSelector("div[class='btn-event-raid group']")).click();
		System.out.println("Raid Battle");
		Thread.sleep(1000);
		if (ePresent.isElementPresent(driver, By.className("pop-select-part-raid"))) {
			System.out.println("Event boss selection");
			//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[9]/div/div[2]/div/div[2]")).click();
			driver.findElement(By.cssSelector("div[data-part='1']")).click();
			Thread.sleep(1000);
		}
		//driver.findElement(By.cssSelector("img[class='img-quest-thumb'][src*='93744']")).click();
		driver.findElement(By.cssSelector("div[class^='btn-quest-start ico-'][data-quest-id*='939821']")).click();
		System.out.println("Impossible");

		int maxAttempts = 5; // Optional: To prevent infinite loops
		int attempts = 0;
		/*
		 * if (attempts < maxAttempts) {
		 * driver.findElement(By.cssSelector("[data-group='3']")).click(); // Raid
		 * Thread.sleep(1000); driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div[3]/div[2]/div[4]"
		 * )) .click(); // Hard Thread.sleep(2000); }
		 */
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);			
			Results results = new Results();
			if (attempts + 1 == maxAttempts) {
				results.results(driver, wait, false);
			} else {
				results.results(driver, wait, true);
			}

			boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close"));
			if (elementExists) {
				Thread.sleep(1000);
				driver.findElement(By.className("bt)n-usual-close")).click();
				System.out.println("Mission Close");
				Thread.sleep(2000);
			}
			attempts++;
			System.out.println(attempts);
		}
		System.out.println("Script Complete");
	}

}
