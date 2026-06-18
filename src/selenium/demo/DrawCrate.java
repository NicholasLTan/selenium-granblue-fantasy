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

public class DrawCrate {
	@Test
	public void drawCrate() throws InterruptedException{
		Login login = new Login();
		WebDriver driver = Objects.requireNonNull(login.login(), "WebDriver must not be null");
		WebDriverWait wait = new WebDriverWait(driver, Objects.requireNonNull(Duration.ofSeconds(10)));
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
		List<WebElement> rupie = driver.findElements(By.cssSelector("div[data-id='6002'][data-count='100']"));
		System.out.println("Rupie.size = " + rupie.size());
		if ( !rupie.isEmpty() ) {
			WebElement rupieButton = Objects.requireNonNull(rupie.get(0), "Rupie element must not be null");
			wait.until(ExpectedConditions.elementToBeClickable(rupieButton));
			rupieButton.click();
			Thread.sleep(1000); //For click to process, need to switch to url
		}
		
		driver.get("https://game.granbluefantasy.jp/#present");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-get-all")));
		driver.findElement(By.cssSelector("div[class*='termed']")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lis-present")));
		wait.until(ExpectedConditions.attributeToBe(By.cssSelector("div[id='loading']"), "style", "display: none;"));
		driver.findElement(By.cssSelector("div#prt-present-limit div div[class='btn-present-other']")).click();
		wait.until(ExpectedConditions.attributeToBe(By.cssSelector("div[id='loading']"), "style", "display: none;"));
		//!driver.findElement( By.className("txt-unclaimed-present")).getText().equals("0")
		List<WebElement> getAll = driver.findElements(By.cssSelector("#prt-present-limit div.prt-get-all"));
		count = 0;
		while ( !getAll.isEmpty() && getAll.get(0).isDisplayed() && !Objects.requireNonNull(getAll.get(0).getAttribute("class")).contains("hide") ) {
			System.out.println("GetAll size = " + getAll.size());
			System.out.println("pre = " + getAll.get(0).getAttribute("class"));
			driver.findElement(By.cssSelector("#prt-present-limit div div.btn-get-all")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prt-popup-header")));
			okElement = driver.findElement(ok);
			okElement.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("prt-popup-header")));
			String headerText = driver.findElement(By.className("prt-popup-header")).getText();
			if (headerText.equals("Item Pickup")) {
				okElement = driver.findElement(ok);
				okElement.click();
				wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("txt-pop-confirm"), "You picked up"));
				Thread.sleep(500);
				//okElement = driver.findElement(ok); //fails here
				//okElement.click();
				wait.until(ExpectedConditions.stalenessOf(okElement));
				getAll = driver.findElements(By.cssSelector("#prt-present-limit div.prt-get-all"));
				System.out.println("post= " + getAll.get(0).getAttribute("class"));
				count++;	
			} else if (headerText.equals("Too many items")) {
				okElement = driver.findElement(ok);
				okElement.click();
				break;
			}
			
		}
		System.out.println(count + " Item pages picked up");
		
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.END).perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("atx-lead-link")));
		Thread.sleep(500);  //Necessary sleep for PGDN to process
		
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
			Thread.sleep(1000); // Sleep for picked up items to clear from crate, prevent ElementClickInterceptedException
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
				okElement = driver.findElement(ok);
				okElement.click();
				count++;
				wait.until(ExpectedConditions.stalenessOf(okElement));
			}
			System.out.println(count + " " + type + " pages reserved");
		} 
		System.out.println("DrawCrate Completed");
		
	}
}
