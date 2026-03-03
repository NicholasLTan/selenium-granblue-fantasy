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
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[1]/div/img")).click();  //Exo Sagi banner
		//driver.findElement(By.xpath("//div[class='btn-global-banner'][data-href$='godslayer']")).click();

		System.out.println("Exo Vohu");
		Thread.sleep(5000);

		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[3]/div[2]/div[2]/div/div[2]/img")).click();  
		System.out.println("Crucible");
		Thread.sleep(1000);
		
		int maxAttempts;
		int attempts;
		
		
		maxAttempts = 21; // Optional: To prevent infinite loops
		attempts = 0;
		//wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
		if (attempts < maxAttempts) {
			//driver.findElement(By.cssSelector("[data-group='1']")).click(); //Solo
			//Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[5]/div/div[3]/div[2]")).click(); //Play
			Thread.sleep(5000);          
			//driver.findElement(By.className("btn-usual-ok")).click(); //Maniac
			//Thread.sleep(2000);
		}		
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
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
