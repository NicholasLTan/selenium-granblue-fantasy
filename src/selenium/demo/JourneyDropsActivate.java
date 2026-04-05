package selenium.demo;
import java.time.Duration;
import org.openqa.selenium.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JourneyDropsActivate {
	public void journeyDropsActivate(WebDriver driver) throws InterruptedException {
		
		driver.get("https://game.granbluefantasy.jp/#shop/exchange/trajectory"); 
		//Thread.sleep(5000);  // Let the user actually see something!
		// Click on the element
		String hours = "2";
		String[] dropList = {"1","2","6"};
		By ok = By.className("btn-usual-ok");
		By ctrl = By.className("btn-control");
		By next = By.className("btn-usual-next");
		By cancel = By.className("btn-usual-cancel");
		By close = By.className("btn-usual-close");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("txt-trangect-num")));
		int jdNum = Integer.valueOf(driver.findElement(By.className("txt-trangect-num")).getText()); 
		for (String dropId : dropList) {
			By jdByCSS = By.cssSelector("div[data-support-id='" + dropId + "']");
			if (Integer.valueOf(dropId) > 5) {
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.PAGE_DOWN).perform();
				wait.until(ExpectedConditions.visibilityOfElementLocated(jdByCSS));
				Thread.sleep(500);
			}
			
			String jdTarget = String.valueOf(jdNum - (Integer.valueOf(hours) * 10));
			String questXpath = "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[3]/div[" + dropId + "]/div[3]";			 
			WebElement button = driver.findElement(jdByCSS);
			//WebElement button = driver.findElement(By.xpath(questXpath));
			String buttonString = button.getDomAttribute("class");
			//System.out.println(buttonString);
			if (buttonString.equals("btn-use-support")  ) {				
				driver.findElement(jdByCSS).click();	// Activate		
				//driver.findElement(By.xpath(questXpath)).click();	// Activate
				//Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(cancel));
				driver.findElement(By.cssSelector("div[data-list-key='4']")).click(); // Lv4
			} else if (buttonString.equals("btn-edit-support")) {
				driver.findElement(jdByCSS).click();	// Extend
				//driver.findElement(By.xpath(questXpath)).click();	// Extend
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop-usual")));
				driver.findElement(By.className("btn-extend-support")).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(ok));
			if (!hours.equals("1")) {
		        WebElement selectElement = driver.findElement(By.className("num-time"));
		        Select select = new Select(selectElement);
		        select.selectByValue(hours);
			}			
			driver.findElement(ok).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop-support-complete")));
			Thread.sleep(500); //Necessary delay
			driver.findElement(ok).click();
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("txt-trangect-num"),jdTarget));
			jdNum = Integer.valueOf(driver.findElement(By.className("txt-trangect-num")).getText());
		}
		return;
	}	  
}