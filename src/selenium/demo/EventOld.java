package selenium.demo;

import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventOld {

	@Test
	public void eventOld() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		SemiAuto semiAuto = new SemiAuto();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[3]/div/img")).click();  //Imagination Overdrive banner
		driver.get("https://game.granbluefantasy.jp/#quest/extra/event/11073"); 
		System.out.println("Unbending Chivalry");  
		System.out.println("Event SP Quests");
		Thread.sleep(1000);
		
		int maxAttempts;
		int attempts;
				
		maxAttempts = 0; // Optional: To prevent infinite loops
		attempts = 0;		
		if (attempts < maxAttempts) {
			wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
			Thread.sleep(5000);
			driver.findElement(By.cssSelector("[data-group='1']")).click(); //Solo
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div/div[3]/div[4]")).click(); //Maniac
			Thread.sleep(2000);          
			driver.findElement(By.className("btn-usual-ok")).click(); //Maniac
			Thread.sleep(2000);
		}		
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
			Results results = new Results();
			results.results(driver, wait, true);
			attempts++;
			System.out.println(attempts);
		}
		
		maxAttempts = 25; // Optional: To prevent infinite loops
		attempts = 0;
		if (attempts < maxAttempts) {
			
			wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
			Thread.sleep(5000);
			driver.findElement(By.cssSelector("[data-group='1']")).click(); //Solo
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div/div[2]/div[4]")).click(); //Maniac+
			Thread.sleep(2000);          
			//driver.findElement(By.className("btn-usual-ok")).click(); //Extreme
			//Thread.sleep(2000);
		}		
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
			Results results = new Results();
			results.results(driver, wait, true);
			attempts++;
			System.out.println(attempts);
		}
		
		maxAttempts = 0; // Optional: To prevent infinite loops
		attempts = 0;
		if (attempts < maxAttempts) {
			wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
			Thread.sleep(5000);
			driver.findElement(By.cssSelector("[data-group='3']")).click(); //Raid
			Thread.sleep(1000);
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div[3]/div[2]/div[4]")).click(); //Extreme+
			Thread.sleep(2000);
		} 
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			//autoBattle.autoBattle(driver, wait);
			semiAuto.semiAuto(driver, wait);
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
		System.out.println("Script complete");		
		
	}
}
