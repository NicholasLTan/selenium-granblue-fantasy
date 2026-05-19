package selenium.demo;

import java.time.Duration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Provisions {
	@Test
	public void provisions() throws InterruptedException{
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get("https://game.granbluefantasy.jp/#frontier");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-receive-all")));
		driver.findElement(By.className("btn-receive-all")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("prt-popup-header")));
		//wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Pick Up Loot"));
		driver.findElement(By.className("btn-usual-ok")).click();
	}
}
