package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class AutoBattle {
	public void autoBattle(WebDriver driver, WebDriverWait wait) {		
		try { 	
			WebElement autoButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-auto")));  
			autoButton.click(); //Turn on auto-battle
			System.out.println("Auto-battle");
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
