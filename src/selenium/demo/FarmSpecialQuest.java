package selenium.demo;
import java.time.Duration;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmSpecialQuest {
	@Test
	public void farmSpecialQuest() throws InterruptedException {
		int maxAttempts = 500; // Optional: To prevent infinite loops
		int attempts = 0;
		String itemID = "5431";
		
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
		PlayAgain playAgain = new PlayAgain();
		MaterialTracker materialTracker = new MaterialTracker();
		//By selectOne = By.cssSelector("div[data-key='100']"); //DHalo
		//By selectTwo = By.cssSelector("div[data-chapter-id='51005']"); //DHalo play
		//By selectThree = By.cssSelector("div[class='btn-usual-ok hide-common-text']"); //DHalo popup play
		By selectOne = By.cssSelector("div[data-key='97']"); //Angel Halo
		By selectTwo = By.cssSelector("div[data-chapter-id='51003']"); //VH
		//By selectThree = By.cssSelector("div[class='btn-usual-ok hide-common-text']"); //DHalo popup play
		
		driver.get("https://game.granbluefantasy.jp/#quest/extra");
		System.out.println("Special Quests");
		
		while (materialTracker.materialTracker(driver, itemID)) {
			wait.until(ExpectedConditions.elementToBeClickable(selectOne));
			driver.findElement(selectOne).click();
			wait.until(ExpectedConditions.elementToBeClickable(selectTwo));
			driver.findElement(selectTwo).click();
			//wait.until(ExpectedConditions.elementToBeClickable(selectThree));
			//driver.findElement(selectThree).click();
			System.out.println("Play");
			playAgain.playAgain(driver, wait, itemID, false);
		}
		//playAgain.playAgain(driver, wait, maxAttempts);
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
