package selenium.demo;

import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmExo {

	@Test
	public void farmExo() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		driver.get("https://game.granbluefantasy.jp/#event/godslayer"); //Exo event URL

		System.out.println("Exo Cocytus");
		Thread.sleep(5000);

		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[3]/div[2]/div[2]/div/div[2]/img")).click();
		driver.findElement(By.className("img-boss-quest")).click();
		System.out.println("Crucible");
		Thread.sleep(1000);
		
		String itemID = "10584"; //Stirian Lapis for Cocytus		
		int maxItem = 10;
		int currItem = maxItem;
		String itemCSS = "figure[data-item-id='" + itemID + "']";
		IsElementPresent ePresent = new IsElementPresent();
		boolean	elementExists = ePresent.isElementPresent(driver, By.cssSelector(itemCSS));
		if (elementExists) {
			WebElement item = driver.findElement(By.cssSelector(itemCSS));
			String itemCountString = item.findElement(By.xpath("./figcaption")).getText();
			System.out.println(itemCountString);
			String currItemCountString = itemCountString.substring(0,itemCountString.indexOf("/"));
			System.out.println(currItemCountString);
			currItem = Integer.valueOf(currItemCountString);
			System.out.println("currItem = " + currItem);
		}
		
		int maxAttempts = 6; // To prevent infinite loops; 0 for infinite
		int attempts = 0;
		//wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
		if (attempts < maxAttempts || maxAttempts == 0) {
			//driver.findElement(By.cssSelector("[data-group='1']")).click(); //Solo
			//Thread.sleep(2000);
			//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[5]/div/div[3]/div[2]")).click(); //Play
			driver.findElement(By.className("btn-set-quest")).click(); //Play
			Thread.sleep(5000);          
			//driver.findElement(By.className("btn-usual-ok")).click(); //Maniac
			//Thread.sleep(2000);
		}		
		while (attempts < maxAttempts || maxAttempts == 0 ) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			ePresent = new IsElementPresent();				
			Results results = new Results();
			results.results(driver, longWait, true);

			/*
			 * boolean elementExists = ePresent.isElementPresent(driver,
			 * By.className("btn-usual-close")); if (elementExists) { Thread.sleep(1000);
			 * driver.findElement(By.className("btn-usual-close")).click();
			 * System.out.println("Mission Close"); Thread.sleep(2000); }
			 */
			attempts++;
			System.out.println(attempts);
		}
		
		
				
		
	}
}
