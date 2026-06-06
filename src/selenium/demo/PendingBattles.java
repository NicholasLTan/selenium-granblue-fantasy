package selenium.demo;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PendingBattles {
	@Test
	public void pendingBattles() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login());
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(10)));
		Results results = new Results();
		int count = 0;
		
		while (true) {
			driver.get("https://game.granbluefantasy.jp/#quest/assist/unclaimed");
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("txt-multi-title"), "Pending Battles"));
			Thread.sleep(1500); //Delay necessary for making sure raids populate before creating raidList
			List<WebElement> raidList = driver.findElements(By.cssSelector("div[class*='btn-multi-raid']"));
			if (!raidList.isEmpty()) {
				count++;
				raidList.get(0).click();
				results.results(driver, wait, false);
			} else {
				break;
			}
		}
		System.out.println("Cleared " + count + " pending battles");
	}
}
