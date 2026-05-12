package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CasinoChips {
	@Test
	public void casinoChips() throws InterruptedException{
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get("https://game.granbluefantasy.jp/#casino");
		
		List<WebElement> chips = driver.findElements(By.cssSelector(".btn-shine.present"));
		System.out.println(chips.size() + " chip shines found");
		if ( !chips.isEmpty() ) {
			for (int i = 0; i < chips.size(); i++) {
				wait.until(ExpectedConditions.elementToBeClickable(chips.get(i)));
				chips.get(i).click();
				wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Chips"));
				System.out.println(driver.findElement(By.className("txt-number")).getText() + " picked up");
				driver.findElement(By.className("btn-usual-ok")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("prt-popup-header")));
				Thread.sleep(500);
			}
		}
		System.out.println("CasinoChips Completed");

	}
}
