package selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Battle {
	public void battle(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		By ok = By.className("btn-usual-ok");
		By popup = By.className("pop-usual");
		AutoBattle autoBattle = new AutoBattle();
		autoBattle.autoBattle(driver, wait);
		while (driver.getCurrentUrl().startsWith("https://game.granbluefantasy.jp/#raid")) {
			wait.until(ExpectedConditions.or(
					ExpectedConditions.visibilityOfElementLocated(popup),
					ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#result")));
			WebElement element = driver.findElement(ok);
			if (element.isDisplayed()) {
				System.out.println("Battle OK Click");
				element.click();
				wait.until(ExpectedConditions.stalenessOf(element));
			}
			
		}
		if (!driver.getCurrentUrl().startsWith("https://game.granbluefantasy.jp/#raid")) {
			return;
		}
	}
}
