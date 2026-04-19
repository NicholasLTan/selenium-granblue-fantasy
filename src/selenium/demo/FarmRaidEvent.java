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
		final String raidStr = "6063771_highlevel";
		final String raidCss = "img[alt$='" + raidStr + "']";
		final String eventUrl = "https://game.granbluefantasy.jp/#event/advent";
		int maxAttempts = 5; // Optional: To prevent infinite loops
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		AutoBattle autoBattle = new AutoBattle();
		Battle battle = new Battle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		Results results = new Results();
		WebElement refresh;
		List<WebElement> elements;
		IsElementPresent ePresent = new IsElementPresent();

		driver.get("https://game.granbluefantasy.jp/#quest/assist/event");
		// driver.findElement(By.cssSelector("img[class='img-global-banner'][src*='treasureraid165']")).click();
		// // Lives Yet Unwritten
		// driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[2]/div/img")).click();
		// //Imagination Overdrive banner
		//System.out.println("Raids");
		//Thread.sleep(5000);

		//driver.findElement(By.id("tab-multi")).click();
		//Thread.sleep(1000);

		//driver.findElement(By.cssSelector("div[class^='btn-switch-list event']")).click();
		//Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class^='onm-assist-priority'")));
		elements = driver.findElements(By.cssSelector("div[class^='onm-assist-priority'"));
		if (!elements.isEmpty() && elements.get(0).getAttribute("class").endsWith("disable")) {
			elements.get(0).click();
		}
//		<div class="btn-multi-raid lis-raid guild-member" data-quest-id="743461" data-quest-type="1" data-raid-id="45287985320" data-raid-type="1" data-viewer-id="" data-chapter-name="Lvl 150 Shenxian" data-bp="2" data-buff-name="" data-cjs-id="9101593" data-is-semi="false" data-timeline-id="633" data-user-id="19656090"><div class="prt-raid-thumbnail"><img class="img-raid-thumbnail" data-raid-id="45287985320" src="https://prd-game-a-granbluefantasy.akamaized.net/assets_en/img/sp/assets/summon/qm/6063771_highlevel.png" alt="assets/summon/qm/6063771_highlevel"></div><div class="prt-raid-info"><div class="txt-raid-name" style="width: 180px; font-size: 12px; height: 12px; line-height: 12px;">Lvl 150 Shenxian</div><div class="prt-item-effect"></div><div class="prt-raid-status"><div class="prt-raid-gauge"><div class="prt-raid-gauge-inner" style="width: 38%;"></div></div><div class="prt-use-ap" data-ap="2" data-ap-max="3"><span class="ico-ap"></span><span class="ico-ap"></span><span class="ico-ap-none"></span></div></div><div class="prt-raid-subinfo"><div class="prt-flees-in">1/18</div><div class="prt-remaining-time">01:24:41</div></div><div class="prt-request-info"><div class="txt-request">Quest Host:</div> <img class="img-job-icon" src="https://prd-game-a-granbluefantasy.akamaized.net/assets_en/img/sp/ui/icon/job/100401.png" alt="100401"><div class="txt-request"><span class="txt-request-name">Seal</span></div><div class="ico-user-status"></div></div></div><div class="prt-button-cover"></div></div>
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
				if (refreshCount == 500) {
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
			boolean crew = false;
			for (WebElement raid : raids) {	
				if (raid.getAttribute("class").endsWith("guild-member")) { // Prioritize crew raid for Prestige pendants
					crew = true;
					break;
				}
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
			if (crew) {
				System.out.println("crew raid found. raidNum = " + raidNum);
				maxNum = raidNum;
				//raidNum-- ???
			} else {
				
			}
			maxNum--;
			WebElement raid = raids.get(maxNum);
			System.out.println("maxNum = " + (maxNum+1));
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
				//autoBattle.autoBattle(driver, wait);
				battle.battle(driver, longWait);
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

