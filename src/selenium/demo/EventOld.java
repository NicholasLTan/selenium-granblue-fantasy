package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventOld {
	@Test
	public void eventOld() throws InterruptedException {
		boolean maniac = true;
		boolean raid = true;
		boolean extreme = true;
		int maxAttempts = 10; //For farming Extreme
		
		Login login = new Login();
		WebDriver driver = login.login();
		PlayAgain playAgain = new PlayAgain();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(120));
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[3]/div/img")).click();  //Imagination Overdrive banner
		driver.get("https://game.granbluefantasy.jp/#quest/extra/event/11074"); 
		System.out.println("ZodiaCamp 2nd");  
		System.out.println("Event SP Quests");		
		int attempts;

		//Maniac Loop
		if ( maniac ) {
			for (int i=6; i<=7; i++) {
				maxAttempts = 2; //Maniac 2x daily limit
				attempts = 0;
				if (attempts < maxAttempts) {
					By cssManiac = By.cssSelector("div[data-difficulty='" + i + "']");				
					wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='1']")));
					driver.findElement(By.cssSelector("div[data-group='1']")).click(); //Solo
					wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
					List<WebElement> elementExists = driver.findElements(cssManiac);
					if (elementExists.size() > 0) {
						System.out.println("EventOld Maniac " + i + " launching.");
						driver.findElement(cssManiac).click(); //Maniac+
						wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-ok"))); //Daily limit OK
						maxAttempts = Integer.valueOf(driver.findElement(By.className("txt-popup-body")).getText().replaceAll("[^0-9]", ""));
						driver.findElement(By.className("btn-usual-ok")).click(); //Maniac
					} else {
						System.out.println("EventOld Maniac " + i + " not found.");
						driver.findElement(By.className("btn-usual-close")).click();
						break;
					}

				}
				playAgain.playAgain(driver, longWait, maxAttempts);
			}
		}
		
		//Raid EX farming
		if ( raid ) {
			maxAttempts = 5; // Optional: To prevent infinite loops
			attempts = 0;
			if (attempts < maxAttempts) {
				wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='3']")));
				driver.findElement(By.cssSelector("div[data-group='3']")).click(); //Raid
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-difficulty='4']")));
				driver.findElement(By.cssSelector("div[data-difficulty='4']")).click(); //Extreme+
			}
			playAgain.playAgain(driver, longWait, maxAttempts);							
		}
		
		//Extreme Farming
		if ( extreme ) {
			attempts = 0;		
			if (attempts < maxAttempts) {
				wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='1']")));
				driver.findElement(By.cssSelector("div[data-group='1']")).click(); //Solo
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-difficulty='4']")));
				driver.findElement(By.cssSelector("div[data-difficulty='4']")).click(); //Extreme
			}		
			playAgain.playAgain(driver, longWait, maxAttempts);
		}
		System.out.println("Script complete");
	}
}
