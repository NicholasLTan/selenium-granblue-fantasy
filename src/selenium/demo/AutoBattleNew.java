package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
public class AutoBattleNew {
	public void autoBattleNew(WebDriver driver, WebDriverWait wait) {		
		try { 	
			//wait.until(ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#raid_multi/"));
			System.out.println("Auto-battle");
			Wait<WebDriver> wait2 = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(15))
					.pollingEvery(Duration.ofMillis(200));
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='txt-auto-setting']")));
			System.out.println("element found");
			driver.findElement(By.cssSelector("div[class='txt-auto-setting']")).click();
			//autoButton.click(); //Turn on auto-battle
			//Actions actions = new Actions(driver);
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
//<div class="txt-auto-setting">Tap the screen to enable/disable auto attack.</div>