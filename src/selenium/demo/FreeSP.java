package selenium.demo;
import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FreeSP {
	@Test
	public void freeSP() throws InterruptedException {
		int maxAttempts = 4000; // To prevent infinite loops
		By click1 = By.cssSelector("div[data-key^='800']"); //Exclusive Quest
		By click2 = By.cssSelector("div[data-chapter-id^='8000']");	//Play

		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login());
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(30)));
		
		driver.get("https://game.granbluefantasy.jp/#quest/extra");
		System.out.println("Event SP Quests");
		wait.until(ExpectedConditions.elementToBeClickable(click1));
		driver.findElement(click1).click();  
		System.out.println("FreeSP");
		wait.until(ExpectedConditions.elementToBeClickable(click2));
		driver.findElement(click2).click();  
		System.out.println("Play");
		
		PlayAgain playAgain = new PlayAgain();
		playAgain.playAgain(driver, wait, maxAttempts, false);		
	}
}
