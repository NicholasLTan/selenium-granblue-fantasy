package selenium.demo;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FarmRaidEvent {
	@Test
	public void farmRaidEvent() throws InterruptedException {
		int maxAttempts = 50; // Optional: To prevent infinite loops
		boolean speedFarm = false; //to use BP ASAP. Use False for FP farming.
		boolean quickEntry = true; //For raids that fill/end quickly, usually 6-man or Baha
		int minHP = 20;
		final String raidStr = "603771_highlevel";
		final String raidCss = "img[alt*='" + raidStr + "']";
		final String eventUrl = "https://game.granbluefantasy.jp/#event/advent";
		final String raidUrl = "https://game.granbluefantasy.jp/#quest/assist/event";
		final String normalApStr = "prt-use-ap";
		final String normalApCss = "div[class='" + normalApStr + "']";
		final String lowApStr = normalApStr + " decreased";
		final String lowApCss = "div[class='" + lowApStr + "']";		
		int logLevel = 1;

		
		
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login(), "WebDriver must not be null");
		ConfirmTeam confirmTeam = new ConfirmTeam();
		Battle battle = new Battle();
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(30)));
		WebDriverWait longWait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(600)));
		Results results = new Results();
		WebElement refresh;
		By refreshBy = By.cssSelector("div[class='btn-switch-list event active']");
		List<WebElement> elements;
		IsElementPresent ePresent = new IsElementPresent();

		driver.get(raidUrl);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='onm-assist-priority'")));
		elements = driver.findElements(By.cssSelector("div[class^='onm-assist-priority'"));
		if (!elements.isEmpty()) {
			WebElement firstElement = Objects.requireNonNull(elements.get(0));
			String classAttr = firstElement.getAttribute("class");
			if (classAttr != null && classAttr.endsWith("disable")) {
				firstElement.click();
			}
		}
		int attempts = 1;
		boolean forcedRefresh = false;

		while (attempts <= maxAttempts) {
			if ( logLevel >= 1 ) {System.out.println("Attempt " + attempts + " started");}
			boolean retry = false;
			boolean ten = false;
			wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
			WebElement raidList = driver.findElement(By.id("prt-multi-list"));
			List<WebElement> raids;
			raids = raidList.findElements(By.cssSelector(raidCss));
			Thread.sleep(1000);
			refresh = driver.findElement(refreshBy);
			int refreshCount=0;
			if ( logLevel >= 1 ) {System.out.println(raids.size() + " " + raidStr);}
			while (raids.size() < 1) { 
				if ( logLevel >= 1 ) {System.out.println("Refresh " + refreshCount);}
				//wait.until(element.isDisplayed() -> element.click());
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				if (refreshCount == 5 || quickEntry) {
					if ( logLevel >= 1 ) {System.out.println(refreshCount + " refreshes");}
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
				{System.out.println(raids.size() + " " + raidStr);}
				Thread.sleep(1000);
			}
			/*
			 * int rNum = 1; for (WebElement raid : raids) { //WebElement raidStatus =
			 * raid.findElement(By.xpath(
			 * "./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
			 * System.out.println(rNum + " " + raid.getAttribute("class")); rNum++; }
			 */
			if ( logLevel >= 1 ) {System.out.println("*****");}
			raids = raidList.findElements(By.xpath("./div"));
			int raidNum = 1;
			int maxPct = 0;
			int maxNum = 0;
			int minPct = 100;
			int minNum = 0;
			boolean crew = false;
			for (WebElement raid : raids) {	
				String raidClass = raid.getAttribute("class");
				if (raidClass != null && raidClass.endsWith("guild-member")) { // Prioritize crew raid for Prestige pendants
					crew = true;
					break;
				}
				WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-thumbnail']/img[@class='img-raid-thumbnail']"));
				WebElement raidPct = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[@class='prt-raid-gauge']/div[@class='prt-raid-gauge-inner']"));
				String strPct = raidPct.getAttribute("style");
				if (strPct == null) {
					System.out.println("Skipping raid " + raidNum + " due to missing style attribute");
					raidNum++;
					continue;
				}
				int intPct = Integer.parseInt(strPct.replaceAll("\\D+", ""));
				String raidStatusAlt = raidStatus.getAttribute("alt");
				if (ten || (raidStatusAlt != null && ((!quickEntry && raidStatusAlt.equals(lowApStr)) || (quickEntry && raidStatusAlt.equals(normalApStr))))) {
					//System.out.println(raidNum + " " + raidStatus.getAttribute("class") + " " + intPct);					
					if (intPct > maxPct) {
						maxPct = intPct;
						maxNum = raidNum;
					}
					if (intPct < minPct && intPct > minHP) {
						minPct = intPct;
						minNum = raidNum;
					}
				}
				
				//System.out.println("gauge = " + raidPct.getAttribute("style"));
				if ( logLevel >= 1 ) {System.out.println(raidNum + " " + String.valueOf(raidStatusAlt) + " " + intPct);}
				
				raidNum++;
			}
			if (crew) {
				System.out.println("crew raid found. raidNum = " + raidNum);
				//raidNum-- ???
			} else if (speedFarm) {
				if ( logLevel >= 0 ) {System.out.println("speed mode. minNum = " + minNum + "; minPct = " + minPct);}
				if (minNum==0) { forcedRefresh = true; continue; }
				minNum--;
				raidNum = minNum;
			} else {
				if ( logLevel >= 0 ) {System.out.println("FP mode. maxNum = " + maxNum + "; maxPct = " + maxPct);}
				maxNum--;
				raidNum = maxNum;
			}
			WebElement raid = raids.get(raidNum);
			//System.out.println("maxNum = " + (maxNum+1));
			WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-thumbnail']/img[@class='img-raid-thumbnail']"));
			
			String raidAlt = raidStatus.getAttribute("alt");
			if (raidAlt != null && raidAlt.contains(raidStr)) {
				if ( logLevel >= 1 ) {System.out.println("");}
				if (maxNum > 6) {
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_DOWN).perform();
					Thread.sleep(1000);
				}
				raid.click();
				//Thread.sleep(5000);
				/*
				if (driver.getCurrentUrl().equals("https://game.granbluefantasy.jp/#quest/assist/event")) {
					System.out.println("assist");
					driver.findElement(By.className("btn-usual-ok")).click();
					retry = true;
					wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
					driver.findElement(refreshBy).click();
					//break;
				} else {
					System.out.println("break");
					//break;
				}
				*/
			} else if (ten) {
				System.out.println(raid.getAttribute("class"));
				
				raid.click();
				//break;
			}
			
			wait.until(ExpectedConditions.or(
					//ExpectedConditions.urlMatches("https://game.granbluefantasy.jp/#quest/assist"),
					ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#quest/supporter_raid"),
					ExpectedConditions.urlContains("https://game.granluefantasy.jp/#result_multi"),
					ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#raid_multi"),
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='pop-usual common-pop-error pop-show']"))));
			String currentUrl = driver.getCurrentUrl();
			if (raidUrl.equals(currentUrl)) { 
				System.out.println("assist");
				driver.findElement(By.className("btn-usual-ok")).click();
				retry = true;
				wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
				driver.findElement(refreshBy).click();
			}
			
			
			if (!retry) {
				
				confirmTeam.confirmTeam(wait);
				Thread.sleep(1500);
				String url = Objects.requireNonNull(driver.getCurrentUrl());
				if ( logLevel >= 1 ) {System.out.println("Conf " + url + " Auto");}
				try {
					battle.battle(driver, longWait, false);
				} catch (ElementClickInterceptedException e) {
					if ( url.startsWith("https://game.granbluefantasy.jp/#quest/supporter_raid")) {
						if ( logLevel >= 1 ) {System.out.println("supporter_raid");} 
						Thread.sleep(1000); 
						boolean	elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-ok")); 
						if (elementExists) {
							if ( logLevel >= 1 ) {System.out.println("Raid ended");}
							driver.findElement(By.className("btn-usual-ok")).click(); 
							//Thread.sleep(2000);
							driver.get(raidUrl);
							wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
							Thread.sleep(1000); 
							driver.findElement(refreshBy).click();
							//Thread.sleep(5000); 
							continue; 
						} 
					} else if (url.startsWith("https://game.granluefantasy.jp/#result_multi")) {
						System.out.println("result_multi"); 
						boolean elementExists =	ePresent.isElementPresent(driver, By.className("btn-control")); 
						if (elementExists) { 
							driver.findElement(By.className("btn-control")).click();
							//Thread.sleep(2000);
							driver.get(raidUrl);
							//Thread.sleep(5000); 
							continue; 
						} 
					} else if (url.startsWith("https://game.granbluefantasy.jp/#raid_multi")) {
						System.out.println("raid_multi");
						driver.findElement(By.className("btn-usual-ok")).click();
						Thread.sleep(1000);
						driver.findElement(By.className("btn-treasure-footer-reload")).click();
					}
				}
				//autoBattle.autoBattle(driver, wait);
				//battle.battle(driver, longWait, false);
				System.out.println("RaidEvent Battle completed, moving to Results");
				results.results(driver, longWait, false);
				System.out.println("Attempt " + attempts + " completed");
				attempts++;
				wait.until(ExpectedConditions.urlMatches(eventUrl));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='btn-event-multi']")));
				driver.findElement(By.cssSelector("div[class^='btn-event-multi']")).click();
				wait.until(ExpectedConditions.urlMatches(raidUrl));
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

