package selenium.demo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Results {
	public static final String retryXpath = "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[2]";
	public static final String expeditionXpath = "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[3]/div[1]/div[2]/div[5]";
	public void results(WebDriver driver, WebDriverWait wait, boolean retry) throws InterruptedException {
		System.out.println("Results wait");
		Thread.sleep(1000);
		By ok = By.className("btn-usual-ok");
		By ctrl = By.className("btn-control");
		By next = By.className("btn-usual-next");
		
		WebElement element;
		wait.until(ExpectedConditions.or(
				ExpectedConditions.elementToBeClickable(ok),
				ExpectedConditions.elementToBeClickable(ctrl),
				//ExpectedConditions.textToBePresentInElementLocated(By.className("prt-popup-header"), "Battle Concluded"),
				ExpectedConditions.elementToBeClickable(By.className("prt-tips-box"))
		));
		System.out.println("Results proceeding");
		IsElementPresent ePresent = new IsElementPresent();
		if (ePresent.isElementPresent(driver, ok)) {
			System.out.println("ok");
			element = driver.findElement(ok);
		} else if (ePresent.isElementPresent(driver, ctrl)) {
			System.out.println("ctrl");
			element = driver.findElement(ctrl);
		} else {
			System.out.println("Results failed");
			return;
		}
		
		String url = driver.getCurrentUrl();
		System.out.println(url + " " + driver.getCurrentUrl());
		if (url.startsWith("https://game.granblue.jp/#result_multi/empty")) {
			Thread.sleep(1000);
			element.click(); //Back to Backup Requests				
			Thread.sleep(5000);
		}
		else if (url.startsWith("https://game.granbluefantasy.jp/#raid")) {
			System.out.println("Raid OK");
			element = wait.until(ExpectedConditions.elementToBeClickable(ok));
			element.click();
			Thread.sleep(1000);
			element = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-treasure-footer-reload")));
			element.click();
			Thread.sleep(4000);
		}
		element.getClass();
		
		boolean elementExists = ePresent.isElementPresent(driver, ok);
		if (elementExists) {
			System.out.println("XP");
			element = wait.until(ExpectedConditions.elementToBeClickable(ok));
			element.click(); //XP		
			Thread.sleep(4000);
		}
		
		
		elementExists = ePresent.isElementPresent(driver, By.id("cjs-lp-rankup"));
		while (elementExists) {
			System.out.println("Rankup");
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id("cjs-lp-rankup")));
			element.click();			
			Thread.sleep(1000);
			elementExists = ePresent.isElementPresent(driver, By.id("cjs-lp-rankup"));
		}
		
		
		elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close"));
		if (elementExists) {
			System.out.println("Missions");
			element = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
			element.click(); //Tokens			
			Thread.sleep(1000);
		}
		elementExists = ePresent.isElementPresent(driver, ok);
		if (elementExists) {
			System.out.println("Tokens");
			element = wait.until(ExpectedConditions.elementToBeClickable(ok));
			element.click(); //Tokens			
			Thread.sleep(1000);
			elementExists = ePresent.isElementPresent(driver, ok);
			if (elementExists) {
				System.out.println("Missions");
				element = wait.until(ExpectedConditions.elementToBeClickable(ok));
				Thread.sleep(1000);
				element.click(); //Tokens			
				Thread.sleep(1000);
			}
		}

		elementExists = ePresent.isElementPresent(driver, By.id("cjs-lp-rankup"));
		while (elementExists) {
			System.out.println("Rankup");
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id("cjs-lp-rankup")));
			element.click();			
			Thread.sleep(1000);
			elementExists = ePresent.isElementPresent(driver, By.id("cjs-lp-rankup"));
		}
		//elementExists = ePresent.isElementPresent(driver, By.className("btn-retry"));
		
		elementExists = ePresent.isElementPresent(driver, By.cssSelector("div[data-chapter-id][class='btn-retry cnt-quest']"));  //Retry
		boolean expeditionExists = ePresent.isElementPresent(driver, By.xpath(expeditionXpath)); //Expedition
		boolean spExists = ePresent.isElementPresent(driver, ctrl); //SP Quests
		//System.out.println (elementExists);
		//System.out.println (retry);
		if (elementExists && retry) {
			System.out.println("Retry");
			element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(retryXpath)));
			element.click(); //Retry			
			Thread.sleep(1000);
			elementExists = ePresent.isElementPresent(driver, next); //Dimensional Halo, Play Next			
			if (elementExists) {
				System.out.println("Dimensional Halo");
				return;
			}
			elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close")); //Mission
			if (elementExists) {
				System.out.println("Mission Close");
				element = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
				element.click();
				Thread.sleep(2000);
			}
		} else if (expeditionExists) {
			System.out.println("Expedition");
			element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(expeditionXpath)));
			element.click(); //Expedition			
			Thread.sleep(1000);
		} else if (spExists) {
			System.out.println("Back to SP Quests");
			element = wait.until(ExpectedConditions.elementToBeClickable(ctrl));
			element.click(); //Back to SP Quests				
			Thread.sleep(5000);
		}
		
		
		elementExists = ePresent.isElementPresent(driver, By.className("btn-usual-close"));  //Close Missions
		if (elementExists) {
			System.out.println("Skyscope Mission Close");
			element = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
			element.click();
			Thread.sleep(1000);
		}
		return;
	}
}
