package selenium.demo;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventOld {
	@Test
	public void eventOld() throws InterruptedException {
		boolean maniac = true;
		boolean raid = false;
		boolean extreme = true;
		int maxAttempts;
		int extremeAttempts = 10; //For farming Extreme
		
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login(), "WebDriver must not be null");
		PlayAgain playAgain = new PlayAgain();
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(30)));
		WebDriverWait longWait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(120)));
		driver.get("https://game.granbluefantasy.jp/#quest/extra/event/6043");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("atx-lead-link")));
		System.out.println("Event SP Quests");		
		System.out.println(driver.findElement(By.className("atx-lead-link")).getText());
		int attempts;

		//Maniac Loop
		if ( maniac ) {
			for (int i=6; i<=7; i++) {
				maxAttempts = 2; //Maniac 2x daily limit
				attempts = 0;
				if (attempts < maxAttempts) {
					By cssManiac = By.cssSelector("div[data-difficulty='" + i + "']");
					wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='1']")));
					driver.findElement(By.cssSelector("div[data-group='1']")).click(); //Solo
					wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
					List<WebElement> elementExists = driver.findElements(cssManiac);
					for (int j=0; j<elementExists.size(); j++) {						
						wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
						if (driver.findElements(By.className("pop-usual")).isEmpty()) {
							wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='1']")));
							driver.findElement(By.cssSelector("div[data-group='1']")).click(); //Solo
							wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
						}
						elementExists = driver.findElements(cssManiac);
						String classAttr = elementExists.size() > 0 ? elementExists.get(j).getAttribute("class") : null;
						if (elementExists.size() > 0 && classAttr != null && !classAttr.contains("disable")) {
							System.out.println("EventOld Maniac " + i + "(" + j + ") launching.");
							elementExists.get(j).click(); //Maniac+
							wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-ok"))); //Daily limit OK
							maxAttempts = Integer.valueOf(driver.findElement(By.className("txt-popup-body")).getText().replaceAll("[^0-9]", ""));
							System.out.println("With " + maxAttempts + " attempts.");
							driver.findElement(By.className("btn-usual-ok")).click(); //Maniac
							playAgain.playAgain(driver, longWait, maxAttempts, false);
						} else {
							System.out.println("EventOld Maniac " + i + "(" + j + ") not found.");
							if (j + 1 == elementExists.size()) {
								wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
								driver.findElement(By.className("btn-usual-close")).click();
							}
							continue;
						}
					}
				}				
			}
			if (!driver.findElements(By.className("pop-usual")).isEmpty()) {
				driver.findElement(By.className("btn-usual-close")).click();
			}
		}
		
		//Raid EX farming
		if ( raid ) {
			maxAttempts = 5; // Optional: To prevent infinite loops
			attempts = 0;
			if (attempts < maxAttempts) {
				wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='3']")));
				driver.findElement(By.cssSelector("div[data-group='3']")).click(); //Raid
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-difficulty='4']")));
				driver.findElement(By.cssSelector("div[data-difficulty='9']")).click(); //Extreme+
				//driver.findElement(By.cssSelector("div[data-difficulty='4']")).click(); //Extreme
			}
			playAgain.playAgain(driver, longWait, maxAttempts, false);							
		}
		
		//Extreme Farming
		if ( extreme ) {
			maxAttempts = extremeAttempts;
			attempts = 0;		
			if (attempts < maxAttempts) {
				wait.until(ExpectedConditions.urlContains("#quest/extra/event"));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-group='1']")));
				driver.findElement(By.cssSelector("div[data-group='1']")).click(); //Solo
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-difficulty='4']")));
				driver.findElement(By.cssSelector("div[data-difficulty='9']")).click(); //Extreme+
				//driver.findElement(By.cssSelector("div[data-difficulty='4']")).click(); //Extreme
			}		
			playAgain.playAgain(driver, longWait, maxAttempts, false);
		}
		System.out.println("Script complete");
	}
}
