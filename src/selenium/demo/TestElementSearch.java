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
		//WebElement raid;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		AutoBattleNew autoBattle = new AutoBattleNew();

		autoBattle.autoBattleNew(driver, wait);
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
