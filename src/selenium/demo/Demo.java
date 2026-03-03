package selenium.demo;

import org.openqa.selenium.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;
import java.io.File;
import java.time.Duration;
import java.util.List;

public class Demo {
	
	public void testGBF() throws InterruptedException {
		DreadBarrage dB = new DreadBarrage();
		dB.dreadBarrage();
	}
/*public static boolean isElementPresent(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size() > 0;
    }
	   
public void testGoogleSearch() throws InterruptedException {
  // Optional. If not specified, WebDriver searches the PATH for chromedriver.
  System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\libs\\selenium-jars\\chromedriver-win64\\chromedriver.exe");
  ChromeOptions options = new ChromeOptions();
  //println File("C:\\Users\\Nick\\AppData\\Local\\Google\\Chrome\\User Data\\Default").getAbsolutePath();
  options.setExperimentalOption("debuggerAddress","localhost:9222");


  WebDriver driver = new ChromeDriver(options);
  driver.get("https://game.granbluefantasy.jp/#mypage"); 
  Thread.sleep(5000);  // Let the user actually see something!
  // Click on the element
  boolean elementExists = isElementPresent(driver, By.className("btn-usual-close"));
  if (elementExists) {
		driver.findElement(By.className("btn-usual-close")).click();
		System.out.println("Main Quest Close");
		Thread.sleep(2000);
	}
  
  driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[3]/div[2]/div[2]/div/div")).click();  //Dread Barrage banner
  System.out.println("Dread Barrage");
  //driver.findElement(By.cssSelector("[alt='banner_event_start_1']")).click();
  Thread.sleep(2000);
  
  // 1*
  //driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[1]/div[2]")).click();
  
  // 3*
  //driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[3]/div[2]")).click();
  
  // 4*
  driver.findElement(By.xpath("//*[@id=\"enemy-info-list\"]/div[2]/div[4]/div[2]")).click();
  System.out.println("4*");
  Thread.sleep(2000);
  
  int maxAttempts = 20; // Optional: To prevent infinite loops
  int attempts = 0;
  while (attempts < maxAttempts) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wrapper\"]/div[3]/div[3]/div[3]/div[2]")));	    
	    
		element.click(); // Confirm team/support
		System.out.println("Confirm team/support");
		//Thread.sleep(10000);
		  
		WebElement autoButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-auto"))); 
				//driver.findElement(By.className("btn-auto")); 
		autoButton.click(); //Turn on auto-battle
		System.out.println("Auto-battle");
		//Thread.sleep(5000);  
		
		//  Thread.sleep(5000);  // Let the user actually see something!
		//  driver.quit();  
		
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-usual-ok")));
		element.click(); //XP
		System.out.println("XP");
		//*[@id="pop"]/div/div[3]/div
		Thread.sleep(1500);
		elementExists = isElementPresent(driver, By.id("cjs-lp-rankup"));
		while (elementExists) {
			driver.findElement(By.id("cjs-lp-rankup")).click();
			System.out.println("Rankup");
			Thread.sleep(1000);
			elementExists = isElementPresent(driver, By.id("cjs-lp-rankup"));
		}
		driver.findElement(By.className("btn-usual-ok")).click(); //Tokens
		System.out.println("Tokens");

		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"cnt-result\"]/div[1]/div[2]/div[2]")).click(); //Retry
		System.out.println("Retry");
		
		Thread.sleep(1000);
		//WebElement closeButton = driver.findElement(By.className("btn-usual-close"));
		elementExists = isElementPresent(driver, By.className("btn-usual-close"));
		if (elementExists) {
			driver.findElement(By.className("btn-usual-close")).click();
			System.out.println("Mission Close");
			Thread.sleep(2000);
		}
		attempts++;
		System.out.println(attempts);
	}
  
  }*/
}