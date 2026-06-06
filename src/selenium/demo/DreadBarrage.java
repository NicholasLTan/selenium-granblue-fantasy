package selenium.demo;

import java.time.Duration;
import java.util.Objects;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DreadBarrage {
	@Test
	public void dreadBarrage() throws InterruptedException {
		int quest = 5;
		int maxAttempts = 10; // Optional: To prevent infinite loops
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();

		driver.get("https://game.granbluefantasy.jp/#event/teamforce");
		System.out.println("Dread Barrage");
		Thread.sleep(2000);

		if ( quest == 5 ) {
			driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[5]/div[2]")).click();
			System.out.println("5*");
		} else if ( quest == 4 ) {
			driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[4]/div[2]")).click();
			System.out.println("4*");
		} else if ( quest == 3 ) {
			driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[3]/div[2]")).click();
			System.out.println("3*");
		} else if ( quest == 2 ) {
			driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[2]/div[2]")).click();
			System.out.println("2*");
		} else if ( quest == 1 ) {
			driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[1]/div[2]")).click();
			System.out.println("1*");
		}
		Thread.sleep(2000);
	
		int attempts = 0;
		while (attempts < maxAttempts) {
			WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(1000)));
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
