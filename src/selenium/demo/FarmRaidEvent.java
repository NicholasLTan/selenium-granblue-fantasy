package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmRaidEvent {
	@Test
	public void farmRaidEvent() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		Results results = new Results();
		WebElement refresh;
		IsElementPresent ePresent = new IsElementPresent();
		/**UnF
		final String raidCss = "img[alt$='hell100']";
		final String raidStr = "hell100";
		**/
		final String raidStr = "treasureraid169_high";
		final String raidCss = "img[alt$='" + raidStr + "']";
		final String eventUrl = "https://game.granbluefantasy.jp/#event/treasureraid169";
		
		//WebElement raid;


		driver.get("https://game.granbluefantasy.jp/#quest/assist");
		// driver.findElement(By.cssSelector("img[class='img-global-banner'][src*='treasureraid165']")).click();
		// // Lives Yet Unwritten
		// driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[2]/div/img")).click();
		// //Imagination Overdrive banner
		System.out.println("Raids");
		Thread.sleep(5000);

		driver.findElement(By.id("tab-multi")).click();
		Thread.sleep(1000);

		driver.findElement(By.cssSelector("div[class^='btn-switch-list event']")).click();
		Thread.sleep(1000);

		int maxAttempts = 3; // Optional: To prevent infinite loops
		int attempts = 1;
		while (attempts <= maxAttempts) {
			System.out.println("Attempt " + attempts + " started");
			boolean retry = false;
			boolean ten = false;
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-switch-list event active']")));
			WebElement raidList = driver.findElement(By.id("prt-multi-list"));
			List<WebElement> raids;
			raids = raidList.findElements(By.cssSelector(raidCss));
			Thread.sleep(1000);
			refresh = driver.findElement(By.cssSelector("div[class='btn-switch-list event active']"));
			int refreshCount=0;
			System.out.println(raids.size() + " " + raidStr);
			while (raids.size() < 1) { 
				System.out.println("Refresh " + refreshCount);
				//wait.until(element.isDisplayed() -> element.click());
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				if (refreshCount == 50) {
					System.out.println(refreshCount + " refreshes");
					raids = raidList.findElements(By.cssSelector("img[class='img-raid-thumbnail']"));
					ten = true;
					break;
				}
				refresh.click();
				refreshCount++;
				Thread.sleep(100);
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				Thread.sleep(1000);
				raids = raidList.findElements(By.cssSelector(raidCss));
				System.out.println(raids.size() + " " + raidStr);
				Thread.sleep(1000);
			}
			/*
			 * int rNum = 1; for (WebElement raid : raids) { //WebElement raidStatus =
			 * raid.findElement(By.xpath(
			 * "./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
			 * System.out.println(rNum + " " + raid.getAttribute("class")); rNum++; }
			 */
			System.out.println("*****");
			raids = raidList.findElements(By.xpath("./div"));
			int raidNum = 1;
			int maxPct = 0;
			int maxNum = 0;
			for (WebElement raid : raids) {				
				WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-thumbnail']/img[@class='img-raid-thumbnail']"));
				WebElement raidPct = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[@class='prt-raid-gauge']/div[@class='prt-raid-gauge-inner']"));
				String strPct = raidPct.getAttribute("style");
				int intPct = Integer.parseInt(strPct.replaceAll("\\D+", ""));
				if (raidStatus.getAttribute("alt").contains(raidStr) && (intPct > maxPct)) {
					maxPct = intPct;
					maxNum = raidNum;
				}
				//System.out.println("gauge = " + raidPct.getAttribute("style"));
				System.out.println(raidNum + " " + raidStatus.getAttribute("alt") + " " + intPct);
				
				raidNum++;
			}
			maxNum--;
			WebElement raid = raids.get(maxNum);
			System.out.println("maxNum = " + maxNum);
			WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-thumbnail']/img[@class='img-raid-thumbnail']"));
			
			if (raidStatus.getAttribute("alt").endsWith(raidStr)) {
				System.out.println("");
				if (maxNum > 6) {
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_DOWN).perform();
					Thread.sleep(1000);
				}
				raid.click();
				Thread.sleep(5000);
				if (driver.getCurrentUrl().equals("https://game.granbluefantasy.jp/#quest/assist")) {
					System.out.println("assist");
					driver.findElement(By.className("btn-usual-ok")).click();
					retry = true;
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-switch-list event active']")));
					driver.findElement(By.cssSelector("div[class='btn-switch-list event active']")).click();
					//break;
				} else {
					System.out.println("break");
					//break;
				}
			} else if (ten) {
				System.out.println(raid.getAttribute("class"));
				
				raid.click();
				//break;
			}
			
			
			
			
			if (!retry) {
				
				confirmTeam.confirmTeam(wait);
				Thread.sleep(1500);
				String url = driver.getCurrentUrl();
				System.out.println("Conf " + url + " Auto");
				if ( url.startsWith("https://game.granbluefantasy.jp/#quest/supporter_raid") ) {
					System.out.println("supporter_raid");
					Thread.sleep(1000);
					boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-ok"));
					if (elementExists) {
						System.out.println("Raid ended");
						driver.findElement(By.className("btn-usual-ok")).click();
						Thread.sleep(2000);
						driver.get("https://game.granbluefantasy.jp/#quest/assist");
						wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-switch-list event active']")));
						Thread.sleep(1000);
						driver.findElement(By.cssSelector("div[class='btn-switch-list event active']")).click();
						Thread.sleep(5000);
						continue;
					}
				} else if ( url.startsWith("https://game.granluefantasy.jp/#result_multi") ) {
					System.out.println("result_multi");
					boolean elementExists = ePresent.isElementPresent(driver, By.className("btn-control"));
					if (elementExists) {
						driver.findElement(By.className("btn-control")).click();
						Thread.sleep(2000);
						driver.get("https://game.granbluefantasy.jp/#quest/assist");
						Thread.sleep(5000);
						continue;
					}
				}
				autoBattle.autoBattle(driver, wait);
				results.results(driver, longWait, false);
				System.out.println("Attempt " + attempts + " completed");
				attempts++;
				wait.until(ExpectedConditions.urlMatches(eventUrl));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-multi']")));
				driver.findElement(By.cssSelector("div[class^='btn-event-multi']")).click();
				wait.until(ExpectedConditions.urlMatches("https://game.granbluefantasy.jp/#quest/assist/event"));
				//Thread.sleep(5000);
			}
		}
		System.out.println("Run concluded");

		/*
		 * driver.findElement(By.cssSelector("img[class='img-raid-boss']")).click();
		 * System.out.println("Raid Battle"); Thread.sleep(1000);
		 * driver.findElement(By.cssSelector(
		 * "img[class='img-quest-thumb'][src*='93684']")).click();
		 * System.out.println("Impossible");
		 * 
		 * 
		 * if (attempts < maxAttempts) {
		 * driver.findElement(By.cssSelector("[data-group='3']")).click(); // Raid
		 * Thread.sleep(1000); driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div[3]/div[2]/div[4]"
		 * )) .click(); // Hard Thread.sleep(2000); }
		 * 
		 * while (attempts < maxAttempts) { confirmTeam.confirmTeam(wait);
		 * autoBattle.autoBattle(wait); IsElementPresent ePresent = new
		 * IsElementPresent(); Results results = new Results(); if (attempts + 1 ==
		 * maxAttempts) { results.results(driver, wait, false); } else {
		 * results.results(driver, wait, true); }
		 * 
		 * boolean elementExists = ePresent.isElementPresent(driver,
		 * By.className("btn-usual-close")); if (elementExists) { Thread.sleep(1000);
		 * driver.findElement(By.className("btn-usual-close")).click(); 
		 * System.out.println("Mission Close"); Thread.sleep(2000); } attempts++;
		 * System.out.println(attempts); }
		 */

		/*
		 * maxAttempts = 2; // Optional: To prevent infinite loops attempts = 0;
		 * driver.findElement(By.cssSelector("[data-group='1']")).click(); //Solo
		 * Thread.sleep(2000); driver.findElement(By.xpath(
		 * "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div/div[3]/div[4]"
		 * )).click(); //Maniac Thread.sleep(2000);
		 * driver.findElement(By.className("btn-usual-ok")).click(); //Maniac
		 * Thread.sleep(2000); while (attempts < maxAttempts) {
		 * confirmTeam.confirmTeam(wait); autoBattle.autoBattle(wait); IsElementPresent
		 * ePresent = new IsElementPresent(); Results results = new Results();
		 * results.results(driver, wait, true);
		 * 
		 * 
		 * boolean elementExists = ePresent.isElementPresent(driver,
		 * By.className("btn-usual-close")); if (elementExists) { Thread.sleep(1000);
		 * driver.findElement(By.className("btn-usual-close")).click();
		 * System.out.println("Mission Close"); Thread.sleep(2000); }
		 * 
		 * attempts++; System.out.println(attempts); }
		 */
	}
}

