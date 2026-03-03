package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.Test;


public class Reload {
	@Test
	public void reload(WebDriver driver, WebDriverWait wait) {
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("btn-attack-start")));
		System.out.println("Start disappeared");
		// if url like game.granbluefantasy.jp/#result
		if (driver.getCurrentUrl().startsWith("https://game.granbluefantasy.jp/#raid")) {
			driver.findElement(By.className("btn-treasure-footer-reload")).click();
			System.out.println("Reload clicked");
		}
		//autoButton.click(); //Turn on auto-battle		
		return;
	}
}

