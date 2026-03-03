package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmRaids {
	@Test
	public void farmRaids() throws InterruptedException {
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		Results results = new Results();
		WebElement refresh;
		IsElementPresent ePresent = new IsElementPresent();
		//WebElement raid;

		final String raidStr = "treasureraid166_high";
		final String raidCss = "img[alt$='" + raidStr + "']";
		final String eventUrl = "https://game.granbluefantasy.jp/#event/treasureraid166";

		final By refreshBy = By.cssSelector("div[class='btn-search-refresh']");
		final By finderSlot = By.cssSelector("div[class^='btn-search-switch slot2']");

		final String normalApStr = "prt-use-ap";
		final String normalApCss = "div[class='" + normalApStr + "']";
		final String lowApStr = normalApStr + " decreased";
		final String lowApCss = "div[class='" + lowApStr + "']";

		// driver.findElement(By.cssSelector("img[class='img-global-banner'][src*='treasureraid165']")).click();
		// // Lives Yet Unwritten
		// driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[2]/div/img")).click();
		// //Imagination Overdrive banner

		boolean exitAtZero = true;
		int maxAttempts = 20; // Optional: To prevent infinite loops
		int attempts = 1;
		while (attempts <= maxAttempts) {
			if (driver.getCurrentUrl() != "https://game.granbluefantasy.jp/#quest/assist") {
				driver.get("https://game.granbluefantasy.jp/#quest/assist");
				System.out.println("Raids");
				wait.until(ExpectedConditions.elementToBeClickable(By.id("tab-search")));
				driver.findElement(By.id("tab-search")).click();
				System.out.println("search click");
				wait.until(ExpectedConditions.elementToBeClickable(finderSlot));
				driver.findElement(finderSlot).click();
				System.out.println("finder click");
			}
			
			System.out.println("Attempt " + attempts + " started");
			boolean retry = false;
			boolean ten = false;
			wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
			WebElement raidList = driver.findElement(By.id("prt-search-list"));
			List<WebElement> raids;
			raids = raidList.findElements(By.cssSelector(lowApCss));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
			refresh = driver.findElement(refreshBy);
			int refreshCount=0;
			System.out.println(raids.size() + " reduced ap");
			while (raids.size() < 1) { 
				System.out.println("Refresh " + refreshCount);
				//wait.until(element.isDisplayed() -> element.click());
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				if (refreshCount == 3) {
					System.out.println(refreshCount + " refreshes");
					raids = raidList.findElements(By.cssSelector(normalApCss));
					ten = true;
					break;
				}
				refresh.click();
				refreshCount++;
				Thread.sleep(100);
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				Thread.sleep(1000);
				raids = raidList.findElements(By.cssSelector(lowApCss));
				System.out.println(raids.size() + " reduced ap");
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
			if (raids.size() == 0) { System.out.println("zero"); continue; }
			int raidNum = 1;
			int maxPct = 0;
			int maxNum = 0;
			int currBP = Integer.valueOf(driver.findElement(By.cssSelector("div[data-current-bp]")).getAttribute("data-current-bp"));
			System.out.println(currBP + " BP");
			if ( exitAtZero && currBP <= 1 ) { break; }
			for (WebElement raid : raids) {				
				WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
				WebElement raidPct = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[@class='prt-raid-gauge']/div[@class='prt-raid-gauge-inner']"));
				String strPct = raidPct.getAttribute("style");
				int intPct = Integer.parseInt(strPct.replaceAll("\\D+", ""));				
				
				if ((ten || raidStatus.getAttribute("class").equals(lowApStr))) {
					//System.out.println(raidNum + " " + raidStatus.getAttribute("class") + " " + intPct);					
					if (intPct > maxPct) {
						maxPct = intPct;
						maxNum = raidNum;
					}
				}
				raidNum++;
			}						
			System.out.println("maxNum = " + maxNum + "; maxPct = " + maxPct);
			maxNum--;
			WebElement raid = raids.get(maxNum);
			WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
			System.out.println("");
			if (maxNum > 5) {
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
				wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
				driver.findElement(refreshBy).click();
				//break;
				/*
				 * } else { System.out.println("break");
				 */
			}

			if (!retry) {
				confirmTeam.confirmTeam(wait);
				//Thread.sleep(1500);
				String url = driver.getCurrentUrl();
				System.out.println("Conf " + url + " Auto");
				try {
					autoBattle.autoBattle(driver, wait);
				} catch (ElementClickInterceptedException e) {
					if ( url.startsWith("https://game.granbluefantasy.jp/#quest/supporter_raid")) {
						System.out.println("supporter_raid"); 
						Thread.sleep(1000); 
						boolean	elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-ok")); 
						if (elementExists) {
							System.out.println("Raid ended");
							driver.findElement(By.className("btn-usual-ok")).click(); 
							Thread.sleep(2000);
							driver.get("https://game.granbluefantasy.jp/#quest/assist");
							wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
							Thread.sleep(1000); 
							driver.findElement(refreshBy).click();
							Thread.sleep(5000); 
							continue; 
						} 
					} else if (url.startsWith("https://game.granluefantasy.jp/#result_multi")) {
						System.out.println("result_multi"); 
						boolean elementExists =	ePresent.isElementPresent(driver, By.className("btn-control")); 
						if (elementExists) { 
							driver.findElement(By.className("btn-control")).click();
							Thread.sleep(2000);
							driver.get("https://game.granbluefantasy.jp/#quest/assist");
							Thread.sleep(5000); 
							continue; 
						} 
					} else if (url.startsWith("https://game.granbluefantasy.jp/#raid_multi")) {
						System.out.println("raid_multi");
						driver.findElement(By.className("btn-usual-ok")).click();
						Thread.sleep(1000);
						driver.findElement(By.className("btn-treasure-footer-reload")).click();
					}
				}
				results.results(driver, longWait, false);
				
				System.out.println("Attempt " + attempts + " completed");
				attempts++;
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

