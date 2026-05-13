package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestElementSearch {
	@Test
	public void testElementSearch() throws InterruptedException {
		TestLogin login = new TestLogin();
		WebDriver driver = login.testLogin();
		WebElement element;
		List<WebElement> elements;
		//WebElement raid;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		AutoBattleNew autoBattle = new AutoBattleNew();
		By autoButton = By.className("btn-auto");
		By attackButton = By.cssSelector("div[class^='btn-attack-start']");
		By ok = By.cssSelector("div[class^='btn-usual-ok']");
		WebElement okElement;

		List<WebElement> rupie = driver.findElements(By.cssSelector(".btn-lupi.multi.free"));
		System.out.println(rupie.size());
		if ( !rupie.isEmpty() ) {
			wait.until(ExpectedConditions.elementToBeClickable(rupie.get(0)));
			rupie.get(0).click();			
		}
		
		/*
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-receive-all")));
		driver.findElement(By.className("btn-receive-all")).click();
		*/
		
		//List<WebElement> getAll = driver.findElements(By.cssSelector("#prt-present-limit div.prt-get-all"));
		//System.out.println(getAll.size());
		
		/*
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-title-rupie-gacha")));
		List<WebElement> rupie = driver.findElements(By.cssSelector(".btn-shine.present"));
		System.out.println(rupie.size());
		if ( !rupie.isEmpty() ) {
			wait.until(ExpectedConditions.elementToBeClickable(rupie.get(0)));
			rupie.get(0).click();
		}
		*/
		
		/*
		System.out.println("test started");
		System.out.println(driver.findElement(By.className("prt-head-current")).getText());
		WebElement getAll = driver.findElement(By.cssSelector("#prt-present-limit div.prt-get-all"));
		while ( getAll.isDisplayed() ) {
			System.out.println("loop entered");
			driver.findElement(By.cssSelector("#prt-present-limit div div.btn-get-all")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
			okElement = driver.findElement(ok);
			okElement.click();
			//wait.until(ExpectedConditions.stalenessOf(okElement));
			wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Item Pickup"));
			okElement = driver.findElement(ok);
			okElement.click();
			wait.until(ExpectedConditions.stalenessOf(okElement));
		}
		System.out.println("test ended");
		*/
		
		/*
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
		System.out.println("R pendants = " + countR.getText());
		System.out.println("SR pendants = " + countSR.getText());
		if (!countSR.getAttribute("class").isEmpty())  {
			System.out.println("class exists");			
		} else {
			System.out.println("class DNE");
		}
		*/	
		
		
		/*
		List<WebElement> purpleSkills = driver.findElements(By.cssSelector("div[class^='lis-ability-state'][state='2'][type='5']"));
		System.out.println("size = " + purpleSkills.size());
		
		if ( !purpleSkills.isEmpty() ) {
		    for ( int i = 0 ; i < purpleSkills.size() ; i++ ) {
		        WebElement purple = purpleSkills.get(i);
		        System.out.println("pos = " + purple.findElement(By.xpath("./../..")).getAttribute("pos"));
		        if (!purple.findElement(By.xpath("./../..")).getAttribute("pos").isEmpty()) {
		        	purple.click();
		        	Thread.sleep(2500);
		        	//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='btn-ability-skip']")));
		        	//if ( driver.findElement(By.className("prt-ability-skip")).getAttribute("active").equals("1") ) {
		        	//	driver.findElement(By.className("btn-ability-skip")).click();
		        	//}
		        	
		        	purpleSkills = driver.findElements(By.cssSelector("div[icon-type='5']"));
		        	if ( !purpleSkills.isEmpty() ) {
		        		purpleSkills.get(0).click();
		        	}
		        	driver.findElement(By.className("btn-command-back")).click();
		        }
		    } 
		}
		System.out.println("end");
		
		if (!driver.findElements(attackButton).isEmpty()) {
			System.out.println("not empty");
			//driver.findElement(attackButton).click();
		} else {
			System.out.println("empty");
		}
		*/
		
		//driver.findElement(By.className("btn-ability-skip")).click();
		/*
		By selectOne = By.cssSelector("div[data-group='1']"); //Angel Halo
		By selectTwo = By.cssSelector("div[data-difficulty='6']"); //VH
		
		System.out.println("start");
		List<WebElement> elementRankup = driver.findElements(selectOne);
		//wait.until(ExpectedConditions.elementToBeClickable(selectOne));
		for (WebElement raid : elementRankup) {
			 System.out.println(raid.getAttribute("class")); }
		System.out.println("1 found");
		driver.findElement(selectOne).click();
		//driver.findElement(selectOne).findElement(By.xpath("./parent::*")).click();
		System.out.println("1 clicked");
		wait.until(ExpectedConditions.elementToBeClickable(selectTwo));
		System.out.println("2 found");
		driver.findElement(selectTwo).click();
		System.out.println("2 clicked");
		*/
		
		/*
		By rankup = By.id("cjs-lp-rankup");
		List<WebElement> elementRankup = driver.findElements(rankup);
		if (!elementRankup.isEmpty() && elementRankup.get(0).isDisplayed()) {
			System.out.println("Rankup");
			wait.until(ExpectedConditions.elementToBeClickable(elementRankup.get(0)));
			Thread.sleep(1000); //Necessary sleep for canvas anim to play
			elementRankup.get(0).click();
			wait.until(ExpectedConditions.stalenessOf(elementRankup.get(0)));
			System.out.println("Rankup clicked");
		}
		*/
		/*
		final By finderSlot = By.cssSelector("div[class^='btn-search-switch slot2']");
		wait.until(ExpectedConditions.elementToBeClickable(finderSlot));
		driver.findElement(finderSlot).click();
		*/
		
		/*By rankup = By.id("cjs-lp-rankup");
		List<WebElement> elementRankup = driver.findElements(rankup);
		if (!elementRankup.isEmpty() && elementRankup.get(0).isDisplayed()) {
			System.out.println("Rankup");
			//elementRankup.get(0).click();
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(rankup));
			for (WebElement raid : elementRankup) {
				System.out.println(raid.getAttribute("id") + raid.isDisplayed()); }
			System.out.println("Rankup clicked");
		}*/
		
		/*String url = driver.getCurrentUrl(); 
		if (url.startsWith("https://game.granbluefantasy.jp/#result_multi/empty")) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}*/
		
		/*if (!driver.findElements(autoButton).isEmpty()) {
			List<WebElement> elements = driver.findElements(autoButton);
			System.out.println(elements.size());
			for (WebElement raid : elements) {
			System.out.println(raid.getAttribute("class") + raid.isDisplayed()); }
		}*/
		
		/*
		 * IsElementPresent ePresent = new IsElementPresent();
		 * 
		 * element =
		 * driver.findElement(By.cssSelector("div[class='btn-event-raid group']"));
		 * //element =
		 * 
		 * if (ePresent.isElementPresent(driver,
		 * By.cssSelector("div[class='pop-usual pop-daily-bonus pop-show']"))) {
		 * System.out.println("true");
		 * driver.findElement(By.className("btn-usual-close")).click();
		 * Thread.sleep(1000); } //WebElement child =
		 * element.findElement(By.className("btn-usual-close"));
		 * System.out.println("Src name: " + element.getAttribute("src"));
		 * System.out.println("Class name: " + element.getAttribute("class"));
		 * System.out.println("Alt name: " + element.getText()); //child.click();
		 */		
		//autoBattle.autoBattleNew(driver, wait);
		/*
		 * WebElement raidList = driver.findElement(By.id("prt-search-list"));
		 * List<WebElement> raids; raids =
		 * raidList.findElements(By.cssSelector("div[class='prt-use-ap decreased']"));
		 * System.out.println(raids.size());
		 * 
		 * for (WebElement raid : raids) {
		 * System.out.println(raid.getAttribute("class")); }
		 */
		/*
		 * element = driver.findElement(By.cssSelector("a[class^='btn']")); //element =
		 * driver.findElement(By.cssSelector("[data-group='1']"));
		 * System.out.println("Class name: " + element.getAttribute("src"));
		 * System.out.println("Input name: " + element.getAttribute("class"));
		 * System.out.println("Input name: " + element.getAttribute("alt"));
		 */ //element.click();
		//System.out.println(element.getAttribute());
		// <div class="btn-retry cnt-quest" data-buton-name="Play Again"
		// data-chapter-id="81408" data-quest-id="814081" data-type="25"
		// data-chapter-name="Dimensional Riftwalker" data-ap="0"
		// data-start-at-once="false" data-retry-quest="1" data-duplicate-key="1"
		// style="display: block;"></div>
		// System.out.println(driver.getCurrentUrl());
		// driver.findElement(By.cssSelector("div[data-href='event/terra'] >
		// img")).click();
		// List<WebElement> searchInputs = driver.findElements(By.cssSelector("div[class='prt-use-ap decreased']"));
		//List<WebElement> searchInputs = driver.findElements(By.id("prt-search-list"));
		/*
		 * WebElement raidList = driver.findElement(By.id("prt-search-list"));
		 * List<WebElement> raids; raids =
		 * raidList.findElements(By.cssSelector("div[class='prt-use-ap decreased']"));
		 * while (raids.size() < 1) {
		 * driver.findElement(By.cssSelector("div[class='btn-search-refresh']")).click()
		 * ; Thread.sleep(1500); raids =
		 * driver.findElements(By.cssSelector("div[class='prt-use-ap decreased']"));
		 * Thread.sleep(500); } raids = raidList.findElements(By.xpath("./div")); int
		 * raidNum = 1; for (WebElement raid : raids) { List<WebElement>
		 * allChildElements = raid.findElements(By.xpath(
		 * "./div[@class='prt-raid-info']/div[@class='prt-raid-status']/div[2]")); for
		 * (WebElement child :allChildElements) {
		 * System.out.println(child.getAttribute("class")); } raidNum++; }
		 */
			//System.out.println(raid.findElement(By.xpath("./div[@class=prt-raid-info]")).getAttribute("class"));
			/*
			 * if (raid.findElement(By.xpath(
			 * "./div[class='prt-raid-info]/div[class='prt-raid-status']/div[2]")).
			 * getAttribute("class") == "prt-use-ap decreased") { if (raidNum > 6) { Actions
			 * actions = new Actions(driver); actions.sendKeys(Keys.PAGE_DOWN).perform();
			 * Thread.sleep(1000); } raid.click(); Thread.sleep(5000); break; }
			 */
		
		// elementExists = ePresent.isElementPresent(driver, By.className("btn-retry"));
		// List<WebElement> searchInputs =
		// driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[2]"));
		// cssSelector("div[class='btn-retry cnt-quest'][data-buton-name='Play
		// Again']"));
		/*
		 * System.out.println("Found " + searchInputs.size() + " search inputs."); for
		 * (WebElement input : searchInputs) { //element = searchInputs.get(0); raid =
		 * element.findElement(By.xpath("./parent::div/parent::div/parent::div"));
		 * element = searchInputs.get(0); raid =
		 * element.findElement(By.xpath("./child::div")); System.out.println(raid);
		 * System.out.println("Class name: " + input.getAttribute("src"));
		 * System.out.println("Input name: " + input.getAttribute("class"));
		 * System.out.println("Input name: " + input.getAttribute("alt"));
		 * System.out.println("getText: " + input.getText()); Actions actions = new
		 * Actions(driver); actions.sendKeys(Keys.PAGE_DOWN).perform(); }
		 */
			/*
			 * WebElement page=driver.findElement(By.className("cnt-quest-assist"));
			 * page.sendKeys(Keys.PAGE_DOWN);
			 */
			/*
			 * String costString = input.getText(); int preNum = costString.indexOf(':');
			 * int postNum = costString.indexOf(">>"); String pre =
			 * costString.substring(preNum+2, postNum-1); String post =
			 * costString.substring(postNum+3); int testPre = Integer.valueOf(pre); int
			 * testPost = Integer.valueOf(post); System.out.println("pre = " + testPre +
			 * " post = " + testPost);
			 */ 
		
		 

		/*
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(
		 * "btn-attack-start"))); System.out.println("Start disappeared");
		 * driver.findElement(By.className("btn-treasure-footer-reload")).click();
		 * System.out.println("Reload clicked");
		 */
		// new Actions(driver).sendKeys('{CTRL}r{CTRL}');

	}
	// driver.findElement(By.cssSelector("div[class='btn-retry
	// cnt-quest'][data-buton-name='Play Again']")).click();
	// html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[2]
	// /html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[2]
	// /html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[5]
	///html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[5]

}
