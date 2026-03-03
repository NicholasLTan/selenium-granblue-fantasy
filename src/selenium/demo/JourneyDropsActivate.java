package selenium.demo;
import java.time.Duration;
import org.openqa.selenium.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JourneyDropsActivate {
	public void journeyDropsActivate(WebDriver driver) throws InterruptedException {
		
		driver.get("https://game.granbluefantasy.jp/#shop/exchange/trajectory"); 
		Thread.sleep(5000);  // Let the user actually see something!
		// Click on the element
		String[] dropList = {"1","2","6"};
		
		for (String dropId : dropList) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
			if (dropId == "6") {
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.PAGE_DOWN).perform();
				Thread.sleep(1000);
			}
			
			String questXpath = "/html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[2]/div[3]/div[" + dropId + "]/div[3]";
			
			WebElement button = driver.findElement(By.xpath(questXpath));
			String buttonString = button.getDomAttribute("class");
			System.out.println(buttonString);
			if (buttonString.equals("btn-use-support")  ) {				
				driver.findElement(By.xpath(questXpath)).click();			
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-cancel")));
				driver.findElement(By.cssSelector("div[data-list-key='4']")).click();
				driver.findElement(By.className("btn-usual-ok")).click();
				Thread.sleep(1000);
				driver.findElement(By.className("btn-usual-ok")).click();
				wait2.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(questXpath), "Active"));
				Thread.sleep(1000);
			} else if (buttonString.equals("btn-edit-support")) {
				driver.findElement(By.xpath(questXpath)).click();	
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-usual-close")));
				driver.findElement(By.className("btn-extend-support")).click();
				Thread.sleep(1000);
				driver.findElement(By.className("btn-usual-ok")).click();
				Thread.sleep(1000);
				driver.findElement(By.className("btn-usual-ok")).click();
				Thread.sleep(2000);
			}
			
			
		}
		return;
	}	  
}

//html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div[4]/div[2]
//html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[2]/div/div[4]/div[2]
//html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[3]/div[2]
//html/body/div[1]/div[2]/div/div[3]/div[3]/div[2]/div[7]/div/div[3]/div[2]