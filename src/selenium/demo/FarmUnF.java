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
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[2]/div/img")).click();  //Unite and Fight banner
		driver.get("https://game.granbluefantasy.jp/#event/teamraid081");
		System.out.println("UnF");
		Thread.sleep(5000);
		
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[4]/div[4]/img")).click();  //Easy
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[4]/div[3]/img")).click();  //Nightmare
		Thread.sleep(1000);
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[19]/div/div[2]/div/div[4]/div[3]")).click();  //Eyeball		
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[20]/div/div[2]/div/div[4]/div/div/div[3]/div[1]")).click();  //NM100
		System.out.println("Blanche");
		Thread.sleep(5000);

		int maxAttempts;
		int attempts;
		
		maxAttempts = 50; // Optional: To prevent infinite loops
		attempts = 0;
		
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
				
		
	}
}
