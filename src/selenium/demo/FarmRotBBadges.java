package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmRotBBadges {
	@Test
	public void farmRotBBadges() throws InterruptedException {
		int maxAttempts = 3;
		String[] questIdList = {"711191","711041","711141","711091"};
		final String eventUrl = "https://game.granbluefantasy.jp/#event/advent";
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		Battle battle = new Battle();
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		Results results = new Results();
		WebElement refresh;
		IsElementPresent ePresent = new IsElementPresent();
		
		int attempts;
		driver.get(eventUrl);		
		for (String questId : questIdList) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			PlayAgain playAgain = new PlayAgain();
			attempts = 0;
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-select-multi'")));
			driver.findElement(By.cssSelector("div[class='btn-select-multi']")).click();
			String questString = "div[data-quest-id='" + questId + "']";
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(questString)));
			driver.findElement(By.cssSelector(questString)).click();
			playAgain.playAgain(driver, longWait, maxAttempts);
		}
		System.out.println("Script complete");
	}
}
