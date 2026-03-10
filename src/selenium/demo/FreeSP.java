package selenium.demo;
import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FreeSP {
	@Test
	public void freeSP() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		SemiAuto semiAuto = new SemiAuto();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
		
		//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[1]/div[1]/div[8]/div[1]")).click();  //Quest
		//driver.findElement(By.className("btn-link-quest")).click();  //Quest
		//System.out.println("Quest");
		//Thread.sleep(5000);
		//driver.findElement(By.cssSelector("div[class^='btn-extra-quest']")).click();  //Special
		driver.get("https://game.granbluefantasy.jp/#quest/extra");
		System.out.println("Event SP Quests");
		Thread.sleep(5000);
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[4]/div[1]/div[2]/div/div[1]/div/div[4]")).click();  //FreeSP
		System.out.println("FreeSP");
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[6]/div/div[2]/div/div[1]/div/div[4]")).click();  //Play
		System.out.println("Play");
		Thread.sleep(5000);
		int maxAttempts = 200; // Optional: To prevent infinite loops
		int attempts = 0;
		while (attempts < maxAttempts) {
			confirmTeam.confirmTeam(wait);
			semiAuto.semiAuto(driver, wait);
			IsElementPresent ePresent = new IsElementPresent();				
			Results results = new Results();
			if ( attempts + 1 == maxAttempts) {
				results.results(driver, wait, false);
			} else {
				results.results(driver, wait, true);
			}
			attempts++;
			System.out.println(attempts);
		}
	}
}
