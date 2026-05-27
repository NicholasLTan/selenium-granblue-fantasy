package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class AutoBattle {
	public void autoBattle(WebDriver driver, WebDriverWait wait) {		
		try { 	
			//WebElement autoButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-auto")));  
			//WebElement autoButton; 
			// raid url: https://game.granbluefantasy.jp/#raid_multi/44875607170
			// solo url: https://game.granbluefantasy.jp/#raid/1982213644
			By autoButton = By.className("btn-auto");
			By attackButton = By.className("btn-attack-start");
			Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofMillis(50));
				
			wait.until ( ExpectedConditions.or (
					ExpectedConditions.elementToBeClickable(autoButton),
					ExpectedConditions.elementToBeClickable(attackButton)));
			if (!driver.findElements(autoButton).isEmpty() && driver.findElement(autoButton).isDisplayed()) { 
				driver.findElement(autoButton).click(); //Turn on auto-battle
				System.out.println("auto");
			} else if (!driver.findElements(attackButton).isEmpty() && driver.findElement(attackButton).isDisplayed()) {
				driver.findElement(attackButton).click();
				System.out.println("attack");
				//wait.until(ExpectedConditions.stalenessOf(driver.findElement(attackButton)));
				fluentWait.until(ExpectedConditions.elementToBeClickable(autoButton));
				try { 
					driver.findElement(autoButton).click();
				} catch (StaleElementReferenceException e) {
					if (!driver.findElements(By.className("btn-result")).isEmpty()) {
						System.out.println("AutoBattle AutoButton Stale Goto Result");
						wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-result")));
						driver.findElement(By.className("btn-result")).click();
					}
				}				
				System.out.println("semi-auto");
			}
		} catch (TimeoutException e) {
			String url = driver.getCurrentUrl();
			System.out.println("Autobattle " + url);
			if (url.startsWith("https://game.granbluefantasy.jp/#result_multi/")) { 
				System.out.println("Autobattle exited early");
				return; 
			} else if (url.startsWith("https://game.granbluefantasy.jp/#raid_multi/")) {
				System.out.println("Battle ended first");
				Reload reload = new Reload();
				reload.reload(driver, wait);
				return;
			}
		}
		return;
	}
}
/**
driver.findElement(By.cssSelector("div[class='pop-usual pop-rematch-fail pop-show']")).click();
**/
