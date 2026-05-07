package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProQuests {
	@Test
	public void proQuests() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String url = "https://game.granbluefantasy.jp/#quest/extra";
		
		driver.get("https://game.granbluefantasy.jp/#mypage");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-mbp-detail")));
		WebElement pendantButton = driver.findElement(By.className("btn-mbp-detail"));
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_DOWN).perform();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("btn-footer-quest"))));
		Thread.sleep(1000);
		pendantButton.click();
		wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Pendant Details"));
		WebElement pendantR = driver.findElement(By.cssSelector("div[class$='rarity-r']"));
		WebElement parentR = pendantR.findElement(By.xpath(".."));
		WebElement countR = parentR.findElement(By.xpath(".//div[@class='txt-bonus-num']/span"));
		WebElement pendantSR = driver.findElement(By.cssSelector("div[class$='rarity-sr']"));
		WebElement parentSR = pendantSR.findElement(By.xpath(".."));
		WebElement countSR = parentSR.findElement(By.xpath(".//div[@class='txt-bonus-num']/span"));
		boolean maxR;
		boolean maxSR;
		if (!countR.getAttribute("class").isEmpty())  {
			maxR = true;
		} else {
			maxR = false;
		}
		if (!countSR.getAttribute("class").isEmpty())  {
			maxSR = true;
		} else {
			maxSR = false;
		}
		
		
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
						wait.until(ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#quest/supporter"));
						wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-supporter")));
						if (type.equals("normal") && questNum <= 1) {
							if ( questNum == 0 && !maxR ) {
								driver.findElement(By.cssSelector("div[class*='id-5'][data-id='5']")).click();
								wait.until(ExpectedConditions.textToBe(By.className("prt-deck-title"), "Hayai"));
								driver.findElement(By.cssSelector("ol[class*='flex-control-nav'] > li:nth-child(3)")).click();
							} else if ( questNum == 1 && !maxSR) {
								driver.findElement(By.cssSelector("div[class*='id-4'][data-id='4']")).click();
								wait.until(ExpectedConditions.textToBe(By.className("prt-deck-title"), "Quick"));
								driver.findElement(By.cssSelector("ol[class*='flex-control-nav'] > li:nth-child(2)")).click();
							} else {
								driver.findElement(By.cssSelector("div[class*='id-7'][data-id='7']")).click();
								wait.until(ExpectedConditions.textToBe(By.className("prt-deck-title"), "Event"));
							}
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
