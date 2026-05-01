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
		boolean exitAtZero = true;
		boolean speedFarm = true;
		int maxAttempts = 20; // Optional: To prevent infinite loops		
		int minHP = 20;
		final By finderSlot = By.cssSelector("div[class^='btn-search-switch slot4']");
		
		Login login = new Login();
		WebDriver driver = login.login();
		ConfirmTeam confirmTeam = new ConfirmTeam();
		Battle battle = new Battle();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(600));
		Results results = new Results();
		WebElement refresh;
		WebElement element;
		IsElementPresent ePresent = new IsElementPresent();
		//WebElement raid;

		final By refreshBy = By.cssSelector("div[class='btn-search-refresh']");
		final String normalApStr = "prt-use-ap";
		final String normalApCss = "div[class='" + normalApStr + "']";
		final String lowApStr = normalApStr + " decreased";
		final String lowApCss = "div[class='" + lowApStr + "']";

		int attempts = 1;
		boolean forcedRefresh = false;
		while (attempts <= maxAttempts) {
			if (driver.getCurrentUrl() != "https://game.granbluefantasy.jp/#quest/assist") {
				driver.get("https://game.granbluefantasy.jp/#quest/assist");
				System.out.println("Raids");
				element = wait.until(ExpectedConditions.elementToBeClickable(By.id("tab-search")));
				if ( element.getAttribute("class").equals("btn-tabs")) {
					driver.findElement(By.id("tab-search")).click();
					System.out.println("search click");
				}
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
			List<WebElement> raidNormalAP;
			raids = raidList.findElements(By.cssSelector(lowApCss));
			raidNormalAP = raidList.findElements(By.cssSelector(normalApCss));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
			refresh = driver.findElement(refreshBy);
			int refreshCount=0;
			System.out.println(raids.size() + " reduced ap");
			while (raids.size() < 1 || forcedRefresh) { 
				refresh.click();
				refreshCount++;
				Thread.sleep(100);
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				Thread.sleep(1000);
				raids = raidList.findElements(By.cssSelector(lowApCss));
				System.out.println(raids.size() + " reduced ap");
				Thread.sleep(1000);
				System.out.println("Refresh " + refreshCount);
				//wait.until(element.isDisplayed() -> element.click());
				wait.until(ExpectedConditions.elementToBeClickable(refresh));
				if (refreshCount == 5) {
					System.out.println(refreshCount + " refreshes");
					raids = raidList.findElements(By.cssSelector(normalApCss));
					ten = true;
					refreshCount=0;
					//break;
				} else if (forcedRefresh) {
					forcedRefresh = false;
				}
			}
			/*
			 * int rNum = 1; for (WebElement raid : raids) { //WebElement raidStatus =
			 * raid.findElement(By.xpath(
			 * "./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
			 * System.out.println(rNum + " " + raid.getAttribute("class")); rNum++; }
			 */
			System.out.println("*****");
			forcedRefresh = false;
			raids = raidList.findElements(By.xpath("./div"));
			if (raids.size() == 0) { System.out.println("zero"); continue; }
			int raidNum = 1;
			int maxPct = 0;
			int maxNum = 0;
			int minPct = 100;
			int minNum = 0;
			int currBP = Integer.valueOf(driver.findElement(By.cssSelector("div[data-current-bp]")).getAttribute("data-current-bp"));
			boolean crew = false;
			System.out.println(currBP + " BP");
			if ( exitAtZero && currBP <= 1 ) { break; }
			for (WebElement raid : raids) {
				if (raid.getAttribute("class").endsWith("guild-member")) {
					crew = true;
					break;
				}
				WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
				WebElement raidPct = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[@class='prt-raid-gauge']/div[@class='prt-raid-gauge-inner']"));
				String strPct = raidPct.getAttribute("style");
				int intPct = Integer.parseInt(strPct.replaceAll("\\D+", ""));				
				//<div class="btn-multi-raid lis-raid guild-member" data-quest-id="743461" data-quest-type="1" data-raid-id="45287985320" data-raid-type="1" data-viewer-id="" data-chapter-name="Lvl 150 Shenxian" data-bp="2" data-buff-name="" data-cjs-id="9101593" data-is-semi="false" data-timeline-id="633" data-user-id="19656090"><div class="prt-raid-thumbnail"><img class="img-raid-thumbnail" data-raid-id="45287985320" src="https://prd-game-a-granbluefantasy.akamaized.net/assets_en/img/sp/assets/summon/qm/6063771_highlevel.png" alt="assets/summon/qm/6063771_highlevel"></div><div class="prt-raid-info"><div class="txt-raid-name" style="width: 180px; font-size: 12px; height: 12px; line-height: 12px;">Lvl 150 Shenxian</div><div class="prt-item-effect"></div><div class="prt-raid-status"><div class="prt-raid-gauge"><div class="prt-raid-gauge-inner" style="width: 38%;"></div></div><div class="prt-use-ap" data-ap="2" data-ap-max="3"><span class="ico-ap"></span><span class="ico-ap"></span><span class="ico-ap-none"></span></div></div><div class="prt-raid-subinfo"><div class="prt-flees-in">1/18</div><div class="prt-remaining-time">01:24:41</div></div><div class="prt-request-info"><div class="txt-request">Quest Host:</div> <img class="img-job-icon" src="https://prd-game-a-granbluefantasy.akamaized.net/assets_en/img/sp/ui/icon/job/100401.png" alt="100401"><div class="txt-request"><span class="txt-request-name">Seal</span></div><div class="ico-user-status"></div></div></div><div class="prt-button-cover"></div></div>
				if ((ten || raidStatus.getAttribute("class").equals(lowApStr))) {
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
				raidNum++;
			}						
			if (crew) {
				System.out.println("crew raid found. raidNum = " + raidNum);
				//raidNum-- ???
			} else if (speedFarm) {
				System.out.println("speed mode. minNum = " + minNum + "; minPct = " + minPct);
				if (minNum==0) { forcedRefresh = true; continue; }
				minNum--;
				raidNum = minNum;
			} else {
				System.out.println("FP mode. maxNum = " + maxNum + "; maxPct = " + maxPct);
				maxNum--;
				raidNum = maxNum;
			}
			
			WebElement raid = raids.get(raidNum);
			//WebElement raidStatus = raid.findElement(By.xpath("./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]"));
			System.out.println("");
			if (raidNum > 5) {
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.PAGE_DOWN).perform();
				Thread.sleep(1000); //Necessary sleep for PGDN to process
			}
			raid.click();
			//Thread.sleep(5000);
			wait.until(ExpectedConditions.or(
					//ExpectedConditions.urlMatches("https://game.granbluefantasy.jp/#quest/assist"),
					ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#quest/supporter_raid"),
					ExpectedConditions.urlContains("https://game.granluefantasy.jp/#result_multi"),
					ExpectedConditions.urlContains("https://game.granbluefantasy.jp/#raid_multi"),
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='pop-usual common-pop-error pop-show']"))));
			if (driver.getCurrentUrl().equals("https://game.granbluefantasy.jp/#quest/assist")) {
				//System.out.println("assist");
				driver.findElement(By.className("btn-usual-ok")).click();
				retry = true;
				wait.until(ExpectedConditions.elementToBeClickable(refreshBy));
				driver.findElement(refreshBy).click();
			}

			if (!retry) {
				confirmTeam.confirmTeam(driver, wait);
				//Thread.sleep(1500);
				String url = driver.getCurrentUrl();
				System.out.println("Conf " + url + " Auto");
				try {
					battle.battle(driver, longWait);
				} catch (ElementClickInterceptedException e) {
					if ( url.startsWith("https://game.granbluefantasy.jp/#quest/supporter_raid")) {
						System.out.println("supporter_raid"); 
						Thread.sleep(1000); 
						boolean	elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-ok")); 
						if (elementExists) {
							System.out.println("Raid ended");
							driver.findElement(By.className("btn-usual-ok")).click(); 
							//Thread.sleep(2000);
							driver.get("https://game.granbluefantasy.jp/#quest/assist");
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
							driver.get("https://game.granbluefantasy.jp/#quest/assist");
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
				results.results(driver, wait, false);
				
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

