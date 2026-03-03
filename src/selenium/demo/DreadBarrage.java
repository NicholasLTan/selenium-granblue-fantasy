package selenium.demo;

import java.time.Duration;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DreadBarrage {

	@Test
	public void dreadBarrage() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();


		driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div[2]/div[2]/div/div")).click();  //Dread Barrage banner
		driver.get("https://game.granbluefantasy.jp/#event/teamforce");
		System.out.println("Dread Barrage");
		//driver.findElement(By.cssSelector("[alt='banner_event_start_1']")).click();
		Thread.sleep(2000);

		// 1*
		//driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[1]/div[2]")).click();

		// 2*
		//driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[2]/div[2]")).click();
		//System.out.println("2*");

		// 3*
		//driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[3]/div[2]")).click();

		// 4*
		//driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[4]/div[2]")).click();
		//System.out.println("4*");

		// 5*
		driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[5]/div[2]")).click();
		System.out.println("5*");
		Thread.sleep(2000);

		int maxAttempts = 10; // Optional: To prevent infinite loops
		int attempts = 0;
		while (attempts < maxAttempts) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
			confirmTeam.confirmTeam(wait);
			autoBattle.autoBattle(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
			Results results = new Results();
			results.results(driver, wait, true);

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
		System.out.println("Script Complete");
	}
}
