package selenium.demo;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

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
		WebDriver driver = Objects.requireNonNull(login.login());
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(10)));
		String url = "https://game.granbluefantasy.jp/#quest/extra";
		int logLevel = 0;
		
		driver.get("https://game.granbluefantasy.jp/#mypage");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-mbp-detail")));
		if (!driver.findElements(By.className("prt-popup-header")).isEmpty()) {
			driver.findElement(By.className("btn-usual-close")).click();
		}
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
		String countRClass = countR.getAttribute("class");
		if (countRClass != null && !countRClass.isEmpty())  {
			maxR = true;
		} else {
			maxR = false;
		}
		String countSRClass = countSR.getAttribute("class");
		if (countSRClass != null && !countSRClass.isEmpty())  {
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
			PlayAgain playAgain = new PlayAgain();
			if (logLevel >= 1 ) { System.out.println("Type = " + type); }
			wait.until(ExpectedConditions.urlContains(url));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
			driver.findElement(By.cssSelector("div[class^='btn-quest-type'][data-type='" + type + "']")).click();			
			WebElement questGroup = driver.findElement(By.cssSelector(".prt-stage-quest.active"));
			List<WebElement> playButtons = questGroup.findElements(By.cssSelector("div[class^='btn-set-quest']"));
			while (!playButtons.isEmpty()) {
				playButtons = questGroup.findElements(By.cssSelector("div[class^='btn-set-quest']"));
				if (logLevel >= 1) { System.out.println("Size = " + playButtons.size()); }
				for (int i=0; i<playButtons.size(); i++) {
					wait.until(ExpectedConditions.urlContains(url));
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
					questGroup = driver.findElement(By.cssSelector(".prt-stage-quest.active"));
					playButtons = questGroup.findElements(By.cssSelector("div[class^='btn-set-quest']"));
					if (logLevel >= 1) { System.out.println("i = " + i); }
					String playClass = playButtons.get(i).getAttribute("class");
					if (playClass != null && !playClass.contains("disable")) {
						playButtons.get(i).click();
						wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='pop-pro-quest-skip']")));
						driver.findElement(By.className("btn-usual-ok")).click();
						wait.until(ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#quest/supporter"));
						wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-supporter")));
						if (logLevel >= 1) { System.out.println("Type = " + type + "; questNum = " + i); }
						if (type.equals("normal") && i <= 1) {
							if ( i == 0 && !maxR ) {
								driver.findElement(By.cssSelector("div[class*='id-5'][data-id='5']")).click();
								wait.until(ExpectedConditions.or(
										ExpectedConditions.textToBe(By.className("prt-deck-title"), "Hayai"),
										ExpectedConditions.textToBe(By.className("prt-deck-title"), "R")));
								driver.findElement(By.cssSelector("ol[class*='flex-control-nav'] > li:nth-child(3)")).click();
								if (logLevel >= 1) { System.out.println("Switch R"); }
							} else if ( i == 1 && !maxSR) {
								driver.findElement(By.cssSelector("div[class*='id-4'][data-id='4']")).click();
								wait.until(ExpectedConditions.or(
										ExpectedConditions.textToBe(By.className("prt-deck-title"), "Quick"),
										ExpectedConditions.textToBe(By.className("prt-deck-title"), "SR")));
								driver.findElement(By.cssSelector("ol[class*='flex-control-nav'] > li:nth-child(2)")).click();
								if (logLevel >= 1) { System.out.println("Switch SR"); }
							} else {
								driver.findElement(By.cssSelector("div[class*='id-7'][data-id='7']")).click();
								wait.until(ExpectedConditions.textToBe(By.className("prt-deck-title"), "Event"));
								if (logLevel >= 1) { System.out.println("Switch *"); }
							}
						}
						playAgain.playSkip(driver, wait);
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
