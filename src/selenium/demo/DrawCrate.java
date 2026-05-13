package selenium.demo;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DrawCrate {
	@Test
	public void drawCrate() throws InterruptedException{
		Login login = new Login();
		WebDriver driver = login.login();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		By ok = By.cssSelector("div[class^='btn-usual-ok']");
		WebElement okElement;
		int count;
		
		driver.get("https://game.granbluefantasy.jp/#gacha/normal");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-title-rupie-gacha")));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("flex-next")));
		if (!driver.findElements(By.className("prt-popup-header")).isEmpty()) {
			wait.until(ExpectedConditions.elementToBeClickable(ok));
			okElement = driver.findElement(ok);
			okElement.click();
			wait.until(ExpectedConditions.stalenessOf(okElement));
		}
		List<WebElement> rupie = driver.findElements(By.cssSelector(".btn-lupi.multi.free"));
		if ( !rupie.isEmpty() ) {
			wait.until(ExpectedConditions.elementToBeClickable(rupie.get(0)));
			rupie.get(0).click();			
		}
		
		driver.get("https://game.granbluefantasy.jp/#present");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-get-all")));
		driver.findElement(By.cssSelector("div[class*='termed']")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lis-present")));
		String allCount = driver.findElement(By.className("txt-unclaimed-present")).getText();
		wait.until(ExpectedConditions.attributeToBe(By.cssSelector("div[id='loading']"), "style", "display: none;"));
		driver.findElement(By.cssSelector("div#prt-present-limit div div[class='btn-present-other']")).click();
		wait.until(ExpectedConditions.attributeToBe(By.cssSelector("div[id='loading']"), "style", "display: none;"));
		//!driver.findElement( By.className("txt-unclaimed-present")).getText().equals("0")
		List<WebElement> getAll = driver.findElements(By.cssSelector("#prt-present-limit div.prt-get-all"));
		count = 0;
		while ( !getAll.isEmpty() && getAll.get(0).isDisplayed() && !getAll.get(0).getAttribute("class").contains("hide") ) {
			System.out.println("GetAll size = " + getAll.size());
			System.out.println("pre = " + getAll.get(0).getAttribute("class"));
			driver.findElement(By.cssSelector("#prt-present-limit div div.btn-get-all")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
			okElement = driver.findElement(ok);
			okElement.click();
			wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Item Pickup"));
			okElement = driver.findElement(ok);
			okElement.click();
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("txt-pop-confirm"), "You picked up"));
			getAll = driver.findElements(By.cssSelector("#prt-present-limit div.prt-get-all"));
			System.out.println("post= " + getAll.get(0).getAttribute("class"));
			count++;
		}
		System.out.println(count + " Item pages picked up");
		
		String[] list = { "weapon", "summon" };
		for ( String type : list ) {
			driver.findElement(By.cssSelector("div[class^='btn-bonus-stock'][data-category='" + type +"']")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated((By.className("prt-popup-header"))));
			wait.until(ExpectedConditions.elementToBeClickable(ok));
			Thread.sleep(500);
			okElement = driver.findElement(ok);
			okElement.click();
			wait.until(ExpectedConditions.stalenessOf(okElement));
			if ( !driver.findElements(ok).isEmpty() ) {	
				wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Plus Mark Stock"));
				okElement = driver.findElement(ok);
				okElement.click();
				wait.until(ExpectedConditions.stalenessOf(okElement));
				okElement = driver.findElement(ok);
				okElement.click();
				System.out.println(type + " plus stocked");
			} else {
				driver.findElement(By.className("btn-usual-close")).click();			
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("prt-popup-header")));
			
			count = 0;
			while (true) {
				driver.findElement(By.cssSelector("div[class^='btn-recycle'][data-category='" + type +"']")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
				driver.findElement(ok).click();
				wait.until(ExpectedConditions.textToBe(By.className("prt-popup-header"), "Reserve"));
				okElement = driver.findElement(ok);
				if (driver.findElement(By.className("txt-popup-body")).getText().equals("No matching loot.")) {
					okElement.click();
					break;
				}
				okElement.click();
				wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("prt-result-recycle"), "The following was reserved."));
				driver.findElement(ok).click();
				count++;
			}
			System.out.println(count + " " + type + " pages reserved");
		} 
		System.out.println("DrawCrate Completed");
		
	}
}
