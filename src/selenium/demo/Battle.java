package selenium.demo;

import java.util.List;

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
			List<WebElement> elementPopup = driver.findElements(popup);
			if (driver.getCurrentUrl().contains("https://game.granbluefantasy.jp/#result")) {
				return;
			} else if (!elementPopup.isEmpty() && elementPopup.get(0).isDisplayed() ) {
				System.out.println("Battle OK Click");
				elementPopup.get(0).findElement(ok).click();
				wait.until(ExpectedConditions.stalenessOf(elementPopup.get(0)));
				driver.findElement(By.className("btn-treasure-footer-reload")).click();
				return;
			}
		}
		if (!driver.getCurrentUrl().startsWith("https://game.granbluefantasy.jp/#raid")) {
			return;
		}
	}
}
