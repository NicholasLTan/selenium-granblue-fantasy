package selenium.demo;

import java.time.Duration;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecordsTen {

	@Test
	public void recordsTen() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		Reload reload = new Reload();

		driver.get("https://game.granbluefantasy.jp/#event/terra");
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[2]/div/img")).click();  //Records of the Ten
		System.out.println("Records of the Ten");
		//driver.findElement(By.cssSelector("[alt='banner_event_start_1']")).click();
		Thread.sleep(5000);

		 //For farming steps
			/*
			 * driver.findElement(By.cssSelector("div[data-chapter-id='94032'")).click();
			 * System.out.println("Extreme");
			 */
		  driver.findElement(By.cssSelector("div[data-chapter-id='94033'")).click(); 
		  System.out.println("Extreme");
		  Thread.sleep(5000);
		 
		
		//For farming NM100
		/*
		 * driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[3]/div[1]/div[2]/div/div[1]/div/div/div[1]"
		 * )).click(); System.out.println("Nightmare"); Thread.sleep(1500);
		 * driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[6]/div/div[2]/div/div[2]/div[1]/div[1]"
		 * )).click(); System.out.println("NM100"); Thread.sleep(5000);
		 */

		int maxAttempts = 10; // Optional: To prevent infinite loops
		int attempts = 0;
		while (attempts < maxAttempts) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(600));
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			//reload.reload(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
			Results results = new Results();
			if (attempts + 1 == maxAttempts) {
				results.results(driver, wait, false);
			} else {
				results.results(driver, wait, true);
			}
			/*
			 * boolean elementExists = ePresent.isElementPresent(driver,
			 * By.className("btn-usual-close")); if (elementExists) { Thread.sleep(1000);
			 * driver.findElement(By.className("btn-usual-close")).click();
			 * System.out.println("Mission Close"); Thread.sleep(2000); }
			 */
			attempts++;
			System.out.println("Completed attempt " + attempts);
		}
	}



}
