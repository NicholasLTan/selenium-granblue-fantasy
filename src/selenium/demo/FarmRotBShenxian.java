package selenium.demo;
import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmRotBShenxian {
	@Test
	public void farmSpecialQuest() throws InterruptedException {
		int maxAttempts = 500; // Optional: To prevent infinite loops
		By selectOne = By.cssSelector("div[class='prt-hl-multi']"); //Shenxian
		By selectTwo = By.cssSelector("div[data-chapter-id='74345']"); //150
		//By selectOne = By.cssSelector("div[class='prt-raid-image']"); //Extreme+ Elemental
		final String eventUrl = "https://game.granbluefantasy.jp/#event/advent";
		
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(600));
		PlayAgain playAgain = new PlayAgain();
		
		driver.get(eventUrl);
		System.out.println("Rise of the Beasts");
		
		wait.until(ExpectedConditions.elementToBeClickable(selectOne));
		maxAttempts = Integer.valueOf(driver.findElement(selectOne).findElement(By.className("prt-stock-count")).getText());
		driver.findElement(selectOne).click();
		wait.until(ExpectedConditions.elementToBeClickable(selectTwo));
		driver.findElement(selectTwo).click();
		playAgain.playAgain(driver, wait, maxAttempts, false);
		System.out.println("Script Complete");
	}
}
