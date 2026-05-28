package selenium.demo;

import java.time.Duration;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecordsTen {

	@Test
	public void recordsTen() throws InterruptedException {
		int maxAttempts = 1000; // Optional: To prevent infinite loops
		By questBy = By.cssSelector("div[data-chapter-id='94413'");
				
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		PlayAgain playAgain = new PlayAgain();
		
		driver.get("https://game.granbluefantasy.jp/#event/terra");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("prt-head-current")));
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());

		if (!driver.findElements(By.className("prt-popup-header")).isEmpty()) {
			WebElement close = driver.findElement(By.className("btn-usual-close"));
			close.click();
			wait.until(ExpectedConditions.stalenessOf(close));
		}
			
		wait.until(ExpectedConditions.elementToBeClickable(questBy));
		System.out.println(driver.findElement(questBy).getAttribute("data-quest-name"));
		driver.findElement(questBy).click(); 
		//For farming NM100
		/*
		 * driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[3]/div[1]/div[2]/div/div[1]/div/div/div[1]"
		 * )).click(); System.out.println("Nightmare"); Thread.sleep(1500);
		 * driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[6]/div/div[2]/div/div[2]/div[1]/div[1]"
		 * )).click(); System.out.println("NM100"); Thread.sleep(5000);
		 */	
		playAgain.playAgain(driver, longWait, maxAttempts, true);
	}



}
