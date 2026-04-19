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
		int attempts = 0;
		String itemID = "5441";
		final String eventUrl = "https://game.granbluefantasy.jp/#event/advent";
		
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(600));
		PlayAgain playAgain = new PlayAgain();
		MaterialTracker materialTracker = new MaterialTracker();
		//By selectOne = By.cssSelector("div[data-key='100']"); //DHalo
		//By selectTwo = By.cssSelector("div[data-chapter-id='51005']"); //DHalo play
		//By selectThree = By.cssSelector("div[class='btn-usual-ok hide-common-text']"); //DHalo popup play
		By selectOne = By.cssSelector("div[class='prt-hl-multi']"); //Angel Halo
		By selectTwo = By.cssSelector("div[data-chapter-id='74345']"); //VH
		//By selectThree = By.cssSelector("div[class='btn-usual-ok hide-common-text']"); //DHalo popup play
		
		driver.get(eventUrl);
		System.out.println("Rise of the Beasts");
		/*
		while (materialTracker.materialTracker(driver, itemID)) {
			wait.until(ExpectedConditions.elementToBeClickable(selectOne));
			driver.findElement(selectOne).click();
			wait.until(ExpectedConditions.elementToBeClickable(selectTwo));
			driver.findElement(selectTwo).click();
			//wait.until(ExpectedConditions.elementToBeClickable(selectThree));
			//driver.findElement(selectThree).click();
			System.out.println("Play");
			playAgain.playAgain(driver, wait, itemID);
		}
		*/
		
		wait.until(ExpectedConditions.elementToBeClickable(selectOne));
		maxAttempts = Integer.valueOf(driver.findElement(selectOne).findElement(By.className("prt-stock-count")).getText());
		driver.findElement(selectOne).click();
		wait.until(ExpectedConditions.elementToBeClickable(selectTwo));
		driver.findElement(selectTwo).click();
		playAgain.playAgain(driver, wait, maxAttempts);
		/*
		 * while (attempts < maxAttempts) { confirmTeam.confirmTeam(wait);
		 * autoBattle.autoBattle(driver, wait); IsElementPresent ePresent = new
		 * IsElementPresent(); Results results = new Results(); if ( attempts + 1 ==
		 * maxAttempts) { results.results(driver, wait, false); } else {
		 * results.results(driver, wait, true); } attempts++;
		 * System.out.println(attempts); }
		 */
		System.out.println("Script Complete");
	}
}
