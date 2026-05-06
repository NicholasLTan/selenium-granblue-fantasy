package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProQuests {
	@Test
	public void proQuests() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String url = "https://game.granbluefantasy.jp/#quest/extra";
		/*
		driver.get("https://game.granbluefantasy.jp/#mypage");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-mbp-detail")));
		driver.findElement(By.className("btn-mbp-detail")).click();
		wait.until(ExpectedConditions.textToBePresentInElementValue(By.className("prt-popup-header"), "Pendant Details"));
		get R/SR true/false
		*/
		
		
		driver.get(url);
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-pro-list")));
		driver.findElement(By.className("btn-pro-list")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
		if ( ! driver.findElement(By.className("prt-popup-header")).getText().equals("Pro Quest List") ) {
			System.out.println("Failed on Pro Quest List");
		}
		String[] proList = {"normal", "high", "extra"};
		for (String type : proList) {
			int questNum = 0;
			PlayAgain playAgain = new PlayAgain();
			System.out.println("Type = " + type);
			wait.until(ExpectedConditions.urlContains(url));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
			driver.findElement(By.cssSelector("div[class^='btn-quest-type'][data-type='" + type + "']")).click();			
			WebElement questGroup = driver.findElement(By.cssSelector(".prt-stage-quest.active"));
			List<WebElement> playButtons = questGroup.findElements(By.cssSelector("div[class^='btn-set-quest']"));
			while (!playButtons.isEmpty()) {
				playButtons = questGroup.findElements(By.cssSelector("div[class^='btn-set-quest']"));
				System.out.println("Size = " + playButtons.size());
				for (int i=0; i<playButtons.size(); i++) {
					wait.until(ExpectedConditions.urlContains(url));
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
					questGroup = driver.findElement(By.cssSelector(".prt-stage-quest.active"));
					playButtons = questGroup.findElements(By.cssSelector("div[class^='btn-set-quest']"));
					System.out.println("i = " + i);
					if (!playButtons.get(i).getAttribute("class").contains("disable")) {
						playButtons.get(i).click();
						wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='pop-pro-quest-skip']")));
						driver.findElement(By.className("btn-usual-ok")).click();
						if (type.equals("normal") && questNum <= 1) {

						}
						playAgain.playSkip(driver, wait);
						questNum++;
					}
				}
				break;
			}
			//div.btn-set-quest.multi.type-pro-list.use-treasure.ico-clear
			//div.btn-set-quest.multi.type-pro-list.use-treasure.ico-clear
			//div.btn-set-quest.multi.type-pro-list.ico-clear
		}
		System.out.println("ProQuests Complete");
	}
}
